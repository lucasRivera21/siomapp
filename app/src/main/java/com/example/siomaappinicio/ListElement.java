package com.example.siomaappinicio;

public class ListElement {
    public String name;
    public String incremento;
    public String unidad;
    public int color;

    public ListElement(String name, String incremento, String unidad, int color) {
        this.name = name;
        this.incremento = incremento;
        this.unidad = unidad;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
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
