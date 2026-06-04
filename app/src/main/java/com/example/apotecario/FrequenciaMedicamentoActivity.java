package com.example.apotecario;

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

        // Lógica manual para seleção única, já que estão dentro de CardViews
        rbUmaVez.setOnClickListener(v -> updateSelection(rbUmaVez));
        rbDuasVezes.setOnClickListener(v -> updateSelection(rbDuasVezes));
        rbCiclos.setOnClickListener(v -> updateSelection(rbCiclos));

        Button btnProximo = findViewById(R.id.btnProximo);
        btnProximo.setOnClickListener(v -> {
            if (!rbUmaVez.isChecked() && !rbDuasVezes.isChecked() && !rbCiclos.isChecked()) {
                Toast.makeText(this, "Por favor, selecione uma frequência", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Indo para configuração de horários...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateSelection(RadioButton selected) {
        rbUmaVez.setChecked(selected == rbUmaVez);
        rbDuasVezes.setChecked(selected == rbDuasVezes);
        rbCiclos.setChecked(selected == rbCiclos);
    }
}