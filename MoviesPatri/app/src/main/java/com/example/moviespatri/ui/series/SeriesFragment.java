package com.example.moviespatri.ui.series;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviespatri.R;
import com.example.moviespatri.models.MovieFeed;
import com.example.moviespatri.retrofit.MovieService;
import com.example.moviespatri.retrofit.RetrofitInstance;
import com.example.moviespatri.series_popular.com.example.moviespatri.SeriesPopular;
import com.example.moviespatri.series_toprated.com.example.moviespatri.SeriesToprated;
import com.example.moviespatri.ui.peliculas.MovieAdapterTopRated;
import com.example.moviespatri.ui.peliculas.PeliculasViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeriesFragment extends Fragment {
    RecyclerView recycler_view_top_rated_series, recyclerView_recommended;
    SeriesAdapterTopRated seriesAdapterTopRated;
    SeriesAdapterRecommender seriesAdapterRecommender;
    private SeriesViewModel seriesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        seriesViewModel = ViewModelProviders.of(this).get(SeriesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_series, container, false);

        /* Enlaza el RecyclerView definido en el layout con el objeto recyclerView */
        recycler_view_top_rated_series = (RecyclerView) root.findViewById(R.id.recycler_view_toprated_series);
        recyclerView_recommended = (RecyclerView) root.findViewById(R.id.recyclerView_recommended_series);

        /* Establece que recyclerView tendrá un layout lineal, en concreto horizontal*/
        recycler_view_top_rated_series.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView_recommended.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false));

        /*  Indica que cada uno de los items va a tener un tamaño fijo*/
        recycler_view_top_rated_series.setHasFixedSize(true);
        recyclerView_recommended.setHasFixedSize(true);

        /* Establece la  decoración por defecto de cada uno de lo items: una línea de división*/
        recycler_view_top_rated_series.addItemDecoration(new DividerItemDecoration(recycler_view_top_rated_series.getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerView_recommended.addItemDecoration(new DividerItemDecoration(recyclerView_recommended.getContext(), DividerItemDecoration.HORIZONTAL));

        /* Instancia un objeto de la clase MovieAdapterTopRated */
        seriesAdapterTopRated = new SeriesAdapterTopRated(root.getContext());
        seriesAdapterRecommender = new SeriesAdapterRecommender(root.getContext());

        /* Establece el adaptador asociado a recyclerView */
        recycler_view_top_rated_series.setAdapter(seriesAdapterTopRated);
        recyclerView_recommended.setAdapter(seriesAdapterRecommender);

        /* Pone la animación por defecto de recyclerView */
        recycler_view_top_rated_series.setItemAnimator(new DefaultItemAnimator());
        recyclerView_recommended.setItemAnimator(new DefaultItemAnimator());

        loadSearch_toprated();
        loadSearch_recommended();
        return root;
    }

    public void loadSearch_toprated() {
        /* Crea la instanncia de retrofit */
        MovieService getMovie = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
        /* Se definen los parámetros de la llamada a la función getTopRated */
        Call<SeriesToprated> call = getMovie.getTopRatedSeries(RetrofitInstance.getApiKey(), "es");
        /* Se hace una llamada asíncrona a la API */
        call.enqueue(new Callback<SeriesToprated>() {
            @Override
            public void onResponse(Call<SeriesToprated> call, Response<SeriesToprated> response) {
                switch (response.code()) {
                    /* En caso de respuesta correcta */
                    case 200:
                        /* En el objeto data de la clase MovieFeed se almacena el JSON convertido a objetos*/
                        SeriesToprated data = response.body();
                        /* Se actualizan los datos contenidos en el adaptador */
                        seriesAdapterTopRated.swap(data.getResults());
                        /* Se notifica un cambio de datos para que se refresque la vista */
                        seriesAdapterTopRated.notifyDataSetChanged();
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<SeriesToprated> call, Throwable t) {
                // Toast.makeText(MainActivity.this, "Error cargando películas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadSearch_recommended () {
        /* Crea la instanncia de retrofit */
        MovieService getMovie = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
        /* Se definen los parámetros de la llamada a la función getTopRated */
        Call<SeriesPopular> call = getMovie.getRecommendedSeries(RetrofitInstance.getApiKey(), "es");
        /* Se hace una llamada asíncrona a la API */
        call.enqueue(new Callback<SeriesPopular>() {
            @Override
            public void onResponse(Call<SeriesPopular> call, Response<SeriesPopular> response) {
                switch (response.code()) {
                    /* En caso de respuesta correcta */
                    case 200:
                        /* En el objeto data de la clase MovieFeed se almacena el JSON convertido a objetos*/
                        SeriesPopular data = response.body();
                        /* Se actualizan los datos contenidos en el adaptador */
                        seriesAdapterRecommender.swap(data.getResults());
                        /* Se notifica un cambio de datos para que se refresque la vista */
                        seriesAdapterRecommender.notifyDataSetChanged();
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<SeriesPopular> call, Throwable t) {
                // Toast.makeText(MainActivity.this, "Error cargando películas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}