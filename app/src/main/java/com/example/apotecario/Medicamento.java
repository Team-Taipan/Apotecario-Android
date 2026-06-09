package com.example.apotecario;

public class Medicamento {
    private String nome;
    private int iconeRes;

    public Medicamento() {}

    public Medicamento(String nome, int iconeRes) {
        this.nome = nome;
        this.iconeRes = iconeRes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIconeRes() {
        // Se for 0, retorna um ícone padrão
        if (iconeRes == 0) {
            return android.R.drawable.ic_menu_edit; 
        }
        return iconeRes;
    }

    public void setIconeRes(int iconeRes) {
        this.iconeRes = iconeRes;
    }
}
