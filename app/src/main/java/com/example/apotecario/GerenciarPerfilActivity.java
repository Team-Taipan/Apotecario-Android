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

public class GerenciarPerfilActivity extends AppCompatActivity {

    private ImageView ivAvatar;
    private ActivityResultLauncher<String> galleryLauncher;
    private EditText etNomeCompleto, etParentesco;
    private String nomeOriginal; // Usado como ID para o PUT/DELETE

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
        etNomeCompleto = findViewById(R.id.etNomeCompleto);
        etParentesco = findViewById(R.id.etParentesco);
        Button btnConfirmar = findViewById(R.id.btnConfirmar);
        Button btnExcluirPerfil = findViewById(R.id.btnExcluirPerfil);
        ImageButton btnVoltar = findViewById(R.id.btnVoltar);

        // Simulando o recebimento de dados (futuramente viria por Intent)
        nomeOriginal = "Otavio"; 
        etNomeCompleto.setText(nomeOriginal);
        etParentesco.setText("Pai");

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
            String novoNome = etNomeCompleto.getText().toString();
            String novoParentesco = etParentesco.getText().toString();
            
            if (novoNome.isEmpty()) {
                Toast.makeText(this, "O nome não pode estar vazio", Toast.LENGTH_SHORT).show();
            } else {
                atualizarPerfilNaAPI(novoNome, novoParentesco);
            }
        });

        btnExcluirPerfil.setOnClickListener(v -> {
            excluirPerfilNaAPI();
        });
    }

    private void atualizarPerfilNaAPI(String nome, String parentesco) {
        Perfil perfilEditado = new Perfil(nome, parentesco, android.R.drawable.ic_menu_gallery, true);

        RetrofitClient.getApiService().atualizarPerfil(nomeOriginal, perfilEditado).enqueue(new Callback<Perfil>() {
            @Override
            public void onResponse(Call<Perfil> call, Response<Perfil> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(GerenciarPerfilActivity.this, "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(GerenciarPerfilActivity.this, "Erro ao atualizar perfil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Perfil> call, Throwable t) {
                Log.e("API_ERROR", "Erro: " + t.getMessage());
                Toast.makeText(GerenciarPerfilActivity.this, "Falha na conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void excluirPerfilNaAPI() {
        // Alterado para deletarPerfil para corresponder ao método definido na interface ApiService
        RetrofitClient.getApiService().deletarPerfil(nomeOriginal).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(GerenciarPerfilActivity.this, "Perfil excluído!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(GerenciarPerfilActivity.this, "Erro ao excluir", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API_ERROR", "Erro: " + t.getMessage());
                Toast.makeText(GerenciarPerfilActivity.this, "Erro de rede", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
