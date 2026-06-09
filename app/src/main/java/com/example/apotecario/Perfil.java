package com.example.apotecario;

import com.google.gson.annotations.SerializedName;

public class Perfil {
    private String id;
    private String nome;
    private String avatar;
    private String tipo;
    
    @SerializedName("parentescoId")
    private Integer parentescoId;
    
    private String papel;

    // Campo local para controle de UI, não enviado para a API (transient)
    private transient boolean selecionado;

    // Construtor vazio para o Json
    public Perfil() {}

    public Perfil(String nome, String avatar, String tipo, Integer parentescoId, String papel) {
        this.nome = nome;
        this.avatar = avatar;
        this.tipo = tipo;
        this.parentescoId = parentescoId;
        this.papel = papel;
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Integer getParentescoId() { return parentescoId; }
    public void setParentescoId(Integer parentescoId) { this.parentescoId = parentescoId; }

    public String getPapel() { return papel; }
    public void setPapel(String papel) { this.papel = papel; }

    public boolean isSelecionado() { return selecionado; }
    public void setSelecionado(boolean selecionado) { this.selecionado = selecionado; }
}
