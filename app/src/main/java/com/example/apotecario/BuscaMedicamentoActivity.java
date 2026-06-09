package com.example.apotecario;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscaMedicamentoActivity extends AppCompatActivity {

    private RecyclerView rvMedicamentos;
    private MedicamentoAdapter adapter;
    private List<Medicamento> listaMedicamentos = new ArrayList<>();
    private EditText etBuscaMedicamento;

    // Handler e Runnable para implementar o Debounce (atraso na busca)
    private Handler searchHandler = new Handler(Looper.getMainLooper());
    private Runnable searchRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_busca_medicamento);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configuração dos componentes da UI
        ImageButton btnVoltar = findViewById(R.id.btnVoltar);
        etBuscaMedicamento = findViewById(R.id.etBuscaMedicamento);
        rvMedicamentos = findViewById(R.id.rvMedicamentos);
        TextView tvCadastreAqui = findViewById(R.id.tvCadastreAqui);

        btnVoltar.setOnClickListener(v -> finish());

        // Configuração do RecyclerView
        rvMedicamentos.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MedicamentoAdapter(listaMedicamentos);
        rvMedicamentos.setAdapter(adapter);

        // Lógica de busca com Debounce
        etBuscaMedicamento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Cancela a execução anterior se o usuário digitar algo novo
                searchHandler.removeCallbacks(searchRunnable);

                searchRunnable = () -> {
                    String query = s.toString().trim();
                    if (query.length() >= 3) {
                        buscarMedicamentosNaAPI(query);
                    } else if (query.isEmpty()) {
                        listaMedicamentos.clear();
                        adapter.notifyDataSetChanged();
                    }
                };

                // Aguarda 500ms no teclado para disparar a API
                searchHandler.postDelayed(searchRunnable, 500);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Cadastrar medicamento manual (caso não encontre na ANVISA)
        tvCadastreAqui.setOnClickListener(v -> {
            Toast.makeText(this, "Funcionalidade de cadastro manual em breve!", Toast.LENGTH_SHORT).show();
        });
    }

    private void buscarMedicamentosNaAPI(String nome) {
        // Obtém o serviço autenticado com o Token JWT
        ApiService api = RetrofitClient.getApiServiceWithToken(this);

        // Chamada para o endpoint /medicamento/anvisa?nome=...&pagina=1
        api.getMedicamentosAnvisa(nome, 1).enqueue(new Callback<List<Medicamento>>() {
            @Override
            public void onResponse(Call<List<Medicamento>> call, Response<List<Medicamento>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaMedicamentos.clear();
                    listaMedicamentos.addAll(response.body());
                    adapter.notifyDataSetChanged();

                    if (listaMedicamentos.isEmpty()) {
                        Toast.makeText(BuscaMedicamentoActivity.this, "Nenhum medicamento encontrado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_ERROR", "Erro ao buscar: " + response.code());
                }

            }

            @Override
            public void onFailure(Call<List<Medicamento>> call, Throwable t) {
                Log.e("API_ERROR", "Falha na conexão: " + t.getMessage());
                Toast.makeText(BuscaMedicamentoActivity.this, "Erro de conexão com o servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}