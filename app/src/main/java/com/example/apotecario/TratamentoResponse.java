package com.example.apotecario;

import java.util.List;

public class TratamentoResponse {

    private Integer id;
    private Integer qtdPorDose;

    private Medicamento medicamento;

    private FrequenciaResponse frequencia;

    public Integer getId() {
        return id;
    }

    public Integer getQtdPorDose() {
        return qtdPorDose;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public FrequenciaResponse getFrequencia() {
        return frequencia;
    }
}