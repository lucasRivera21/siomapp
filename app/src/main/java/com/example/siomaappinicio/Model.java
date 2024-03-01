package com.example.siomaappinicio;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Model implements MainScreenContract.Model{
    MainScreenContract.Presenter presenter;
    JSONObject responseApi;
    /*
    public JSONObject getApi(){

        RequestQueue requestQueue;
        RequestQueue queue = Volley.newRequestQueue(this);
        final String URL = "https://api.siomapp.com/4/datos/262/1/2";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                response -> {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                        String nombre = jsonObject.getString("nombre");

                        this.responseApi = jsonObject;


                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, error -> System.out.println("error")){
            @Override
            protected Map<String, String> getParams()throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("desde", "2024-02-26 00:00:00");
                params.put("hasta", "2024-02-26 23:59:59");
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

        return null;
    }

     */
}
