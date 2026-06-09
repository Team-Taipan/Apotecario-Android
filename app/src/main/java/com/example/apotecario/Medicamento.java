package com.example.apotecario;

import java.io.Serializable;

public class Medicamento implements Serializable {

    private Integer id;
    private String nome;
    private String origem;
    private String fotoURL;

    public Medicamento() {
    }

    public Medicamento(String nome, String origem) {
        this.nome = nome;
        this.origem = origem;
    }

    public Integer getCodigo() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getFotoURL() {
        return fotoURL;
    }

    public void setFotoURL(String fotoURL) {
        this.fotoURL = fotoURL;
    }
}