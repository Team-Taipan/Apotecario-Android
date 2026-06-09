package com.example.apotecario;

import android.content.Context;import android.content.Intent;
import android.content.SharedPreferences;
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

    private static final String PREFS_NAME = "PerfilPrefs";
    private static final String KEY_PERFIL_ID = "id_perfil_ativo";
    private static final String KEY_PERFIL_NOME = "nome_perfil_ativo";
    private static final String KEY_PERFIL_TIPO = "tipo_perfil_ativo";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        tvUserName = view.findViewById(R.id.tvUserName);
        rvMedicamentosAtivos = view.findViewById(R.id.rvMedicamentosAtivos);
        rvMedicamentosAtivos.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton fab = view.findViewById(R.id.fabAddInicio);

        SharedPreferences prefs = getActivity()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String nomeSalvo = prefs.getString(KEY_PERFIL_NOME, "Selecionar Perfil");
        tvUserName.setText(nomeSalvo);

        tvUserName.setOnClickListener(v -> showSelecionarPerfilModal());
        fab.setOnClickListener(v -> showAddOptionsDialog());

        carregarMedicamentosExemplo();
        atualizarNomePerfilAtual();

        return view;
    }

    private void carregarMedicamentosExemplo() {
        List<MedicamentoAtivo> lista = new ArrayList<>();
        lista.add(new MedicamentoAtivo("Dipirona", "2 comprimidos", "09:00", android.R.drawable.ic_menu_edit));
        adapter = new MedicamentoAtivoAdapter(lista);
        rvMedicamentosAtivos.setAdapter(adapter);
    }

    private void atualizarNomePerfilAtual() {
        RetrofitClient.getApiServiceWithToken(getContext())
                .getMeusPerfis()
                .enqueue(new Callback<List<Perfil>>() {

                    @Override
                    public void onResponse(Call<List<Perfil>> call, Response<List<Perfil>> response) {
                        if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                            SharedPreferences prefs = getActivity()
                                    .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

                            if (!prefs.contains(KEY_PERFIL_NOME)) {
                                Perfil primeiro = response.body().get(0);
                                prefs.edit()
                                        .putString(KEY_PERFIL_ID, primeiro.getId())
                                        .putString(KEY_PERFIL_NOME, primeiro.getNome())
                                        .putString(KEY_PERFIL_TIPO, primeiro.getTipo())
                                        .apply();

                                tvUserName.setText(primeiro.getNome());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Perfil>> call, Throwable t) {
                        Log.e("API_ERROR", "Erro: " + t.getMessage());
                    }
                });
    }

    private void showSelecionarPerfilModal() {
        if (getActivity() == null) return;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        View view = getLayoutInflater()
                .inflate(R.layout.bottom_sheet_selecionar_perfil, null);

        bottomSheetDialog.setContentView(view);

        RecyclerView rvPerfis = view.findViewById(R.id.rvPerfis);
        rvPerfis.setLayoutManager(new GridLayoutManager(getContext(), 3));

        ApiService api = RetrofitClient.getApiServiceWithToken(getContext());
        api.getMeusPerfis().enqueue(new Callback<List<Perfil>>() {
            @Override
            public void onResponse(Call<List<Perfil>> call, Response<List<Perfil>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Perfil> listaPerfis = response.body();
                    PerfilAdapter adapter = new PerfilAdapter(listaPerfis, perfil -> {
                        tvUserName.setText(perfil.getNome());

                        SharedPreferences.Editor editor = getActivity()
                                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                                .edit();

                        editor.putString(KEY_PERFIL_ID, perfil.getId());
                        editor.putString(KEY_PERFIL_NOME, perfil.getNome());
                        editor.putString(KEY_PERFIL_TIPO, perfil.getTipo());
                        editor.apply();

                        bottomSheetDialog.dismiss();
                    });
                    rvPerfis.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Erro ao carregar perfis", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Perfil>> call, Throwable t) {
                Log.e("API_ERROR", "Erro: " + t.getMessage());
            }
        });

        view.findViewById(R.id.btnAddNovoPerfil).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            startActivity(new Intent(getActivity(), NovoPerfilActivity.class));
        });

        view.findViewById(R.id.btnGerenciarPerfil).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            SharedPreferences prefs = getActivity()
                    .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

            String id = prefs.getString(KEY_PERFIL_ID, null);
            String nome = prefs.getString(KEY_PERFIL_NOME, "");
            String tipo = prefs.getString(KEY_PERFIL_TIPO, "Dependente");

            Intent intent = new Intent(getActivity(), GerenciarPerfilActivity.class);
            intent.putExtra("ID_PERFIL", id);
            intent.putExtra("NOME_PERFIL", nome);
            intent.putExtra("TIPO_PERFIL", tipo);
            startActivity(intent);
        });

        view.findViewById(R.id.btnClosePerfilModal)
                .setOnClickListener(v -> bottomSheetDialog.dismiss());

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