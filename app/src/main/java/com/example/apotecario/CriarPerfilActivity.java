package com.example.apotecario;

import android.content.Intent;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CriarPerfilActivity extends AppCompatActivity {

    private ImageView ivAvatar;
    private EditText etNomeCompleto;
    private ActivityResultLauncher<String> galleryLauncher;
    private String selectedAvatarName = "avatar_1.png"; // Placeholder

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
        etNomeCompleto = findViewById(R.id.etNomeCompleto);
        FloatingActionButton fabAddPhoto = findViewById(R.id.fabAddPhoto);
        Button btnCriarPerfil = findViewById(R.id.btnCriarPerfil);

        // Configura o launcher para abrir a galeria
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        ivAvatar.setImageURI(uri);
                        ivAvatar.setImageTintList(null);
                        selectedAvatarName = "avatar_custom.png";
                    }
                }
        );

        fabAddPhoto.setOnClickListener(v -> galleryLauncher.launch("image/*"));
        ivAvatar.setOnClickListener(v -> galleryLauncher.launch("image/*"));

        btnCriarPerfil.setOnClickListener(v -> salvarPerfil());
    }

    private void salvarPerfil() {
        String nome = etNomeCompleto.getText().toString().trim();

        if (nome.isEmpty()) {
            Toast.makeText(this, "Por favor, insira seu nome completo", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criar o objeto Perfil com os dados do titular
        // nome, avatar, tipo, parentescoId (null), papel
        Perfil novoPerfil = new Perfil(nome, "avatar_1.png", "Titular", null, "Admin");

        // Obter a instância da API com o Token JWT injetado
        ApiService api = RetrofitClient.getApiServiceWithToken(this);

        // Chamar o endpoint de criação de perfil inicial
        api.criarPerfilInicial(novoPerfil).enqueue(new Callback<Perfil>() {
            @Override
            public void onResponse(Call<Perfil> call, Response<Perfil> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CriarPerfilActivity.this, "Perfil criado com sucesso!", Toast.LENGTH_SHORT).show();

                    // Navega para a MainActivity após o sucesso
                    Intent intent = new Intent(CriarPerfilActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CriarPerfilActivity.this, "Erro ao criar perfil. Verifique os dados.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Perfil> call, Throwable t) {
                Toast.makeText(CriarPerfilActivity.this, "Erro de rede: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
