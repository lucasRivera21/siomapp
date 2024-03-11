package com.example.siomaappinicio;


import java.util.Calendar;

public interface MainScreenContract {
    interface View{
        void getApi(String desde, String hasta, int variable_id, String categoria);
        void loadingData(boolean loading);
        void resetArrayElements();
        void showDate(String date);
        String getDateSelected();
        void setDateSelected(String dateSelected);
        void convertJson(String result);
        String getDataOffline();
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
