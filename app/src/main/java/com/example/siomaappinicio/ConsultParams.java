package com.example.siomaappinicio;

public class ConsultParams {
    private String desde;
    private String hasta;
    private int tipoPeriodoId;

    public ConsultParams(String desde, String hasta, int tipoPeriodoId){
        this.desde = desde;
        this.hasta = hasta;
        this.tipoPeriodoId = tipoPeriodoId;
    }

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

    public int getTipoPeriodoId() {
        return tipoPeriodoId;
    }

    public void setTipoPeriodoId(int tipoPeriodoId) {
        this.tipoPeriodoId = tipoPeriodoId;
    }
}
