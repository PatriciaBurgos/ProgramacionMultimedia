package com.example.moviespatri.ui;
import android.app.Activity;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.moviespatri.R;

public class SQLite_fav_peliculas extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_favoritos);
    }


    public void insertar(String id, String titulo, float rating, String url_img, FavDbHelper_peliculas favdbh){
        //Abrimos la base de datos 'Favoritos_tab_peliculas' en modo escritura
        //FavDbHelper_peliculas favdbh = new FavDbHelper_peliculas(this);
        SQLiteDatabase db = favdbh.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if(db != null){
            //Insertamos los datos en la tabla Usuarios
            db.execSQL("INSERT INTO Favoritos (id,titulo,rating,url_img) VALUES ('" + id + "', '" + titulo + "','" + rating + "', '" + url_img + "')");

            //Cerramos la base de datos
            db.close();
        }
    }

    public long numero_fav(FavDbHelper_peliculas favdbh){
        //FavDbHelper_peliculas favdbh = new FavDbHelper_peliculas(this);

        SQLiteDatabase db = favdbh.getWritableDatabase();

        //Insertamos los datos en la tabla Usuarios
        long numRows = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM Favoritos", null);

        //Cerramos la base de datos
       // db.close();

        return numRows;
    }

    public Cursor consultar_all(FavDbHelper_peliculas favdbh, Cursor c){


        //FavDbHelper_peliculas favdbh = new FavDbHelper_peliculas(this);

        SQLiteDatabase db = favdbh.getWritableDatabase();

        //Insertamos los datos en la tabla Usuarios
        c = db.rawQuery(" SELECT * FROM Favoritos", null);

        //Cerramos la base de datos
        //db.close();

        return c;
    }

    public void eliminar_una_fila (FavDbHelper_peliculas favdbh, String id){
        SQLiteDatabase db = favdbh.getWritableDatabase();

        //Insertamos los datos en la tabla Usuarios
        db.execSQL("DELETE FROM Favoritos WHERE ID = '" + id + "'");

        //Cerramos la base de datos
        db.close();

    }


}
