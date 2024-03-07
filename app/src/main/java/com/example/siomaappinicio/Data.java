package com.example.siomaappinicio;

import androidx.annotation.NonNull;

import org.json.JSONArray;

public class Data {
    private int id;
    private String date;
    private String jsonList;


    //constructor
    public Data(int id, String date, String jsonList) {
        this.id = id;
        this.date = date;
        this.jsonList = jsonList;
    }

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getJsonList() {
        return jsonList;
    }

    public void setJsonList(String jsonList) {
        this.jsonList = jsonList;
    }
}
