package com.example.apotecario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class GerenciarPerfilActivity extends AppCompatActivity {

    private ImageView ivAvatar;
    private ActivityResultLauncher<String> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gerenciar_perfil);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ivAvatar = findViewById(R.id.ivAvatar);
        FloatingActionButton fabAddPhoto = findViewById(R.id.fabAddPhoto);
        EditText etNomeCompleto = findViewById(R.id.etNomeCompleto);
        EditText etParentesco = findViewById(R.id.etParentesco);
        Button btnConfirmar = findViewById(R.id.btnConfirmar);
        Button btnExcluirPerfil = findViewById(R.id.btnExcluirPerfil);
        ImageButton btnVoltar = findViewById(R.id.btnVoltar);

        // Configura o launcher para abrir a galeria (reutilizando a lógica anterior)
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        ivAvatar.setImageURI(uri);
                        ivAvatar.setImageTintList(null);
                    }
                }
        );

        fabAddPhoto.setOnClickListener(v -> galleryLauncher.launch("image/*"));
        ivAvatar.setOnClickListener(v -> galleryLauncher.launch("image/*"));

        btnVoltar.setOnClickListener(v -> finish());

        btnConfirmar.setOnClickListener(v -> {
            String nome = etNomeCompleto.getText().toString();
            if (nome.isEmpty()) {
                Toast.makeText(this, "Por favor, insira o nome", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Alterações salvas!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnExcluirPerfil.setOnClickListener(v -> {
            // Lógica para excluir perfil
            Toast.makeText(this, "Perfil excluído!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}