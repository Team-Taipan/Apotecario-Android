package com.example.apotecario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoAdapter.ViewHolder> {

    private List<Medicamento> medicamentos;

    public MedicamentoAdapter(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicamento_busca, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Medicamento med = medicamentos.get(position);
        holder.tvNome.setText(med.getNome());
        holder.ivIcon.setImageResource(med.getIconeRes());
    }

    @Override
    public int getItemCount() {
        return medicamentos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome;
        ImageView ivIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tvMedicamentoNome);
            ivIcon = itemView.findViewById(R.id.ivMedicamentoIcon);
        }
    }
}