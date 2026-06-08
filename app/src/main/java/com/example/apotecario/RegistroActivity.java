package com.example.apotecario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private EditText edEmail, edPassword, edConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Vínculo de componentes
        edEmail = findViewById(R.id.ed_email);
        edPassword = findViewById(R.id.ed_password);
        edConfirmPassword = findViewById(R.id.ed_confirmPassword);
        Button btnRegistrar = findViewById(R.id.btn_registrar);
        TextView tvIrLogin = findViewById(R.id.tv_ir_login);
        ImageButton btnVoltar = findViewById(R.id.btn_voltar);

        // Botão Voltar
        btnVoltar.setOnClickListener(v -> finish());

        // Ir para Login
        tvIrLogin.setOnClickListener(v -> finish());

        // Lógica de Registro
        btnRegistrar.setOnClickListener(v -> realizarRegistro());
    }

    private void realizarRegistro() {
        String email = edEmail.getText().toString().trim();
        String senha = edPassword.getText().toString().trim();
        String confirmarSenha = edConfirmPassword.getText().toString().trim();

        // Validação básica
        if (email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (senha.length() < 8) {
            Toast.makeText(this, "A senha deve ter no mínimo 8 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
            return;
        }

        // Montagem do corpo da requisição
        Map<String, String> dados = new HashMap<>();
        dados.put("email", email);
        dados.put("senha", senha);
        dados.put("confirmarSenha", confirmarSenha);

        // Chamada à API via Retrofit
        RetrofitClient.getApiService().cadastrarUsuario(dados).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegistroActivity.this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();

                    // Novos usuários devem criar um perfil após o registro
                    Intent intent = new Intent(RegistroActivity.this, CriarPerfilActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Trata erros comuns do servidor
                    String erroMsg = "Erro ao registrar (" + response.code() + ")";
                    if (response.code() == 400) {
                        erroMsg = "Dados inválidos: Verifique o e-mail ou a senha (mín. 8 caracteres)";
                    }
                    Log.e("API_REGISTRO", "Erro no servidor: " + response.code());
                    Toast.makeText(RegistroActivity.this, erroMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Erros de rede ou servidor offline
                Log.e("API_REGISTRO", "Falha de conexão: " + t.getMessage());
                Toast.makeText(RegistroActivity.this, "Sem conexão com o servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}