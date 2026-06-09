package com.example.apotecario;

import java.util.List;

public class CriarTratamentoRequest {

    private Integer medicamentoId;

    private String inicioTratamento;

    private String fimTratamento;

    private Integer qtdPorDose;

    private String frequenciaTipo;

    private List<String> horarios;

    private Integer intervaloHoras;

    private Integer cicloAtivo;

    private Integer cicloRepouso;

    public CriarTratamentoRequest() {
    }

    public Integer getMedicamentoId() {
        return medicamentoId;
    }

    public void setMedicamentoId(Integer medicamentoId) {
        this.medicamentoId = medicamentoId;
    }

    public String getInicioTratamento() {
        return inicioTratamento;
    }

    public void setInicioTratamento(String inicioTratamento) {
        this.inicioTratamento = inicioTratamento;
    }

    public String getFimTratamento() {
        return fimTratamento;
    }

    public void setFimTratamento(String fimTratamento) {
        this.fimTratamento = fimTratamento;
    }

    public Integer getQtdPorDose() {
        return qtdPorDose;
    }

    public void setQtdPorDose(Integer qtdPorDose) {
        this.qtdPorDose = qtdPorDose;
    }

    public String getFrequenciaTipo() {
        return frequenciaTipo;
    }

    public void setFrequenciaTipo(String frequenciaTipo) {
        this.frequenciaTipo = frequenciaTipo;
    }

    public List<String> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<String> horarios) {
        this.horarios = horarios;
    }

    public Integer getIntervaloHoras() {
        return intervaloHoras;
    }

    public void setIntervaloHoras(Integer intervaloHoras) {
        this.intervaloHoras = intervaloHoras;
    }

    public Integer getCicloAtivo() {
        return cicloAtivo;
    }

    public void setCicloAtivo(Integer cicloAtivo) {
        this.cicloAtivo = cicloAtivo;
    }

    public Integer getCicloRepouso() {
        return cicloRepouso;
    }

    public void setCicloRepouso(Integer cicloRepouso) {
        this.cicloRepouso = cicloRepouso;
    }
}