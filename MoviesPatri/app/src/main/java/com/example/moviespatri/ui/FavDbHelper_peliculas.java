package com.example.moviespatri.ui;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavDbHelper_peliculas extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Favoritos_tab_peliculas.db";
    String sqlCreate = "CREATE TABLE Favoritos (id STRING, titulo STRING, rating FLOAT, url_img STRING)";

    public FavDbHelper_peliculas(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Favoritos");

        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
    }

    public void borrar_tabla (SQLiteDatabase db) {
        db.execSQL("DROP TABLE Favoritos");
    }

}
