package com.example.apotecario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FrequenciaMedicamentoActivity extends AppCompatActivity {

    private RadioButton rbUmaVez, rbDuasVezes, rbCiclos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_frequencia_medicamento);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> finish());

        rbUmaVez = findViewById(R.id.rbUmaVez);
        rbDuasVezes = findViewById(R.id.rbDuasVezes);
        rbCiclos = findViewById(R.id.rbCiclos);

        // Lógica manual para seleção única
        rbUmaVez.setOnClickListener(v -> updateSelection(rbUmaVez));
        rbDuasVezes.setOnClickListener(v -> updateSelection(rbDuasVezes));
        rbCiclos.setOnClickListener(v -> updateSelection(rbCiclos));

        Button btnProximo = findViewById(R.id.btnProximo);
        btnProximo.setOnClickListener(v -> {
            int frequencia = 0;
            if (rbUmaVez.isChecked()) frequencia = 1;
            else if (rbDuasVezes.isChecked()) frequencia = 2;
            else if (rbCiclos.isChecked()) frequencia = 3;

            if (frequencia == 0) {
                Toast.makeText(this, "Por favor, selecione uma frequência", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, InformacaoTratamentoActivity.class);
                intent.putExtra("FREQUENCIA_SELECIONADA", frequencia);
                startActivity(intent);
            }
        });
    }

    private void updateSelection(RadioButton selected) {
        rbUmaVez.setChecked(selected == rbUmaVez);
        rbDuasVezes.setChecked(selected == rbDuasVezes);
        rbCiclos.setChecked(selected == rbCiclos);
    }
}