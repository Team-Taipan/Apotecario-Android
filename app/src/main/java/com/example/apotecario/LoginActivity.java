package com.example.apotecario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

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

        Button btnEntrar = findViewById(R.id.btn_entrar);
        TextView btnRegistrar = findViewById(R.id.btn_registrar); // Corrigido para TextView

        btnEntrar.setOnClickListener(v -> {
            // Após o login, abre a tela de Criar Perfil
            Intent intent = new Intent(LoginActivity.this, CriarPerfilActivity.class);
            startActivity(intent);
        });

        btnRegistrar.setOnClickListener(v -> {
            // Abre a tela de Registro
            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(intent);
        });
    }
}