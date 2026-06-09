package com.example.apotecario;

import com.google.gson.annotations.SerializedName;

public class Medicamento {

    @SerializedName("med_nome")
    private String nome;

    private int iconeRes;

    public Medicamento() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIconeRes() {
        if (iconeRes == 0) {
            return android.R.drawable.ic_menu_edit;
        }
        return iconeRes;
    }

    public void setIconeRes(int iconeRes) {
        this.iconeRes = iconeRes;
    }
}