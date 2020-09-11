package com.example.travelplanner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "databaza.db";
    public static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE " +
                "tabela" + " (" +
                "id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name" + " TEXT NOT NULL, " +
                "date" + " TEXT NOT NULL, " +
                "hotel" + " TEXT NOT NULL, " +
                "comment" + " TEXT NOT NULL, " +
                "lat" + " TEXT NOT NULL, " +
                "lng" + " TEXT NOT NULL, " +
                "img" + " BLOP NOT NULL, " +
                "checked" + " TEXT NOT NULL " +
                ");";

        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "tabela");
        onCreate(db);
    }
}