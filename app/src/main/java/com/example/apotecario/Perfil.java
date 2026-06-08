package com.example.apotecario;

public class Perfil {
    private String nome;
    private int avatarRes;
    private boolean selecionado;

    public Perfil(String nome, int avatarRes, boolean selecionado) {
        this.nome = nome;
        this.avatarRes = avatarRes;
        this.selecionado = selecionado;
    }

    public String getNome() {
        return nome;
    }

    public int getAvatarRes() {
        return avatarRes;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }
}