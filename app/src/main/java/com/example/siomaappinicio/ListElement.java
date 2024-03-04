package com.example.siomaappinicio;

public class ListElement {
    public String name;
    public String incremento;
    public String unidad;

    public ListElement(String name, String incremento, String unidad) {
        this.name = name;
        this.incremento = incremento;
        this.unidad = unidad;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIncremento() {
        return incremento;
    }

    public void setIncremento(String incremento) {
        this.incremento = incremento;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
}
