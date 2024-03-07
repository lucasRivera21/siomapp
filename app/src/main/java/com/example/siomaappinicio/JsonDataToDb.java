package com.example.siomaappinicio;

public class JsonDataToDb {
    private String name;
    private String unidad;
    private String categoria;
    private float firstValor;
    private String incremento;

    //constructor
    public JsonDataToDb(String name, String unidad, String categoria, float firstValor, String incremento) {
        this.name = name;
        this.unidad = unidad;
        this.categoria = categoria;
        this.firstValor = firstValor;
        this.incremento = incremento;
    }

    //Getters ans setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public float getFirstValor() {
        return firstValor;
    }

    public void setFirstValor(float firstValor) {
        this.firstValor = firstValor;
    }

    public String getIncremento() {
        return incremento;
    }

    public void setIncremento(String incremento) {
        this.incremento = incremento;
    }
}
