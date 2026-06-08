package com.example.apotecario;

import android.os.Bundle;
import android.util.Log;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NovoPerfilActivity extends AppCompatActivity {

    private ImageView ivAvatar;
    private ActivityResultLauncher<String> galleryLauncher;
    private EditText etNomeCompleto, etParentesco;

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
        etNomeCompleto = findViewById(R.id.etNomeCompleto);
        etParentesco = findViewById(R.id.etParentesco);
        Button btnCriar = findViewById(R.id.btnCriar);
        ImageButton btnVoltar = findViewById(R.id.btnVoltar);

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
            String parentesco = etParentesco.getText().toString();

            if (nome.isEmpty()) {
                Toast.makeText(this, "Por favor, insira o nome", Toast.LENGTH_SHORT).show();
            } else {
                salvarPerfilNaAPI(nome, parentesco);
            }
        });
    }

    private void salvarPerfilNaAPI(String nome, String parentesco) {
        // Criar o objeto de perfil para enviar
        // Usamos um placeholder para o avatar por enquanto
        Perfil novoPerfil = new Perfil(nome, parentesco, android.R.drawable.ic_menu_gallery, false);

        RetrofitClient.getApiService().cadastrarPerfil(novoPerfil).enqueue(new Callback<Perfil>() {
            @Override
            public void onResponse(Call<Perfil> call, Response<Perfil> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(NovoPerfilActivity.this, "Novo perfil criado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(NovoPerfilActivity.this, "Erro ao criar perfil no servidor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Perfil> call, Throwable t) {
                Log.e("API_ERROR", "Erro: " + t.getMessage());
                Toast.makeText(NovoPerfilActivity.this, "Sem conexão com a API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}