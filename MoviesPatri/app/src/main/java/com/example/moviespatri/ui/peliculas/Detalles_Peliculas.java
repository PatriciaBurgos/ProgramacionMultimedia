package com.example.moviespatri.ui.peliculas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviespatri.R;
import com.example.moviespatri.favoritos.com.example.moviespatri.Favoritos;
import com.example.moviespatri.models.CreditsFeed;
import com.example.moviespatri.models.MovieDetail;
import com.example.moviespatri.retrofit.MovieService;
import com.example.moviespatri.retrofit.RetrofitInstance;
import com.example.moviespatri.ui.FavDbHelper_peliculas;
import com.example.moviespatri.ui.Favoritos_tab_peliculas;
import com.example.moviespatri.ui.SQLite_fav_peliculas;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detalles_Peliculas extends AppCompatActivity implements OnLikeListener {

    RecyclerView recycler_view_actores, recycler_view_generos, recycler_view_studio;
    DetallesAdapter_Actores detallesAdapterActores;
    DetallesAdapter_Generos detallesAdapterGeneros;
    DetallesAdapter_Studio detallesAdapterStudio;
    DetallesViewModel detallesViewModel;

    TextView textViewNombre,textViewResumen,textViewAnio;
    ImageView imag;
    Button bot_ver_ahora;
    RatingBar ratingBar;
    LikeButton likeButton;
    SQLite_fav_peliculas sqlite;
    FavDbHelper_peliculas favdbh;
    String nombre,id,imagen;
    ArrayList<Favoritos_tab_peliculas> array_fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_pelicula);


        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        nombre = intent.getStringExtra("nombre");
        String resumen = intent.getStringExtra("resumen");
        imagen = intent.getStringExtra("imagen");
        String anio = intent.getStringExtra("anio");
        String puntuacion = intent.getStringExtra("puntuacion");

        favdbh = new FavDbHelper_peliculas(this);
        sqlite = new SQLite_fav_peliculas();
        array_fav = new ArrayList<>();

        // Capture the layout's TextView and set the string as its text
        textViewNombre = findViewById(R.id.textView_nombre);
        textViewResumen = findViewById(R.id.textView_resumen);
        imag = findViewById(R.id.imageView_pelicula);
        bot_ver_ahora = findViewById(R.id.button_verAhora);
        textViewAnio = findViewById(R.id.textView_anio);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        likeButton = (LikeButton) findViewById(R.id.star_button);


        textViewNombre.setText(nombre);
        textViewResumen.setText(resumen);
       // textViewStudio.setText(studio);
       // textViewGenero.setText(genero);
        textViewAnio.setText(anio);
        ratingBar.setNumStars(5);
        ratingBar.setRating(Float.parseFloat(puntuacion)/2);
        likeButton.setOnLikeListener(this);

        SQLiteDatabase db = favdbh.getWritableDatabase();
        //favdbh.onCreate(db);
        saber_si_el_corazon_esta_like();

        Picasso.get().load("https://image.tmdb.org/t/p/w500"+imagen).into(imag);


        detallesViewModel = ViewModelProviders.of(this).get(DetallesViewModel.class);

        /* Enlaza el RecyclerView definido en el layout con el objeto recyclerView */
        recycler_view_actores = (RecyclerView) findViewById(R.id.recycler_view_actores);
        recycler_view_generos = (RecyclerView) findViewById(R.id.recycler_view_generos);
        recycler_view_studio = (RecyclerView) findViewById(R.id.recycler_view_studio);

        /* Establece que recyclerView tendrá un layout lineal, en concreto horizontal*/
        recycler_view_actores.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recycler_view_actores.setLayoutManager(layoutManager);

        recycler_view_generos.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recycler_view_generos.setLayoutManager(layoutManager2);

        recycler_view_studio.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recycler_view_studio.setLayoutManager(layoutManager3);

        /*  Indica que cada uno de los items va a tener un tamaño fijo*/
        recycler_view_actores.setHasFixedSize(true);
        recycler_view_generos.setHasFixedSize(true);
        recycler_view_studio.setHasFixedSize(true);

        /* Instancia un objeto de la clase MovieAdapterTopRated */
        detallesAdapterActores = new DetallesAdapter_Actores(this);
        detallesAdapterGeneros = new DetallesAdapter_Generos(this);
        detallesAdapterStudio = new DetallesAdapter_Studio(this);

        /* Establece el adaptador asociado a recyclerView */
        recycler_view_actores.setAdapter(detallesAdapterActores);
        recycler_view_generos.setAdapter(detallesAdapterGeneros);
        recycler_view_studio.setAdapter(detallesAdapterStudio);

        /* Pone la animación por defecto de recyclerView */
        recycler_view_generos.setItemAnimator(new DefaultItemAnimator());
        recycler_view_studio.setItemAnimator(new DefaultItemAnimator());

        loadSearch_actores();
        loadSearch_generos();
        loadSearch_studio();
    }

    public void loadSearch_actores () {
        /* Crea la instanncia de retrofit */
        MovieService getCredits = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
        /* Se definen los parámetros de la llamada a la función getTopRated */

        Call<CreditsFeed> call = getCredits.getCredits(id,RetrofitInstance.getApiKey());
        /* Se hace una llamada asíncrona a la API */
        call.enqueue(new Callback<CreditsFeed>() {
            @Override
            public void onResponse(Call<CreditsFeed> call, Response<CreditsFeed> response) {
                switch (response.code()) {
                    /* En caso de respuesta correcta */
                    case 200:
                        /* En el objeto data de la clase MovieFeed se almacena el JSON convertido a objetos*/
                        CreditsFeed data = response.body();
                        /* Se actualizan los datos contenidos en el adaptador */
                        detallesAdapterActores.swap(data.getCast());
                        /* Se notifica un cambio de datos para que se refresque la vista */
                        detallesAdapterActores.notifyDataSetChanged();
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<CreditsFeed> call, Throwable t) {
                // Toast.makeText(MainActivity.this, "Error cargando películas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadSearch_generos () {
        /* Crea la instanncia de retrofit */
        MovieService getGeneros = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
        /* Se definen los parámetros de la llamada a la función getTopRated */

        Call<Favoritos> call = getGeneros.getGeneros(id, RetrofitInstance.getApiKey());
        /* Se hace una llamada asíncrona a la API */
        call.enqueue(new Callback<Favoritos>() {
            @Override
            public void onResponse(Call<Favoritos> call, Response<Favoritos> response) {
                switch (response.code()) {
                    /* En caso de respuesta correcta */
                    case 200:
                        /* En el objeto data de la clase MovieFeed se almacena el JSON convertido a objetos*/
                        Favoritos data = response.body();
                        /* Se actualizan los datos contenidos en el adaptador */
                        detallesAdapterGeneros.swap(data.getGenres());
                        /* Se notifica un cambio de datos para que se refresque la vista */
                        detallesAdapterGeneros.notifyDataSetChanged();
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<Favoritos> call, Throwable t) {
                // Toast.makeText(MainActivity.this, "Error cargando películas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadSearch_studio () {
        /* Crea la instanncia de retrofit */
        MovieService getStudio = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
        /* Se definen los parámetros de la llamada a la función getTopRated */

        Call<Favoritos> call = getStudio.getStudio(id, RetrofitInstance.getApiKey());
        /* Se hace una llamada asíncrona a la API */
        call.enqueue(new Callback<Favoritos>() {
            @Override
            public void onResponse(Call<Favoritos> call, Response<Favoritos> response) {
                switch (response.code()) {
                    /* En caso de respuesta correcta */
                    case 200:
                        /* En el objeto data de la clase MovieFeed se almacena el JSON convertido a objetos*/
                        Favoritos data = response.body();
                        /* Se actualizan los datos contenidos en el adaptador */
                        detallesAdapterStudio.swap(data.getProductionCompanies());
                        /* Se notifica un cambio de datos para que se refresque la vista */
                        detallesAdapterStudio.notifyDataSetChanged();
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<Favoritos> call, Throwable t) {
                // Toast.makeText(MainActivity.this, "Error cargando películas", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void liked(LikeButton likeButton) {
        Toast.makeText(this, "Liked!", Toast.LENGTH_SHORT).show();

        SQLiteDatabase db = favdbh.getWritableDatabase();
     //   favdbh.borrar_tabla (db);
        //favdbh.onCreate(db);
        //favdbh.onUpgrade(db,0,0);

        Float rat = ratingBar.getRating();
        sqlite.insertar(id, nombre, rat, imagen, favdbh);

    }

    @Override
    public void unLiked(LikeButton likeButton) {
        Toast.makeText(this, "Disliked!", Toast.LENGTH_SHORT).show();
        sqlite.eliminar_una_fila(favdbh,id);
    }

    public void saber_si_el_corazon_esta_like (){
        Cursor c = null;
        c = sqlite.consultar_all(favdbh,c);
        c.moveToFirst();
        for (int i = 0; i < sqlite.numero_fav(favdbh); i++) {
            String id = c.getString(0);
            String titulo = c.getString(1);
            Float rating = c.getFloat(2);
            String url = c.getString(3);
            Favoritos_tab_peliculas fav = new Favoritos_tab_peliculas(id,titulo,rating,url);
            array_fav.add(fav);
            c.moveToNext();
        }
        if(id.endsWith(".0")){
              id=id.substring(0,id.length()-2);
          }

        for(int i = 0; i<array_fav.size();i++) {
            if (id.equals(array_fav.get(i).getId())) {
                likeButton.setLiked(true);
            }
        }
    }
}
