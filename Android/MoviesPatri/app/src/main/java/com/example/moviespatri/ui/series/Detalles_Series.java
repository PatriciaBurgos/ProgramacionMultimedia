package com.example.moviespatri.ui.series;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviespatri.R;
import com.example.moviespatri.credits_series.com.example.moviespatri.CreditsSeries;
import com.example.moviespatri.details_series.com.example.moviespatri.DetailsSeries;
import com.example.moviespatri.favoritos.com.example.moviespatri.Favoritos;
import com.example.moviespatri.models.CreditsFeed;
import com.example.moviespatri.retrofit.MovieService;
import com.example.moviespatri.retrofit.RetrofitInstance;
import com.example.moviespatri.ui.FavDbHelper_peliculas;
import com.example.moviespatri.ui.Favoritos_tab_peliculas;
import com.example.moviespatri.ui.SQLite_fav_peliculas;
import com.example.moviespatri.ui.peliculas.DetallesAdapter_Actores;
import com.example.moviespatri.ui.peliculas.DetallesAdapter_Generos;
import com.example.moviespatri.ui.peliculas.DetallesAdapter_Studio;
import com.example.moviespatri.ui.peliculas.DetallesViewModel;
import com.like.LikeButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detalles_Series extends AppCompatActivity {

    RecyclerView recycler_view_actores, recycler_view_generos; /*recycler_view_studio*/;
    DetallesAdapter_Actores_Series detallesAdapterActoresSeries;
    DetallesAdapter_Generos_Series detallesAdapterGenerosSeries;
    //  DetallesAdapter_Studio detallesAdapterStudio;
    DetallesSeriesViewModel detallesViewModel;


    TextView textViewNombre, textViewResumen, textViewFecha;
    ImageView imag;
    Button bot_ver_ahora;
    RatingBar ratingBar;
    LikeButton likeButton;

    String nombre, id, imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_series);


        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        nombre = intent.getStringExtra("nombre");
        String resumen = intent.getStringExtra("resumen");
        imagen = intent.getStringExtra("imagen");
        String puntuacion = intent.getStringExtra("puntuacion");
        String fecha = intent.getStringExtra("fecha");


        // Capture the layout's TextView and set the string as its text
        textViewNombre = findViewById(R.id.textView_nombre_serie);
        textViewResumen = findViewById(R.id.textView_resumen_serie);
        imag = findViewById(R.id.imageView_series);
        bot_ver_ahora = findViewById(R.id.button_verAhora_serie);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar_serie);
        likeButton = (LikeButton) findViewById(R.id.star_button);
        textViewFecha = findViewById(R.id.textView_fecha);


        textViewNombre.setText(nombre);
        textViewResumen.setText(resumen);
        // textViewStudio.setText(studio);
        // textViewGenero.setText(genero);
        ratingBar.setNumStars(5);
        ratingBar.setRating(Float.parseFloat(puntuacion) / 2);
        textViewFecha.setText(fecha);

        Picasso.get().load("https://image.tmdb.org/t/p/w500" + imagen).into(imag);


        detallesViewModel = ViewModelProviders.of(this).get(DetallesSeriesViewModel.class);

        /* Enlaza el RecyclerView definido en el layout con el objeto recyclerView */
        recycler_view_actores = (RecyclerView) findViewById(R.id.recycler_view_actores_serie);
        recycler_view_generos = (RecyclerView) findViewById(R.id.recycler_view_generos_serie);
        /*recycler_view_studio = (RecyclerView) findViewById(R.id.recycler_view_studio);*/

        /* Establece que recyclerView tendrá un layout lineal, en concreto horizontal*/
        recycler_view_actores.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_actores.setLayoutManager(layoutManager);

        recycler_view_generos.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recycler_view_generos.setLayoutManager(layoutManager2);

        /*recycler_view_studio.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recycler_view_studio.setLayoutManager(layoutManager3);*/

        /*  Indica que cada uno de los items va a tener un tamaño fijo*/
        recycler_view_actores.setHasFixedSize(true);
        recycler_view_generos.setHasFixedSize(true);
        /*recycler_view_studio.setHasFixedSize(true);*/

        /* Instancia un objeto de la clase MovieAdapterTopRated */
        detallesAdapterActoresSeries = new DetallesAdapter_Actores_Series(this);
        detallesAdapterGenerosSeries = new DetallesAdapter_Generos_Series(this);
        /*detallesAdapterStudio = new DetallesAdapter_Studio(this);*/

        /* Establece el adaptador asociado a recyclerView */
        recycler_view_actores.setAdapter(detallesAdapterActoresSeries);
        recycler_view_generos.setAdapter(detallesAdapterGenerosSeries);
        /*recycler_view_studio.setAdapter(detallesAdapterStudio);*/

        /* Pone la animación por defecto de recyclerView */
        recycler_view_generos.setItemAnimator(new DefaultItemAnimator());
        /*recycler_view_studio.setItemAnimator(new DefaultItemAnimator());*/

        loadSearch_actores();
        loadSearch_generos();
        /*loadSearch_studio();*/
    }

    public void loadSearch_actores() {
        /* Crea la instanncia de retrofit */
        MovieService getCredits = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
        /* Se definen los parámetros de la llamada a la función getTopRated */

        Call<CreditsSeries> call = getCredits.getCreditsSeries(id, RetrofitInstance.getApiKey());
        /* Se hace una llamada asíncrona a la API */
        call.enqueue(new Callback<CreditsSeries>() {
            @Override
            public void onResponse(Call<CreditsSeries> call, Response<CreditsSeries> response) {
                switch (response.code()) {
                    /* En caso de respuesta correcta */
                    case 200:
                        /* En el objeto data de la clase MovieFeed se almacena el JSON convertido a objetos*/
                        CreditsSeries data = response.body();
                        /* Se actualizan los datos contenidos en el adaptador */
                        detallesAdapterActoresSeries.swap(data.getCast());
                        /* Se notifica un cambio de datos para que se refresque la vista */
                        detallesAdapterActoresSeries.notifyDataSetChanged();
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<CreditsSeries> call, Throwable t) {
                // Toast.makeText(MainActivity.this, "Error cargando películas", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void loadSearch_generos () {
        /* Crea la instanncia de retrofit */
        MovieService getGeneros = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
        /* Se definen los parámetros de la llamada a la función getTopRated */

        Call<DetailsSeries> call = getGeneros.getGenerosSeries(id, RetrofitInstance.getApiKey());
        /* Se hace una llamada asíncrona a la API */
        call.enqueue(new Callback<DetailsSeries>() {
            @Override
            public void onResponse(Call<DetailsSeries> call, Response<DetailsSeries> response) {
                switch (response.code()) {
                    /* En caso de respuesta correcta */
                    case 200:
                        /* En el objeto data de la clase MovieFeed se almacena el JSON convertido a objetos*/
                        DetailsSeries data = response.body();
                        /* Se actualizan los datos contenidos en el adaptador */
                        detallesAdapterGenerosSeries.swap(data.getGenres());
                        /* Se notifica un cambio de datos para que se refresque la vista */
                        detallesAdapterGenerosSeries.notifyDataSetChanged();
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<DetailsSeries> call, Throwable t) {
                // Toast.makeText(MainActivity.this, "Error cargando películas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}