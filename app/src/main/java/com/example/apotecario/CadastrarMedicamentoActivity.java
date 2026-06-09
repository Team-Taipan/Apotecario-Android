package com.example.apotecario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastrarMedicamentoActivity extends AppCompatActivity {

    private EditText etNomeMedicamento;
    private Spinner spFormaFisica;
    private Button btnCadastrar;

    private static final String PREFS_NAME = "PerfilPrefs";
    private static final String KEY_PERFIL_ID = "id_perfil_ativo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastrar_medicamento);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnVoltar = findViewById(R.id.btnVoltar);
        etNomeMedicamento = findViewById(R.id.etNomeMedicamento);
        spFormaFisica = findViewById(R.id.spFormaFisica);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        btnVoltar.setOnClickListener(v -> finish());

        // Configuração do Spinner de Forma Física
        String[] formas = {"Selecione a forma...", "Comprimido", "Cápsula", "Xarope", "Gotas", "Pomada", "Injeção", "Outro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, formas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFormaFisica.setAdapter(adapter);

        btnCadastrar.setOnClickListener(v -> cadastrarMedicamento());
    }

    private void cadastrarMedicamento() {
        String nome = etNomeMedicamento.getText().toString().trim();
        String forma = spFormaFisica.getSelectedItem().toString();

        if (nome.isEmpty()) {
            Toast.makeText(this, "Por favor, insira o nome do medicamento", Toast.LENGTH_SHORT).show();
            return;
        }

        if (forma.equals("Selecione a forma...")) {
            Toast.makeText(this, "Por favor, selecione a forma física", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Perfil não identificado. Selecione um perfil na tela inicial.", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService api = RetrofitClient.getApiServiceWithToken(this);
        
        // O backend espera CriarMedicamentoDto { nome, fotoURL? }
        // Concatenamos a forma física no nome para não perder a informação
        Medicamento novoMed = new Medicamento();
        novoMed.setNome(nome + " (" + forma + ")");
        novoMed.setOrigem("Personalizado");

        api.criarMedicamento(perfilId, novoMed).enqueue(new Callback<Medicamento>() {
            @Override
            public void onResponse(Call<Medicamento> call, Response<Medicamento> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(CadastrarMedicamentoActivity.this, "Medicamento cadastrado!", Toast.LENGTH_SHORT).show();
                    
                    // Segue para a tela de frequência com o novo medicamento criado, vinculando ao fluxo de tratamento
                    Intent intent = new Intent(CadastrarMedicamentoActivity.this, FrequenciaMedicamentoActivity.class);
                    intent.putExtra("MEDICAMENTO", response.body());
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("API_ERROR", "Erro ao cadastrar: " + response.code());
                    Toast.makeText(CadastrarMedicamentoActivity.this, "Erro no servidor ao cadastrar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Medicamento> call, Throwable t) {
                Log.e("API_ERROR", "Falha na rede: " + t.getMessage());
                Toast.makeText(CadastrarMedicamentoActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
