package com.example.apotecario;

import android.os.Bundle;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class BuscaMedicamentoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_busca_medicamento);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> finish());

        RecyclerView rvMedicamentos = findViewById(R.id.rvMedicamentos);
        rvMedicamentos.setLayoutManager(new LinearLayoutManager(this));

        // Dados de exemplo (Simulando o que viria da API/Banco)
        List<Medicamento> lista = new ArrayList<>();
        lista.add(new Medicamento("Paracetamol", android.R.drawable.ic_menu_edit));
        lista.add(new Medicamento("Dipirona (Gotas)", android.R.drawable.ic_menu_help));
        lista.add(new Medicamento("Insulina", android.R.drawable.ic_menu_compass));

        MedicamentoAdapter adapter = new MedicamentoAdapter(lista);
        rvMedicamentos.setAdapter(adapter);
    }
}