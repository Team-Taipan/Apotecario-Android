package com.example.apotecario;

public class MedicamentoAtivo {
    private String nome;
    private String dose;
    private String horario;
    private int iconeRes;

    public MedicamentoAtivo(String nome, String dose, String horario, int iconeRes) {
        this.nome = nome;
        this.dose = dose;
        this.horario = horario;
        this.iconeRes = iconeRes;
    }

    public String getNome() {
        return nome;
    }

    public String getDose() {
        return dose;
    }

    public String getHorario() {
        return horario;
    }

    public int getIconeRes() {
        return iconeRes;
    }
}