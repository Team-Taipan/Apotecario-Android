package com.example.apotecario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    private EditText edEmail, edSenha;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tokenManager = new TokenManager(this);
        edEmail = findViewById(R.id.ed_email);
        edSenha = findViewById(R.id.ed_senha);
        Button btnEntrar = findViewById(R.id.btn_entrar);
        TextView btnRegistrar = findViewById(R.id.btn_registrar);

        btnEntrar.setOnClickListener(v -> realizarLogin());

        btnRegistrar.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(intent);
        });
    }

    private void realizarLogin() {
        String email = edEmail.getText().toString().trim();
        String senha = edSenha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> credenciais = new HashMap<>();
        credenciais.put("email", email);
        credenciais.put("senha", senha);

        RetrofitClient.getApiService().login(credenciais).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, String> dados = response.body();

                    String token = dados.get("accessToken");
                    String ultimoLogin = dados.get("ultimoLogin");

                    Log.d("LOGIN_DEBUG", "Token recebido: " + token);

                    if (token != null) {
                        tokenManager.saveToken(token);

                        Log.d("LOGIN_DEBUG",
                                "Token salvo: " + tokenManager.getToken());
                    }

                    Toast.makeText(LoginActivity.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();

                    Intent intent;
                    // Se ultimoLogin for nulo, vai para Criar Perfil
                    if (ultimoLogin == null || ultimoLogin.isEmpty() || ultimoLogin.equals("null")) {
                        intent = new Intent(LoginActivity.this, CriarPerfilActivity.class);
                    } else {
                        intent = new Intent(LoginActivity.this, MainActivity.class);
                    }

                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Erro: Email ou senha incorretos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
