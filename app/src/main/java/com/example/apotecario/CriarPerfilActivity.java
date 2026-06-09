package com.example.apotecario;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
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
    private String selectedAvatarName = "avatar_1.png"; // padrão

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

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        ivAvatar.setImageURI(uri);
                        ivAvatar.setImageTintList(null);

                        // Pega o nome real do arquivo
                        selectedAvatarName = getFileNameFromUri(uri);

                        Log.d("AVATAR_DEBUG", "Imagem selecionada: " + selectedAvatarName);
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

        TokenManager tokenManager = new TokenManager(this);
        String token = tokenManager.getToken();

        Log.d("TOKEN_DEBUG", "Token recuperado: " + token);

        if (token == null || token.isEmpty()) {
            Log.e("AUTH_ERROR", "Token não encontrado no TokenManager!");
            Toast.makeText(this, "Erro de autenticação. Refaça o login.", Toast.LENGTH_LONG).show();
            return;
        }

        Perfil novoPerfil = new Perfil(
                nome,
                selectedAvatarName,
                "Titular",
                null,
                "Admin"
        );

        ApiService api = RetrofitClient.getApiServiceWithToken(this);

        api.criarPerfilInicial(novoPerfil).enqueue(new Callback<Perfil>() {
            @Override
            public void onResponse(Call<Perfil> call, Response<Perfil> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CriarPerfilActivity.this,
                            "Perfil criado com sucesso!",
                            Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(CriarPerfilActivity.this, MainActivity.class));
                    finish();
                } else {
                    try {
                        Log.e("API_ERROR",
                                "Erro " + response.code() +
                                        " Body: " + response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(CriarPerfilActivity.this,
                            "Erro " + response.code() + ": Não autorizado",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Perfil> call, Throwable t) {
                Log.e("API_ERROR", "Falha de rede: " + t.getMessage());
                Toast.makeText(CriarPerfilActivity.this,
                        "Erro de rede: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Nome real do arquivo selecionado
    private String getFileNameFromUri(Uri uri) {
        String result = null;

        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index != -1) {
                        result = cursor.getString(index);
                    }
                }
            }
        }

        if (result == null) {
            result = uri.getLastPathSegment();
        }

        return result;
    }
}