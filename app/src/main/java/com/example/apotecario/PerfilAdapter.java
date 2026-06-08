package com.example.apotecario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PerfilAdapter extends RecyclerView.Adapter<PerfilAdapter.ViewHolder> {

    private List<Perfil> listaPerfis;
    private OnPerfilClickListener listener;

    public interface OnPerfilClickListener {
        void onPerfilClick(Perfil perfil);
    }

    public PerfilAdapter(List<Perfil> listaPerfis, OnPerfilClickListener listener) {
        this.listaPerfis = listaPerfis;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_perfil_selecao, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Perfil perfil = listaPerfis.get(position);
        holder.tvNome.setText(perfil.getNome());
        holder.ivAvatar.setImageResource(perfil.getAvatarRes());
        
        holder.indicator.setVisibility(perfil.isSelecionado() ? View.VISIBLE : View.INVISIBLE);

        holder.itemView.setOnClickListener(v -> listener.onPerfilClick(perfil));
    }

    @Override
    public int getItemCount() {
        return listaPerfis.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome;
        ImageView ivAvatar;
        View indicator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tvNomePerfil);
            ivAvatar = itemView.findViewById(R.id.ivAvatarPerfil);
            indicator = itemView.findViewById(R.id.indicatorSelecionado);
        }
    }
}