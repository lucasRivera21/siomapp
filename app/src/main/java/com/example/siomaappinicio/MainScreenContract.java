package com.example.siomaappinicio;

import org.json.JSONObject;

public interface MainScreenContract {
    interface View{
        void showPrueba();
        void getApi(String desde, String hasta, int variable_id, String categoria);
        void resetResultProduccion();
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
