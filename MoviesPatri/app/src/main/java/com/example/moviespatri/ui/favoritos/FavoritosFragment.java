package com.example.moviespatri.ui.favoritos;

import android.database.Cursor;
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
import com.example.moviespatri.ui.FavDbHelper_peliculas;
import com.example.moviespatri.ui.Favoritos_tab_peliculas;
import com.example.moviespatri.ui.SQLite_fav_peliculas;

import java.util.ArrayList;

public class FavoritosFragment extends Fragment {

    RecyclerView recyclerView_fav;
    FavoritosAdapter favoritosAdapter;
    private FavoritosViewModel favoritosViewModel;
    private Object FavoritosAdapter;
    SQLite_fav_peliculas sqlite;
    FavDbHelper_peliculas favdbh;
    ArrayList<Favoritos_tab_peliculas> array_fav;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritosViewModel =
                ViewModelProviders.of(this).get(FavoritosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favoritos, container, false);


        /* Enlaza el RecyclerView definido en el layout con el objeto recyclerView */
        recyclerView_fav = (RecyclerView) root.findViewById(R.id.recyclerview_favoritos);

        /* Establece que recyclerView tendrá un layout lineal, en concreto horizontal*/
        recyclerView_fav.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false));

        /*  Indica que cada uno de los items va a tener un tamaño fijo*/
        recyclerView_fav.setHasFixedSize(true);

        /* Establece la  decoración por defecto de cada uno de lo items: una línea de división*/
       // recyclerView_fav.addItemDecoration(new DividerItemDecoration(recyclerView_fav.getContext(), DividerItemDecoration.VERTICAL));

        /* Instancia un objeto de la clase MovieAdapterTopRated */
        favoritosAdapter = new FavoritosAdapter(root.getContext());

        /* Establece el adaptador asociado a recyclerView */
        recyclerView_fav.setAdapter(favoritosAdapter);

        /* Pone la animación por defecto de recyclerView */
        recyclerView_fav.setItemAnimator(new DefaultItemAnimator());


//        SQLiteDatabase db = favdbh.getWritableDatabase();
//        favdbh.onUpgrade(db,0,0);
//        sqlite.insertar(19404,  favdbh);

        sqlite = new SQLite_fav_peliculas();
        favdbh= new FavDbHelper_peliculas(getContext());

        loadSearch();

        return root;
    }



    public void loadSearch() {
        /* Crea la instanncia de retrofit */
        array_fav = new ArrayList();
        Cursor c = null;
        c = sqlite.consultar_all(favdbh,c);
        c.moveToFirst();
        for (int i = 0; i < sqlite.numero_fav(favdbh); i++) {
            //  while(c.moveToNext()){

            String id = c.getString(0);
            String titulo = c.getString(1);
            Float rating = c.getFloat(2);
            String url = c.getString(3);
            Favoritos_tab_peliculas fav = new Favoritos_tab_peliculas(id,titulo,rating,url);
            array_fav.add(fav);
            c.moveToNext();
            //   }
        }


      //  MovieFeed data = response.body();
////                                    /* Se actualizan los datos contenidos en el adaptador */
                                   favoritosAdapter.swap(array_fav);
////                                    /* Se notifica un cambio de datos para que se refresque la vista */
                                    favoritosAdapter.notifyDataSetChanged();
    }


//    public void loadSearch() {
//        /* Crea la instanncia de retrofit */
//        ArrayList<String> array_fav = new ArrayList();
//        Cursor c = null;
//        sqlite.consultar_all(favdbh, c);
//        c.moveToFirst();
//        for(int i = 0; i<sqlite.numero_fav(favdbh);i++){
//          //  while(c.moveToNext()){
//                int id = c.getInt(0);
//                String titulo = c.getString(1);
//                array_fav.add(titulo);
//                c.moveToNext();
//         //   }
//
//
//        }



//        MovieService getMovie = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
//        /* Se definen los parámetros de la llamada a la función getTopRated */
//        Cursor c;
//        c=sqlite.consultar_all(favdbh);
//        for(int i = 0;  i<sqlite.numero_fav(favdbh); i++) {
//           // Call<MovieFeed> call = getMovie.getTopRated(RetrofitInstance.getApiKey(), "es");
//            if (c.moveToFirst()) {
//                //Recorremos el cursor hasta que no haya más registros
//                do {
//                    int id = c.getInt(0);
//                    String titulo = c.getString(1);
//                   // Call<MovieFeed> call = getMovie.getFavoritos(id,RetrofitInstance.getApiKey());
//                    /* Se hace una llamada asíncrona a la API */
//                    //call.enqueue(new Callback<MovieFeed>() {
////                        @Override
////                        public void onResponse(Call<MovieFeed> call, Response<MovieFeed> response) {
////                            switch (response.code()) {
////                                /* En caso de respuesta correcta */
////                                case 200:
////                                    /* En el objeto data de la clase MovieFeed se almacena el JSON convertido a objetos*/
////                                    MovieFeed data = response.body();
////                                    /* Se actualizan los datos contenidos en el adaptador */
////                                    favoritosAdapter.swap(data.getResults());
////                                    /* Se notifica un cambio de datos para que se refresque la vista */
////                                    favoritosAdapter.notifyDataSetChanged();
////                                    break;
////                                case 401:
////                                    break;
////                                default:
////                                    break;
////                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<MovieFeed> call, Throwable t) {
//                            // Toast.makeText(MainActivity.this, "Error cargando películas", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } while(c.moveToNext());
//            }
//
//        }

   // }
}