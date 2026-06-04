package com.example.apotecario;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

public class CriarPerfilActivity extends AppCompatActivity {

    private ImageView ivAvatar;
    private ActivityResultLauncher<String> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_criar_perfil);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ivAvatar = findViewById(R.id.ivAvatar);
        FloatingActionButton fabAddPhoto = findViewById(R.id.fabAddPhoto);
        Button btnCriarPerfil = findViewById(R.id.btnCriarPerfil);
        EditText etNomeCompleto = findViewById(R.id.etNomeCompleto);

        // Configura o launcher para abrir a galeria
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        ivAvatar.setImageURI(uri);
                        // O tint padrão pode esconder a imagem se não for removido
                        ivAvatar.setImageTintList(null);
                    }
                }
        );

        // Clique no botão de câmera ou na própria imagem para escolher foto
        fabAddPhoto.setOnClickListener(v -> galleryLauncher.launch("image/*"));
        ivAvatar.setOnClickListener(v -> galleryLauncher.launch("image/*"));

        btnCriarPerfil.setOnClickListener(v -> {
            String nome = etNomeCompleto.getText().toString();
            if (nome.isEmpty()) {
                Toast.makeText(this, "Por favor, insira seu nome completo", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(CriarPerfilActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}