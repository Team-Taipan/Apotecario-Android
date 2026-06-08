package com.example.apotecario;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GerenciarPerfilActivity extends AppCompatActivity {

    private EditText etNomeCompleto, etParentesco;
    private Button btnConfirmar, btnExcluirPerfil;
    private ImageButton btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_perfil);

        etNomeCompleto = findViewById(R.id.etNomeCompleto);
        etParentesco = findViewById(R.id.etParentesco);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnExcluirPerfil = findViewById(R.id.btnExcluirPerfil);
        btnVoltar = findViewById(R.id.btnVoltar);

        String nomePerfil = getIntent().getStringExtra("NOME_PERFIL");
        if (nomePerfil != null) {
            etNomeCompleto.setText(nomePerfil);
        }

        btnVoltar.setOnClickListener(v -> finish());

        btnConfirmar.setOnClickListener(v -> {
            // Lógica para salvar alterações
            Toast.makeText(this, "Alterações salvas com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        });

        btnExcluirPerfil.setOnClickListener(v -> {
            // Lógica para excluir perfil
            Toast.makeText(this, "Perfil excluído!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
