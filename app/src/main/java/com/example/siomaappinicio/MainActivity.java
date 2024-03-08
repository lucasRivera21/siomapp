package com.example.siomaappinicio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MainScreenContract.View {

    MainScreenContract.Presenter presenter;
    TextView textDate;
    public String dateSelected;
    List<ListElement> elementsProduccion  = new ArrayList<>();
    List<ListElement> elementsPolinizacion  = new ArrayList<>();
    List<ListElement> elementsCorte  = new ArrayList<>();
    List<ListElement> elementsClima  = new ArrayList<>();
    TextView titleText;
    TextView notFoundText;
    boolean isFound = false;
    int observador = 0;
    boolean thereAreData;

    //DataBase
    DataBase dataBase;
    Data modelDataBase;

    JsonDataToDb jsonDataForDb;
    ArrayList<JsonDataToDb> listJsonDataForDb = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize(){
        //DataBase
        dataBase = new DataBase(MainActivity.this);

        titleText = findViewById(R.id.title_text);
        presenter = new Presenter(this, new Model());
        notFoundText = findViewById(R.id.not_found);

        //Date button text
        textDate = findViewById(R.id.date_button);
        presenter.initialValues();

        //showDate(presenter.getCurrentDate());
        this.dateSelected = presenter.getCurrentDate();
        showDate(this.dateSelected);
    }
    public void loadingData(boolean loading){
        if (!loading) {
            this.titleText.setText("Inicio");
        } else {
            this.titleText.setText("Cargando ...");
        }
    }
    public void showDate(String date){
        this.dateSelected = date;
        textDate.setText(date);
        resetArrayElements();
        resetCategiries();
        this.listJsonDataForDb.clear();
    }
    public void showDatePicker(View view){

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Calendar newCalendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view1, selectedYear, selectedMonth, selectedDay) -> {
                    this.dateSelected = String.format(Locale.US, "%d %s %02d", selectedDay, presenter.monthConvert(selectedMonth + 1), selectedYear);
                    showDate(this.dateSelected);
                    presenter.prepareParams(selectedYear, selectedMonth + 1, selectedDay);

                    newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                    newCalendar.set(Calendar.MONTH, selectedMonth);
                    newCalendar.set(Calendar.YEAR, selectedYear);

                    presenter.setCurrentDateUser(newCalendar);
                },
                year, month, day
        );

        datePickerDialog.show();
    }
    public void resetCategiries(){
        LinearLayout resetProduccion = findViewById((R.id.produccion_layot));
        LinearLayout resetPolinizacion = findViewById((R.id.polinizacion_layout));
        LinearLayout resetCorte = findViewById((R.id.corte_layout));
        LinearLayout resetClima = findViewById((R.id.clima_layout));

        resetProduccion.setVisibility(View.GONE);
        resetPolinizacion.setVisibility(View.GONE);
        resetCorte.setVisibility(View.GONE);
        resetClima.setVisibility(View.GONE);

        this.isFound = false;
        this.notFoundText.setVisibility(View.GONE);
    }
    public void enableCategories(String categoria){
        LinearLayout categoriaLayout;

        switch (categoria){
            case "produccion":
                categoriaLayout = findViewById(R.id.produccion_layot);
                break;
            case "polinizacion":
                categoriaLayout = findViewById(R.id.polinizacion_layout);
                break;
            case "corte":
                categoriaLayout = findViewById(R.id.corte_layout);
                break;
            default:
                categoriaLayout = findViewById(R.id.clima_layout);
        }

        categoriaLayout.setVisibility(View.VISIBLE);
    }
    public void resetArrayElements(){
        elementsProduccion.clear();
        elementsPolinizacion.clear();
        elementsCorte.clear();
        elementsClima.clear();
    }

    public void showElements(String name, float firstValor, String unidad, String categoria, String incremento){
        ListAdapter listAdapter;

        RecyclerView recyclerView;
        switch (categoria){
            case "produccion":
                elementsProduccion.add(new ListElement(name, incremento, firstValor + " " + unidad));
                listAdapter = new ListAdapter(elementsProduccion, this);
                recyclerView = findViewById(R.id.produccion_view);

                break;
            case "polinizacion":
                elementsPolinizacion.add(new ListElement(name, incremento, firstValor + " " + unidad));
                listAdapter = new ListAdapter(elementsPolinizacion, this);
                recyclerView = findViewById(R.id.polinizacion_view);
                break;
            case "corte":
                elementsCorte.add(new ListElement(name, incremento, firstValor + " " + unidad));
                listAdapter = new ListAdapter(elementsCorte, this);
                recyclerView = findViewById(R.id.corte_view);
                break;
            default:
                elementsClima.add(new ListElement(name, incremento, firstValor + " " + unidad));
                listAdapter = new ListAdapter(elementsClima, this);
                recyclerView = findViewById(R.id.clima_view);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    public void showElementsOnline(String name, JSONArray arrayValor, String unidad, String categoria){
        String incremento = "---";
        float firstValor;

        try {
            firstValor = arrayValor.getJSONObject(0).getInt("valor");
            try {
                float secondValor = arrayValor.getJSONObject(1).getInt("valor");
                incremento = ((secondValor - firstValor)/firstValor) * 100 + " %";
                firstValor = secondValor;
            } catch (JSONException ignored){}
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        jsonDataForDb = new JsonDataToDb(name, unidad, categoria, firstValor, incremento);
        listJsonDataForDb.add(jsonDataForDb);

        if(name.length() > 18) {
            name = name.substring(0, 19) + "...";
        }

        showElements(name, firstValor, unidad, categoria, incremento);

    }

    public void showElementsOffline(String name, float firstValor, String unidad, String categoria, String incremento){
        if(name.length() > 18) {
            name = name.substring(0, 19) + "...";
        }
        showElements(name, firstValor, unidad, categoria, incremento);
    }

    public void convertJson(String result){
        JSONArray jsonResult = null;
        try {
            jsonResult = new JSONArray(result);
            JSONObject element;
            String categoria;
            for(int i = 0; i < jsonResult.length(); i++){
                element = jsonResult.getJSONObject(i);
                categoria = element.getString("categoria");
                showElementsOffline(element.getString("name"), element.getInt("firstValor"), element.getString("unidad"), categoria, element.getString("incremento"));
                enableCategories(categoria);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void buttonRightClicked(View view){
        presenter.addDayToDate(1);
    }
    public void buttonLeftClicked(View view){
        presenter.addDayToDate(-1);
    }
    public void resetObservador(){
        this.observador = 0;
    }
    public void getApi(String desde, String hasta, int variable_id, String categoria){
        RequestQueue requestQueue;
        RequestQueue queue = Volley.newRequestQueue(this);
        final String URL = "https://api.siomapp.com/4/datos/" + variable_id + "/1/2";

       StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
               response -> {
                   JSONObject jsonObject;
                   try {
                       jsonObject = new JSONObject(response);
                       JSONArray data = jsonObject.getJSONArray("data");
                       observador++;

                       if(data.length() != 0){
                           this.isFound = true;

                           String nombre = jsonObject.getString("nombre");
                           String unidad = jsonObject.getString("unidad");

                           showElementsOnline(nombre, data, unidad, categoria);
                           enableCategories(categoria);
                       }
                       if(this.observador == 15){
                           //Add to data base
                           Gson gson = new Gson();
                           String coodinatesJSON = gson.toJson(listJsonDataForDb);
                           Data dataToDb = new Data(-1, this.dateSelected, coodinatesJSON);
                           try {
                                this.thereAreData = dataBase.addOne(dataToDb);
                                if(!this.thereAreData){
                                    dataBase.updateData(dataToDb, this.dateSelected);
                                }
                           } catch (Exception ignored) {
                           }

                           resetObservador();
                           loadingData(false);
                           if(!isFound){
                               this.notFoundText.setVisibility(View.VISIBLE);
                           }else{
                               this.notFoundText.setVisibility(View.GONE);
                           }
                       }
                   } catch (JSONException e) {
                       Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                   }
               }, error -> {
            observador++;

            if(this.observador == 15){
                resetObservador();
                try {
                    String result = dataBase.getDataForDate(this.dateSelected);

                    try {
                        convertJson(result);
                    } catch (Exception ignored) {
                        Toast.makeText(this, "Sin conexion a internet", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ignored) {}
                loadingData(false);

            }
            }){
                    @Override
                    protected Map<String, String> getParams()throws AuthFailureError{
                        Map<String, String> params = new HashMap<>();
                        params.put("desde", desde);
                        params.put("hasta", hasta);
                        params.put("tipo_periodo_id", "1");
                        return params;
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        JSONObject jsonParams = new JSONObject(getParams());
                        return jsonParams.toString().getBytes();
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/json");
                    params.put("Authorization", "XZ43HlJrcQRTjJSWGwrquw==");
                    return params;
                    }
               };
               requestQueue = Volley.newRequestQueue(this);
               requestQueue.add(stringRequest);
    }


}