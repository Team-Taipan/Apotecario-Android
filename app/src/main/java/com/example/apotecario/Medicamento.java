package com.example.apotecario;

public class Medicamento {
    private String nome;
    private int iconeRes;

    public Medicamento(String nome, int iconeRes) {
        this.nome = nome;
        this.iconeRes = iconeRes;
    }

    public String getNome() {
        return nome;
    }

    public int getIconeRes() {
        return iconeRes;
    }
}