package com.example.siomaappinicio;

import java.util.ArrayList;
import java.util.Objects;

public class Items {
    String nombre;
    String unidad;
    int incremento;

    public Items(String nombre, String unidad, int incremento) {
        this.nombre = nombre;
        this.unidad = unidad;
        this.incremento = incremento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public int getIncremento() {
        return incremento;
    }

    public void setIncremento(int incremento) {
        this.incremento = incremento;
    }

}
