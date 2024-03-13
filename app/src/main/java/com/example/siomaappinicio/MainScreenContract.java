package com.example.siomaappinicio;


import com.android.volley.RequestQueue;

import java.util.Calendar;

public interface MainScreenContract {
    interface View{
        int getObservador();
        void setObservador(int observador);
        void getApi(String desde, String hasta, int variable_id, String categoria, boolean showNow, String currentDate, RequestQueue requestQueue);
        void loadingData(boolean loading);
        void resetArrayElements();
        void showDate(String date);
        String getDateSelected();
        void setDateSelected(String dateSelected);
        void setDateSelectedBefore(String dateSelectedBefore);
        void convertJson(String result);
        String getDataOffline();
        void resetArrayElementsOffline();
        void resetCategiries();

    }
    interface Presenter{
        void setCurrentDateUser(Calendar currentDateUser);
        void addDayToDate(int day);
        String getCurrentDate();
        String monthConvert(int month);
        void prepareParams(int yearSelected, int monthSelected, int daySelected);
        void initialValues();
        boolean isEnable();
        boolean isAbort();
    }
    interface Model{
        //JSONObject getApi();

    }
}
