package com.example.apotecario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MedicamentoAtivoAdapter extends RecyclerView.Adapter<MedicamentoAtivoAdapter.ViewHolder> {

    private List<MedicamentoAtivo> listaMedicamentos;

    public MedicamentoAtivoAdapter(List<MedicamentoAtivo> listaMedicamentos) {
        this.listaMedicamentos = listaMedicamentos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicamento_ativo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MedicamentoAtivo med = listaMedicamentos.get(position);
        holder.tvHorario.setText(med.getHorario());
        holder.tvNome.setText(med.getNome());
        holder.tvDose.setText(med.getDose());
        holder.ivIcone.setImageResource(med.getIconeRes());

        holder.itemView.findViewById(R.id.btnTomarItem).setOnClickListener(v -> {
            // Lógica para marcar como tomado
        });
    }

    @Override
    public int getItemCount() {
        return listaMedicamentos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHorario, tvNome, tvDose;
        ImageView ivIcone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHorario = itemView.findViewById(R.id.tvHorarioItem);
            tvNome = itemView.findViewById(R.id.tvNomeItem);
            tvDose = itemView.findViewById(R.id.tvDoseItem);
            ivIcone = itemView.findViewById(R.id.ivIconeItem);
        }
    }
}