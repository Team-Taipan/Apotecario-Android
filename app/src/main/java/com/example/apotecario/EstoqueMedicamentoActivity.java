package com.example.apotecario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.materialswitch.MaterialSwitch;

public class EstoqueMedicamentoActivity extends AppCompatActivity {

    private int estoqueAtual = 20;
    private int quantidadeMinima = 5;
    
    private TextView tvEstoqueAtual, tvQuantidadeMinima;
    private LinearLayout llQuantidadeMinimaSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_estoque_medicamento);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referências
        tvEstoqueAtual = findViewById(R.id.tvEstoqueAtual);
        tvQuantidadeMinima = findViewById(R.id.tvQuantidadeMinima);
        llQuantidadeMinimaSection = findViewById(R.id.llQuantidadeMinimaSection);
        MaterialSwitch switchAviso = findViewById(R.id.switchAviso);
        
        ImageButton btnMenosEstoque = findViewById(R.id.btnMenosEstoque);
        ImageButton btnMaisEstoque = findViewById(R.id.btnMaisEstoque);
        ImageButton btnMenosMinimo = findViewById(R.id.btnMenosMinimo);
        ImageButton btnMaisMinimo = findViewById(R.id.btnMaisMinimo);

        // Lógica do Switch para mostrar/esconder campo
        switchAviso.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                llQuantidadeMinimaSection.setVisibility(View.VISIBLE);
            } else {
                llQuantidadeMinimaSection.setVisibility(View.GONE);
            }
        });

        // Lógica Contador Estoque Atual
        btnMenosEstoque.setOnClickListener(v -> {
            if (estoqueAtual > 0) {
                estoqueAtual--;
                tvEstoqueAtual.setText(String.valueOf(estoqueAtual));
            }
        });
        btnMaisEstoque.setOnClickListener(v -> {
            estoqueAtual++;
            tvEstoqueAtual.setText(String.valueOf(estoqueAtual));
        });

        // Lógica Contador Quantidade Mínima
        btnMenosMinimo.setOnClickListener(v -> {
            if (quantidadeMinima > 0) {
                quantidadeMinima--;
                tvQuantidadeMinima.setText(String.valueOf(quantidadeMinima));
            }
        });
        btnMaisMinimo.setOnClickListener(v -> {
            quantidadeMinima++;
            tvQuantidadeMinima.setText(String.valueOf(quantidadeMinima));
        });

        findViewById(R.id.btnVoltar).setOnClickListener(v -> finish());
        
        findViewById(R.id.btnSalvar).setOnClickListener(v -> {
            Toast.makeText(this, "Medicamento salvo com sucesso!", Toast.LENGTH_LONG).show();
            
            // Não pode abrir um FRAGMENT diretamente com Intent.
            // Você deve abrir a ACTIVITY que contém o fragment (MainActivity).
            Intent intent = new Intent(EstoqueMedicamentoActivity.this, MainActivity.class);
            
            // Limpa o empilhamento de telas para que o usuário não volte para o cadastro ao apertar 'voltar'
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            
            startActivity(intent);
            finish(); // Fecha a tela de estoque
        });
    }
}