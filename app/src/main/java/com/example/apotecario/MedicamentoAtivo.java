package com.example.apotecario;

public class MedicamentoAtivo {
    private Integer id;
    private String nome;
    private String dose;
    private String horario;
    private int iconeRes;
    private boolean tomado;

    public MedicamentoAtivo(Integer id, String nome, String dose, String horario, int iconeRes) {
        this.id = id;
        this.nome = nome;
        this.dose = dose;
        this.horario = horario;
        this.iconeRes = iconeRes;
        this.tomado = false;
    }

    public Integer getId() {
        return id;
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

    public boolean isTomado() {
        return tomado;
    }

    public void setTomado(boolean tomado) {
        this.tomado = tomado;
    }
}