package com.example.apotecario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        rvMedicamentosAtivos.setLayoutManager(new LinearLayoutManager(getContext()));
        
        FloatingActionButton fab = view.findViewById(R.id.fabAddInicio);

        tvUserName.setOnClickListener(v -> showSelecionarPerfilModal());
        fab.setOnClickListener(v -> showAddOptionsDialog());

        // Por enquanto, como o endpoint de medicamentos ativos depende de um perfil selecionado,
        // manteremos uma lista mockada ou você pode chamar o getMedicamentosAnvisa para testes.
        carregarMedicamentosExemplo();

        return view;
    }

    private void carregarMedicamentosExemplo() {
        List<MedicamentoAtivo> lista = new ArrayList<>();
        lista.add(new MedicamentoAtivo("Dipirona", "2 comprimidos", "09:00", android.R.drawable.ic_menu_edit));
        adapter = new MedicamentoAtivoAdapter(lista);
        rvMedicamentosAtivos.setAdapter(adapter);
    }

    private void showSelecionarPerfilModal() {
        if (getActivity() == null) return;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_selecionar_perfil, null);
        bottomSheetDialog.setContentView(view);

        RecyclerView rvPerfis = view.findViewById(R.id.rvPerfis);
        rvPerfis.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // Chamada para o novo endpoint: /perfil/me
        RetrofitClient.getApiService().getMeusPerfis().enqueue(new Callback<List<Perfil>>() {
            @Override
            public void onResponse(Call<List<Perfil>> call, Response<List<Perfil>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Perfil> listaPerfis = response.body();
                    
                    PerfilAdapter perfilAdapter = new PerfilAdapter(listaPerfis, perfil -> {
                        tvUserName.setText(perfil.getNome());
                        bottomSheetDialog.dismiss();
                    });
                    rvPerfis.setAdapter(perfilAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Perfil>> call, Throwable t) {
                Log.e("API_ERROR", "Erro ao carregar perfis: " + t.getMessage());
            }
        });

        view.findViewById(R.id.btnAddNovoPerfil).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            startActivity(new Intent(getActivity(), NovoPerfilActivity.class));
        });

        view.findViewById(R.id.btnGerenciarPerfil).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            Intent intent = new Intent(getActivity(), GerenciarPerfilActivity.class);
            intent.putExtra("NOME_PERFIL", tvUserName.getText().toString());
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
            startActivity(new Intent(getActivity(), BuscaMedicamentoActivity.class));
        });
        bottomSheetDialog.show();
    }
}