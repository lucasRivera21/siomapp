package com.example.siomaappinicio;

import org.json.JSONObject;

public interface MainScreenContract {
    interface View{
        void showElements(String name, int incremento, float valor, String unidad, String categoria);
        void getApi(String desde, String hasta, int variable_id, String categoria);
        void loadingData(boolean loading);
        void resetArrayElements();
    }
    interface Presenter{
        String getCurrentDate();
        String monthConvert(int month);
        void prepareParams(int yearSelected, int monthSelected, int daySelected);
        void initialValues();
    }
    interface Model{
        //JSONObject getApi();

    }
}
