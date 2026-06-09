package com.example.apotecario;

import java.util.List;

public class MedicamentoResponse {

    private List<Medicamento> data;
    private int total;
    private int pagina;
    private int ultimaPagina;

    public List<Medicamento> getData() {
        return data;
    }

    public void setData(List<Medicamento> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getUltimaPagina() {
        return ultimaPagina;
    }

    public void setUltimaPagina(int ultimaPagina) {
        this.ultimaPagina = ultimaPagina;
    }
}
