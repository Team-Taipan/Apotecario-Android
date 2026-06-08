package com.example.apotecario;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class InicioFragment extends Fragment {

    private RecyclerView rvMedicamentosAtivos;
    private MedicamentoAtivoAdapter adapter;
    private TextView tvUserName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        tvUserName = view.findViewById(R.id.tvUserName);
        rvMedicamentosAtivos = view.findViewById(R.id.rvMedicamentosAtivos);
        FloatingActionButton fab = view.findViewById(R.id.fabAddInicio);

        tvUserName.setOnClickListener(v -> showSelecionarPerfilModal());

        rvMedicamentosAtivos.setLayoutManager(new LinearLayoutManager(getContext()));
        List<MedicamentoAtivo> listaMed = new ArrayList<>();
        listaMed.add(new MedicamentoAtivo("Dipirona", "2 comprimidos", "09:00", android.R.drawable.ic_menu_edit));
        listaMed.add(new MedicamentoAtivo("Dipirona", "5 gotas", "09:00", android.R.drawable.ic_menu_help));
        listaMed.add(new MedicamentoAtivo("Paracetamol", "1 comprimido", "12:00", android.R.drawable.ic_menu_edit));
        adapter = new MedicamentoAtivoAdapter(listaMed);
        rvMedicamentosAtivos.setAdapter(adapter);

        fab.setOnClickListener(v -> showAddOptionsDialog());

        return view;
    }

    private void showSelecionarPerfilModal() {
        if (getActivity() == null) return;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_selecionar_perfil, null);
        bottomSheetDialog.setContentView(view);

        RecyclerView rvPerfis = view.findViewById(R.id.rvPerfis);
        rvPerfis.setLayoutManager(new GridLayoutManager(getContext(), 3));

        List<Perfil> listaPerfis = new ArrayList<>();
        listaPerfis.add(new Perfil("Otavio", android.R.drawable.ic_menu_gallery, true));
        listaPerfis.add(new Perfil("Mariana", android.R.drawable.ic_menu_gallery, false));
        listaPerfis.add(new Perfil("Ricardo", android.R.drawable.ic_menu_gallery, false));
        listaPerfis.add(new Perfil("Carla", android.R.drawable.ic_menu_gallery, false));

        PerfilAdapter perfilAdapter = new PerfilAdapter(listaPerfis, perfil -> {
            tvUserName.setText(perfil.getNome());
            bottomSheetDialog.dismiss();
        });

        rvPerfis.setAdapter(perfilAdapter);

        // Botão de Adicionar Novo Perfil (+)
        View btnAdd = view.findViewById(R.id.btnAddNovoPerfil);
        if (btnAdd != null) {
            btnAdd.setOnClickListener(v -> {
                bottomSheetDialog.dismiss();
                // Usando Intent com o contexto da Activity principal
                Intent intent = new Intent(getActivity(), NovoPerfilActivity.class);
                startActivity(intent);
            });
        }

        // Botão Gerenciar Perfil
        view.findViewById(R.id.btnGerenciarPerfil).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            Intent intent = new Intent(getActivity(), GerenciarPerfilActivity.class);
            startActivity(intent);
        });

        view.findViewById(R.id.btnClosePerfilModal).setOnClickListener(v -> bottomSheetDialog.dismiss());

        bottomSheetDialog.show();
    }

    private void showAddOptionsDialog() {
        if (getActivity() == null) return;
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
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