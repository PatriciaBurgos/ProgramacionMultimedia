package com.example.moviespatri.ui.peliculas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviespatri.R;
import com.example.moviespatri.models.MovieFeed;
import com.example.moviespatri.retrofit.MovieService;
import com.example.moviespatri.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeliculasFragment extends Fragment {
    RecyclerView recyclerView_toprated, recyclerView_recommended;
    MovieAdapterTopRated movieAdapterTopRated, movieAdapterRecommended;
    private PeliculasViewModel peliculasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        peliculasViewModel =ViewModelProviders.of(this).get(PeliculasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_peliculas, container, false);

        /* Enlaza el RecyclerView definido en el layout con el objeto recyclerView */
        recyclerView_toprated = (RecyclerView) root.findViewById(R.id.recycler_view_toprated);
        recyclerView_recommended = (RecyclerView) root.findViewById(R.id.recyclerView_recommended);

        /* Establece que recyclerView tendrá un layout lineal, en concreto horizontal*/
        recyclerView_toprated.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView_recommended.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false));

        /*  Indica que cada uno de los items va a tener un tamaño fijo*/
        recyclerView_toprated.setHasFixedSize(true);
        recyclerView_recommended.setHasFixedSize(true);

        /* Establece la  decoración por defecto de cada uno de lo items: una línea de división*/
        recyclerView_toprated.addItemDecoration(new DividerItemDecoration(recyclerView_toprated.getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerView_recommended.addItemDecoration(new DividerItemDecoration(recyclerView_recommended.getContext(), DividerItemDecoration.HORIZONTAL));

        /* Instancia un objeto de la clase MovieAdapterTopRated */
        movieAdapterTopRated = new MovieAdapterTopRated(root.getContext());
        movieAdapterRecommended = new MovieAdapterTopRated(root.getContext());

        /* Establece el adaptador asociado a recyclerView */
        recyclerView_toprated.setAdapter(movieAdapterTopRated);
        recyclerView_recommended.setAdapter(movieAdapterRecommended);

        /* Pone la animación por defecto de recyclerView */
        recyclerView_toprated.setItemAnimator(new DefaultItemAnimator());
        recyclerView_recommended.setItemAnimator(new DefaultItemAnimator());

        loadSearch_toprated();
        loadSearch_recommended();
        return root;
    }

    public void loadSearch_toprated () {
        /* Crea la instanncia de retrofit */
        MovieService getMovie = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
        /* Se definen los parámetros de la llamada a la función getTopRated */
        Call<MovieFeed> call = getMovie.getTopRated(RetrofitInstance.getApiKey(), "es");
        /* Se hace una llamada asíncrona a la API */
        call.enqueue(new Callback<MovieFeed>() {
            @Override
            public void onResponse(Call<MovieFeed> call, Response<MovieFeed> response) {
                switch (response.code()) {
                    /* En caso de respuesta correcta */
                    case 200:
                        /* En el objeto data de la clase MovieFeed se almacena el JSON convertido a objetos*/
                        MovieFeed data = response.body();
                        /* Se actualizan los datos contenidos en el adaptador */
                        movieAdapterTopRated.swap(data.getResults());
                        /* Se notifica un cambio de datos para que se refresque la vista */
                        movieAdapterTopRated.notifyDataSetChanged();
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<MovieFeed> call, Throwable t) {
               // Toast.makeText(MainActivity.this, "Error cargando películas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadSearch_recommended () {
        /* Crea la instanncia de retrofit */
        MovieService getMovie = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
        /* Se definen los parámetros de la llamada a la función getTopRated */
        Call<MovieFeed> call = getMovie.getRecommended(RetrofitInstance.getApiKey(), "es");
        /* Se hace una llamada asíncrona a la API */
        call.enqueue(new Callback<MovieFeed>() {
            @Override
            public void onResponse(Call<MovieFeed> call, Response<MovieFeed> response) {
                switch (response.code()) {
                    /* En caso de respuesta correcta */
                    case 200:
                        /* En el objeto data de la clase MovieFeed se almacena el JSON convertido a objetos*/
                        MovieFeed data = response.body();
                        /* Se actualizan los datos contenidos en el adaptador */
                        movieAdapterRecommended.swap(data.getResults());
                        /* Se notifica un cambio de datos para que se refresque la vista */
                        movieAdapterRecommended.notifyDataSetChanged();
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<MovieFeed> call, Throwable t) {
                // Toast.makeText(MainActivity.this, "Error cargando películas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}