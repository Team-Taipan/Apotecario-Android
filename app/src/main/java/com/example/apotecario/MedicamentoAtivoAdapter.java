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
    private OnMedicamentoClickListener listener;

    public interface OnMedicamentoClickListener {
        void onMedicamentoLongClick(MedicamentoAtivo medicamento);
        void onTomarClick(MedicamentoAtivo medicamento);
    }

    public MedicamentoAtivoAdapter(List<MedicamentoAtivo> listaMedicamentos, OnMedicamentoClickListener listener) {
        this.listaMedicamentos = listaMedicamentos;
        this.listener = listener;
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
            if (listener != null) listener.onTomarClick(med);
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onMedicamentoLongClick(med);
                return true;
            }
            return false;
        });

        // Também permitir clique simples para abrir opções se preferir,
        // mas o usuário pediu "ao clicar", então vamos usar o clique normal para abrir o menu.
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onMedicamentoLongClick(med);
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