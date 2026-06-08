package com.example.apotecario;

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

public class NovoPerfilActivity extends AppCompatActivity {

    private ImageView ivAvatar;
    private ActivityResultLauncher<String> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_novo_perfil);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ivAvatar = findViewById(R.id.ivAvatar);
        FloatingActionButton fabAddPhoto = findViewById(R.id.fabAddPhoto);
        EditText etNomeCompleto = findViewById(R.id.etNomeCompleto);
        EditText etParentesco = findViewById(R.id.etParentesco);
        Button btnCriar = findViewById(R.id.btnCriar);
        ImageButton btnVoltar = findViewById(R.id.btnVoltar);

        // Launcher para galeria
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

        btnCriar.setOnClickListener(v -> {
            String nome = etNomeCompleto.getText().toString();
            if (nome.isEmpty()) {
                Toast.makeText(this, "Por favor, insira o nome", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Novo perfil criado!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}