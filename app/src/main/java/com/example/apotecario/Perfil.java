package com.example.apotecario;

public class Perfil {
    private String nome;
    private String parentesco;
    private int avatarRes;
    private boolean selecionado;

    public Perfil(String nome, String parentesco, int avatarRes, boolean selecionado) {
        this.nome = nome;
        this.parentesco = parentesco;
        this.avatarRes = avatarRes;
        this.selecionado = selecionado;
    }

    public String getNome() {
        return nome;
    }

    public String getParentesco() {
        return parentesco;
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