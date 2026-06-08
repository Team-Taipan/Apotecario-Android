package com.example.apotecario;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class InicioFragment extends Fragment {

    private RecyclerView rvMedicamentosAtivos;
    private MedicamentoAtivoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        // Inicializar RecyclerView
        rvMedicamentosAtivos = view.findViewById(R.id.rvMedicamentosAtivos);
        rvMedicamentosAtivos.setLayoutManager(new LinearLayoutManager(getContext()));

        // Simulação de dados do banco de dados
        List<MedicamentoAtivo> lista = new ArrayList<>();
        lista.add(new MedicamentoAtivo("Dipirona", "2 comprimidos", "09:00", android.R.drawable.ic_menu_edit));
        lista.add(new MedicamentoAtivo("Dipirona", "5 gotas", "09:00", android.R.drawable.ic_menu_help));
        lista.add(new MedicamentoAtivo("Paracetamol", "1 comprimido", "12:00", android.R.drawable.ic_menu_edit));

        adapter = new MedicamentoAtivoAdapter(lista);
        rvMedicamentosAtivos.setAdapter(adapter);

        FloatingActionButton fab = view.findViewById(R.id.fabAddInicio);
        fab.setOnClickListener(v -> showAddOptionsDialog());

        return view;
    }

    private void showAddOptionsDialog() {
        if (getContext() == null) return;
        
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_add_options, null);
        bottomSheetDialog.setContentView(view);
        
        view.findViewById(R.id.cardAddMedicamento).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            Intent intent = new Intent(getActivity(), BuscaMedicamentoActivity.class);
            startActivity(intent);
        });

        bottomSheetDialog.show();
    }
}