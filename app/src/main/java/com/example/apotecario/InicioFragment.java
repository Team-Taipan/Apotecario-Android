package com.example.apotecario;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InicioFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

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
            // Abre a tela de busca de medicamentos
            Intent intent = new Intent(getActivity(), BuscaMedicamentoActivity.class);
            startActivity(intent);
        });

        bottomSheetDialog.show();
    }
}