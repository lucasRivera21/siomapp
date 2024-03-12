package com.example.siomaappinicio;

import android.util.Log;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;


public class Presenter implements MainScreenContract.Presenter{
    MainScreenContract.View view;
    MainScreenContract.Model model;
    private Calendar currentDateUser = Calendar.getInstance();
    HashMap<String, Integer> produccionData = new HashMap<>();
    HashMap<String, Integer> polinizacionData = new HashMap<>();
    HashMap<String, Integer> corteData = new HashMap<>();
    HashMap<String, Integer> climaData = new HashMap<>();
    HashMap<String, HashMap<String, Integer>> initialData = new HashMap<>();
    DataBase dataBase;
    boolean enable = true;
    Calendar beforeDay;
    boolean abort;

    //constructor
    public Presenter(MainScreenContract.View view, MainScreenContract.Model model){
        this.view = view;
        this.model = model;
    }

    //metodos

    public boolean isAbort() {
        return abort;
    }

    public void setAbort(boolean abort) {
        this.abort = abort;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Calendar getCurrentDateUser() {
        return currentDateUser;
    }

    public void setCurrentDateUser(Calendar currentDateUser) {
        this.currentDateUser = currentDateUser;
    }

    public void initialValues(){
        //Produccion
        this.produccionData.put("Toneladas de fruto fresco por hectárea año acumulado", 256);
        this.produccionData.put("Kilogramos / Persona / día", 531);
        this.produccionData.put("Flores", 254);
        this.produccionData.put("Racimos empacados", 264);
        this.produccionData.put("Racimos cortados", 262);

        //Polinizacion
        this.polinizacionData.put("Área polinizada por persona / día", 265);
        this.polinizacionData.put("% Área polinizada / día", 193);

        //Corte
        this.corteData.put("% Racimos dejados",253);
        this.corteData.put("% Área cortada por mes",267);
        this.corteData.put("Área cortada por persona / día",266);

        //Clima
        this.climaData.put("Precipitación", 8);
        this.climaData.put("Grados Día", 343);
        this.climaData.put("Temperatura Máxima", 342);
        this.climaData.put("Humedad Relativa", 2);
        this.climaData.put("Tasa fotosintética", 377);

        //Initial data
        this.initialData.put("produccion", this.produccionData);
        this.initialData.put("polinizacion", this.polinizacionData);
        this.initialData.put("corte", this.corteData);
        this.initialData.put("clima", this.climaData);
    }
    public void addDayToDate(int day){
        view.resetArrayElements();
        view.resetArrayElementsOffline();

        //Calendar currentDate = this.currentDateUser;
        //Log.d("hi", "current date 1 "+currentDate);

        //currentDate.add(Calendar.DATE, day);
        //Log.d("hi", "current date 2"+currentDate);
        Log.d("hi", "current date 1 "+this.currentDateUser);

        this.currentDateUser.add(Calendar.DATE, day);
        Log.d("hi", "current date 2 "+this.currentDateUser);



        String dateString = String.format(Locale.US, "%d %s %02d", this.currentDateUser.get(Calendar.DAY_OF_MONTH), monthConvert(this.currentDateUser.get(Calendar.MONTH) + 1), this.currentDateUser.get(Calendar.YEAR));
        view.showDate(dateString);

        prepareParams(currentDateUser.get(Calendar.YEAR), currentDateUser.get(Calendar.MONTH) + 1, currentDateUser.get(Calendar.DAY_OF_MONTH));
    }
    public String monthConvert(int month){
        switch (month){
            case 1:
                return "Ene";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Abr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Ago";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            default:
                return "Dic";
        }
    }
    public String getCurrentDate(){
        Calendar today = Calendar.getInstance();
        this.currentDateUser = today;
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH) + 1;
        int day = today.get(Calendar.DAY_OF_MONTH);

        String date = day + " " + monthConvert(month) + " " + year;

        view.setDateSelected(date);

        prepareParams(year, month, day);

        return date;

    }
    public void sendVariableId(String desde, String hasta){

        view.loadingData(true);
        String result = view.getDataOffline();

        //Log.d("Tag", "abort: "+ abort);

        /*
        for(String i : initialData.keySet()){
            for(String j : initialData.get(i).keySet()){
                view.getApi(desde, hasta, initialData.get(i).get(j), i);
            }
        }
         */


        if(!result.isEmpty()){
            //there are data in data base
            view.convertJson(result);
            //Log.d("Tag", "no esta vacio la base de datos " + result);
            for(String i : initialData.keySet()){
                for(String j : initialData.get(i).keySet()){
                    view.getApi(desde, hasta, initialData.get(i).get(j), i, false);
                }
            }
        }else{
            for(String i : initialData.keySet()){
                for(String j : initialData.get(i).keySet()){
                    view.getApi(desde, hasta, initialData.get(i).get(j), i, true);
                }
            }
        }
        //view.resetArrayElements();
        //this.abort = false;

    }
    public void prepareParams(int yearSelected, int monthSelected, int daySelected){
        Calendar beforeDay = Calendar.getInstance();
        beforeDay.set(this.currentDateUser.get(Calendar.YEAR), this.currentDateUser.get(Calendar.MONTH), this.currentDateUser.get(Calendar.DATE));

        beforeDay.add(Calendar.DATE, -1);

        int montBefore = beforeDay.get(Calendar.MONTH) + 1;
        String prepareMonthBefore = montBefore < 10 ? "0" + montBefore : montBefore+"";
        String prepareDayBefore = beforeDay.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + beforeDay.get(Calendar.DAY_OF_MONTH) : beforeDay.get(Calendar.DAY_OF_MONTH)+"";

        String prepareMonth = monthSelected < 10 ? "0" + monthSelected : String.valueOf(monthSelected);
        String prepareDay = daySelected < 10 ? "0" + daySelected : String.valueOf(daySelected);

        String desde = beforeDay.get(Calendar.YEAR) + "-" + prepareMonthBefore + "-" + prepareDayBefore + " 00:00:00";
        String hasta = yearSelected + "-" + prepareMonth + "-" + prepareDay + " 23:59:59";

        sendVariableId(desde, hasta);
    }
}
