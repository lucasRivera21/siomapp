package com.example.siomaappinicio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    public static final String COLUM_DATE = "DATE";
    public static final String COLUM_ID = "ID";
    public static final String COLUM_JSON_LIST = "JSON_LIST";
    public  static final String JSON_TABLE = "JSON_TABLE";

    public DataBase(@Nullable Context context) {
        super(context, "sioma.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + JSON_TABLE + " (" + COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUM_DATE + " TEXT UNIQUE, " + COLUM_JSON_LIST + " TEXT)";
        Log.d("Tag", createTable);
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(Data data){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUM_DATE, data.getDate());
        cv.put(COLUM_JSON_LIST, data.getJsonList());


        float insert = db.insert(JSON_TABLE, null, cv);

        return insert != -1;
    }

    public String getDataForDate(String date){
        String returnJson = "";

        String selection = COLUM_DATE + " = ?";
        String[] selectionArgs = {date};

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(JSON_TABLE, null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()){
            returnJson = cursor.getString(cursor.getColumnIndexOrThrow(COLUM_JSON_LIST));
        }

        cursor.close();
        db.close();
        return returnJson;

    }

    public void updateData(Data data, String date){
        deleteData(date);
        addOne(data);

    }

    public void deleteData(String date){
        String selection = COLUM_DATE + " LIKE ?";
        String[] selectionArgs = {date};
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(JSON_TABLE, selection, selectionArgs);
    }
    /*
    public void deleteAll(){
        String bla = "DROP TABLE SIOMA_TABLE";
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(JSON_TABLE, null, null);
    }
     */
}
