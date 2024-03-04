package com.example.siomaappinicio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements MainScreenContract.View {

    MainScreenContract.Presenter presenter;

    TextView textDate;

    String dateSelected;

    ArrayList<JSONObject> resultProduccion = new ArrayList<>();
    JSONObject responseJson;

    HashMap<String, Object> produccionJson;

    List<ListElement> elementsProduccion  = new ArrayList<>();
    List<ListElement> elementsPolinizacion  = new ArrayList<>();
    List<ListElement> elementsCorte  = new ArrayList<>();
    List<ListElement> elementsClima  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        //Prueba
        //showPrueba();



    }

    private void initialize(){
        presenter = new Presenter(this, new Model());

        //Date button text
        textDate = findViewById(R.id.date_button);
        showDate(presenter.getCurrentDate());
        presenter.initialValues();

    }

    public void showDate(String date){
        textDate.setText(date);
    }
    public void showDatePicker(View view){
        resetArrayElements();

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view1, selectedYear, selectedMonth, selectedDay) -> {
                    this.dateSelected = String.format(Locale.US, "%d %s %02d", selectedDay, presenter.monthConvert(selectedMonth + 1), selectedYear);
                    showDate(this.dateSelected);
                    presenter.prepareParams(selectedYear, selectedMonth + 1, selectedDay);


                },
                year, month, day
        );

        datePickerDialog.show();
    }
    public void resetArrayElements(){
        elementsProduccion  = new ArrayList<>();
        elementsPolinizacion  = new ArrayList<>();
        elementsCorte  = new ArrayList<>();
        elementsClima  = new ArrayList<>();
    }
    public void showElements(String name, int incremento, float valor, String unidad, String categoria){
        //Toast.makeText(this, String.valueOf(this.resultProduccion), Toast.LENGTH_SHORT).show();
        //elements = new ArrayList<>();
        if(name.length() > 18) {
            name = name.substring(0, 19) + "...";
        }

        ListAdapter listAdapter;
        //RecyclerView recyclerView = findViewById(R.id.recycler_view);
        //Log.d("Tag", String.valueOf(listAdapter.getClass()));
        RecyclerView recyclerView = null;
        switch (categoria){
            case "produccion":
                elementsProduccion.add(new ListElement(name, String.valueOf(incremento) + " %", valor + " " + unidad));
                listAdapter = new ListAdapter(elementsProduccion, this);
                recyclerView = findViewById(R.id.produccion_view);
                break;
            case "polinizacion":
                elementsPolinizacion.add(new ListElement(name, String.valueOf(incremento) + " %", valor + " " + unidad));
                listAdapter = new ListAdapter(elementsPolinizacion, this);
                recyclerView = findViewById(R.id.polinizacion_view);
                break;
            case "corte":
                elementsCorte.add(new ListElement(name, String.valueOf(incremento) + " %", valor + " " + unidad));
                listAdapter = new ListAdapter(elementsCorte, this);
                recyclerView = findViewById(R.id.corte_view);
                break;
            default:
                elementsClima.add(new ListElement(name, String.valueOf(incremento) + " %", valor + " " + unidad));
                listAdapter = new ListAdapter(elementsClima, this);
                recyclerView = findViewById(R.id.clima_view);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

    }

    public void performanceJson(JSONObject jsonObject, String categoria){
        String variableId = null;
        try {
            variableId = jsonObject.getString("variable_id");
            //this.resultProduccion.add(jsonObject);

        } catch (JSONException e) {
            Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_LONG).show();
        }
        //Toast.makeText(this, variableId, Toast.LENGTH_LONG).show();
    }

    public void resetResultProduccion(){
        this.resultProduccion.clear();
    }

    public void asigElement(String nombre, int incremento, float valor, String unidad){

    }

    public void getApi(String desde, String hasta, int variable_id, String categoria){
        RequestQueue requestQueue;
        RequestQueue queue = Volley.newRequestQueue(this);
        final String URL = "https://api.siomapp.com/4/datos/" + variable_id + "/1/2";

       StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
               response -> {
                   JSONObject jsonObject = null;
                   try {
                       jsonObject = new JSONObject(response);
                       JSONArray data = jsonObject.getJSONArray("data");

                       if(data.length() != 0){
                           String nombre = jsonObject.getString("nombre");
                           String unidad = jsonObject.getString("unidad");
                           int incremento = jsonObject.getInt("incremento");

                           JSONObject dataObject = data.getJSONObject(0);
                           float valor = dataObject.getInt("valor");
                           //asigElement(nombre, incremento, valor, unidad);
                           showElements(nombre, incremento, valor, unidad, categoria);


                       }

                       /*
                       if(Objects.equals(categoria, "produccion")){
                           this.resultProduccion.add(jsonObject);

                           //Toast.makeText(MainActivity.this, String.valueOf(resultProduccion), Toast.LENGTH_SHORT).show();
                       }


                       //Toast.makeText(MainActivity.this, String.valueOf(jsonObject.toString()), Toast.LENGTH_SHORT).show();

                       //this.produccionDate.add(jsonObject);
                       //this.produccionJson.put(categoria, jsonObject);

                       this.responseJson = jsonObject;
                       performanceJson(jsonObject, categoria);

                        */

                   } catch (JSONException e) {
                       throw new RuntimeException(e);
                   }
               }, error -> Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_LONG).show()){
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