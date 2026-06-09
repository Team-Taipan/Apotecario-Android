package com.example.apotecario;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GerenciarPerfilActivity extends AppCompatActivity {

    private EditText etNomeCompleto, etParentesco;
    private Button btnConfirmar, btnExcluirPerfil;
    private ImageButton btnVoltar;

    // Retrofit espera String
    private String idPerfil;
    private String tipoPerfil;
    private String nomePerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_perfil);

        etNomeCompleto = findViewById(R.id.etNomeCompleto);
        etParentesco = findViewById(R.id.etParentesco);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnExcluirPerfil = findViewById(R.id.btnExcluirPerfil);
        btnVoltar = findViewById(R.id.btnVoltar);

        // Dados vindos da Intent
        idPerfil = getIntent().getStringExtra("ID_PERFIL");
        nomePerfil = getIntent().getStringExtra("NOME_PERFIL");
        tipoPerfil = getIntent().getStringExtra("TIPO_PERFIL");

        if (idPerfil == null || idPerfil.isEmpty()) {
            Toast.makeText(this, "Erro: ID do perfil inválido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (nomePerfil != null) {
            etNomeCompleto.setText(nomePerfil);
        }

        aplicarRegraTipoPerfil(tipoPerfil);

        btnVoltar.setOnClickListener(v -> finish());

        btnConfirmar.setOnClickListener(v -> salvarAlteracoes());

        btnExcluirPerfil.setOnClickListener(v -> excluirPerfil());
    }

    private void salvarAlteracoes() {

        String novoNome = etNomeCompleto.getText().toString().trim();

        if (novoNome.isEmpty()) {
            Toast.makeText(this, "Nome não pode ser vazio", Toast.LENGTH_SHORT).show();
            return;
        }

        Perfil perfilEditado = new Perfil(
                novoNome,
                "avatar_1.png",
                tipoPerfil != null ? tipoPerfil : "Dependente",
                null,
                "Admin"
        );

        ApiService api = RetrofitClient.getApiServiceWithToken(this);

        api.atualizarPerfil(idPerfil, perfilEditado).enqueue(new Callback<Perfil>() {

            @Override
            public void onResponse(Call<Perfil> call, Response<Perfil> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(GerenciarPerfilActivity.this,
                            "Perfil atualizado com sucesso!",
                            Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    Log.e("API_ERROR", "Erro HTTP: " + response.code());

                    try {
                        Log.e("API_ERROR", "BODY: " + response.errorBody().string());
                    } catch (Exception e) {
                        Log.e("API_ERROR", "Erro ao ler body");
                    }

                    Toast.makeText(GerenciarPerfilActivity.this,
                            "Erro ao atualizar perfil",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Perfil> call, Throwable t) {
                Log.e("API_ERROR", "Falha rede: " + t.getMessage());

                Toast.makeText(GerenciarPerfilActivity.this,
                        "Erro de conexão",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void excluirPerfil() {

        ApiService api = RetrofitClient.getApiServiceWithToken(this);

        api.deletarPerfil(idPerfil).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(GerenciarPerfilActivity.this,
                            "Perfil excluído com sucesso!",
                            Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    Log.e("API_ERROR", "Erro delete HTTP: " + response.code());

                    Toast.makeText(GerenciarPerfilActivity.this,
                            "Erro ao excluir perfil",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API_ERROR", "Falha rede: " + t.getMessage());

                Toast.makeText(GerenciarPerfilActivity.this,
                        "Erro de conexão",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void aplicarRegraTipoPerfil(String tipoPerfil) {

        if (tipoPerfil != null && tipoPerfil.equalsIgnoreCase("Titular")) {

            etParentesco.setText("Titular da Conta");

            etParentesco.setEnabled(false);
            etParentesco.setFocusable(false);
            etParentesco.setClickable(false);
            etParentesco.setKeyListener(null);

            etParentesco.setAlpha(0.5f);

            btnExcluirPerfil.setVisibility(View.GONE);

        } else {

            etParentesco.setText("");

            etParentesco.setEnabled(true);
            etParentesco.setFocusable(true);
            etParentesco.setClickable(true);

            etParentesco.setAlpha(1f);

            btnExcluirPerfil.setVisibility(View.VISIBLE);
        }
    }
}