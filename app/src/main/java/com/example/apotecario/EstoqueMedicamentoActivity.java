package com.example.apotecario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.materialswitch.MaterialSwitch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EstoqueMedicamentoActivity extends AppCompatActivity {

    private int estoqueAtual = 20;
    private int quantidadeMinima = 5;

    private TextView tvEstoqueAtual, tvQuantidadeMinima;
    private LinearLayout llQuantidadeMinimaSection;

    private static final String PREFS_NAME = "PerfilPrefs";
    private static final String KEY_PERFIL_ID = "id_perfil_ativo";

    private Medicamento medicamentoSelecionado;
    private int frequenciaSelecionada;
    private int qtdDose;
    private String dataInicio;
    private String dataFim;
    private String horario;
    private String intervalo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_estoque_medicamento);

        medicamentoSelecionado = (Medicamento) getIntent().getSerializableExtra("MEDICAMENTO");

        frequenciaSelecionada = getIntent().getIntExtra("FREQUENCIA_SELECIONADA", 0);

        qtdDose = getIntent().getIntExtra("QTD_DOSE", 1);

        dataInicio = getIntent().getStringExtra("DATA_INICIO");

        dataFim = getIntent().getStringExtra("DATA_FIM");

        horario = getIntent().getStringExtra("HORARIO");

        intervalo = getIntent().getStringExtra("INTERVALO");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referências
        tvEstoqueAtual = findViewById(R.id.tvEstoqueAtual);
        tvQuantidadeMinima = findViewById(R.id.tvQuantidadeMinima);
        llQuantidadeMinimaSection = findViewById(R.id.llQuantidadeMinimaSection);
        MaterialSwitch switchAviso = findViewById(R.id.switchAviso);

        ImageButton btnMenosEstoque = findViewById(R.id.btnMenosEstoque);
        ImageButton btnMaisEstoque = findViewById(R.id.btnMaisEstoque);
        ImageButton btnMenosMinimo = findViewById(R.id.btnMenosMinimo);
        ImageButton btnMaisMinimo = findViewById(R.id.btnMaisMinimo);

        // Lógica do Switch para mostrar/esconder campo
        switchAviso.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                llQuantidadeMinimaSection.setVisibility(View.VISIBLE);
            } else {
                llQuantidadeMinimaSection.setVisibility(View.GONE);
            }
        });

        // Lógica Contador Estoque Atual
        btnMenosEstoque.setOnClickListener(v -> {
            if (estoqueAtual > 0) {
                estoqueAtual--;
                tvEstoqueAtual.setText(String.valueOf(estoqueAtual));
            }
        });
        btnMaisEstoque.setOnClickListener(v -> {
            estoqueAtual++;
            tvEstoqueAtual.setText(String.valueOf(estoqueAtual));
        });

        // Lógica Contador Quantidade Mínima
        btnMenosMinimo.setOnClickListener(v -> {
            if (quantidadeMinima > 0) {
                quantidadeMinima--;
                tvQuantidadeMinima.setText(String.valueOf(quantidadeMinima));
            }
        });
        btnMaisMinimo.setOnClickListener(v -> {
            quantidadeMinima++;
            tvQuantidadeMinima.setText(String.valueOf(quantidadeMinima));
        });

        findViewById(R.id.btnVoltar).setOnClickListener(v -> finish());

        findViewById(R.id.btnSalvar).setOnClickListener(v -> {

            // Verifica se o medicamento foi selecionado, evitando nullpointer
            if (medicamentoSelecionado == null) {

                Toast.makeText(this, "Medicamento não encontrado", Toast.LENGTH_LONG).show();

                return;
            }

            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

            int perfilId = -1;
            try {
                perfilId = prefs.getInt(KEY_PERFIL_ID, -1);
            } catch (ClassCastException e) {
                Object val = prefs.getAll().get(KEY_PERFIL_ID);
                if (val instanceof String) {
                    try {
                        perfilId = Integer.parseInt((String) val);
                    } catch (NumberFormatException nfe) {
                        Log.e("PREFS_ERROR", "Erro ao converter id_perfil_ativo: " + val);
                    }
                }
            }

            if (perfilId == -1) {

                Toast.makeText(this, "Nenhum perfil selecionado", Toast.LENGTH_LONG).show();

                return;
            }

            CriarTratamentoRequest request = new CriarTratamentoRequest();

            request.setMedicamentoId(medicamentoSelecionado.getCodigo());

            request.setQtdPorDose(qtdDose);

            request.setInicioTratamento(montarDataHoraISO(dataInicio, horario));

            if (dataFim != null && !dataFim.isEmpty() && dataFim.contains("/")) {
                request.setFimTratamento(montarDataHoraISO(dataFim, horario));
            }

            switch (frequenciaSelecionada) {

                case 1:
                    request.setFrequenciaTipo("Custom");
                    List<String> horarios = new ArrayList<>();
                    horarios.add(horario);
                    request.setHorarios(horarios);

                    break;

                case 2:
                    request.setFrequenciaTipo("Intervalo");
                    request.setIntervaloHoras(extrairIntervaloHoras(intervalo));

                    break;

                case 3:
                    request.setFrequenciaTipo("Ciclo");
                    request.setCicloAtivo(21);
                    request.setCicloRepouso(7);

                    break;
            }

            ApiService api = RetrofitClient.getApiServiceWithToken(this);

            api.criarTratamento(perfilId, request).enqueue(new Callback<Void>() {

                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                    Log.d("TRATAMENTO", "HTTP " + response.code());

                    if (response.isSuccessful()) {

                        Toast.makeText(EstoqueMedicamentoActivity.this, "Tratamento salvo com sucesso!", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(EstoqueMedicamentoActivity.this, MainActivity.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        finish();
                    } else {
                        try {
                            Log.e("TRATAMENTO", response.errorBody() != null ? response.errorBody().string() : "Sem corpo de erro");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(EstoqueMedicamentoActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    private String converterData(String dataBr) {

        if (dataBr == null || dataBr.isEmpty()) {
            return null;
        }

        try {
            SimpleDateFormat origem = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date data = origem.parse(dataBr);
            SimpleDateFormat destino = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return destino.format(data);
        } catch (ParseException e) {
            return null;
        }
    }

    private String montarDataHoraISO(String data, String hora) {

        String dataConvertida = converterData(data);
        if (dataConvertida == null) {
            return null;
        }
        return dataConvertida + "T" + hora + ":00";
    }

    private Integer extrairIntervaloHoras(String texto) {

        if (texto == null) {
            return null;
        }

        if (texto.contains("12")) return 12;
        if (texto.contains("8")) return 8;
        if (texto.contains("6")) return 6;
        if (texto.contains("4")) return 4;

        return null;
    }
}