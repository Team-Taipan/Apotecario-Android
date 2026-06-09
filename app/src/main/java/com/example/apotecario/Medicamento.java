package com.example.apotecario;

import java.io.Serializable;

public class Medicamento implements Serializable {

    private Integer id;

    private String nome;

    private String origem;

    public Integer getCodigo() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getOrigem() {
        return origem;
    }
}