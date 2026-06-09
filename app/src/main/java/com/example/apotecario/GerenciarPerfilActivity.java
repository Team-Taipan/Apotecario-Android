package com.example.apotecario;

import android.os.Bundle;
import android.view.View;
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

        // Dados vindos da Intent
        String nomePerfil = getIntent().getStringExtra("NOME_PERFIL");
        String tipoPerfil = getIntent().getStringExtra("TIPO_PERFIL");

        // Preenche nome sempre que abrir o perfil
        if (nomePerfil != null) {
            etNomeCompleto.setText(nomePerfil);
        }

        // Aplica a regra ao abrir corrigindo o bug de só funcionar depois de mudar e voltar do perfil
        aplicarRegraTipoPerfil(tipoPerfil);

        btnVoltar.setOnClickListener(v -> finish());

        btnConfirmar.setOnClickListener(v ->
                Toast.makeText(this, "Alterações salvas!", Toast.LENGTH_SHORT).show()
        );

        btnExcluirPerfil.setOnClickListener(v ->
                Toast.makeText(this, "Perfil excluído!", Toast.LENGTH_SHORT).show()
        );
    }

    private void aplicarRegraTipoPerfil(String tipoPerfil) {

        // Verifica o tipo de perfil e aplica a regra de edição
        if (tipoPerfil != null && tipoPerfil.equalsIgnoreCase("Titular")) {

            etParentesco.setText("Titular da Conta");

            etParentesco.setEnabled(false);
            etParentesco.setFocusable(false);
            etParentesco.setClickable(false);
            etParentesco.setKeyListener(null);

            etParentesco.setAlpha(0.5f);

            btnExcluirPerfil.setVisibility(View.GONE);

        }
        // Se for dependente, desabilita a edição do parentesco
        else {

            etParentesco.setText("");

            etParentesco.setEnabled(true);
            etParentesco.setFocusable(true);
            etParentesco.setClickable(true);
            etParentesco.setKeyListener(null);

            etParentesco.setAlpha(1f);

            btnExcluirPerfil.setVisibility(View.VISIBLE);
        }
    }
}