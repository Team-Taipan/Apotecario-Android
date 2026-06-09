package com.example.apotecario;

import java.util.List;

public class FrequenciaResponse {

    private String tipo;

    private List<HorarioResponse> horarios;

    public String getTipo() {
        return tipo;
    }

    public List<HorarioResponse> getHorarios() {
        return horarios;
    }
}