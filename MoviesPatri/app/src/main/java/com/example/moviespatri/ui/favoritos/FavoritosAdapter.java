package com.example.moviespatri.ui.favoritos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.moviespatri.R;
import com.example.moviespatri.ui.Favoritos_tab_peliculas;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class FavoritosAdapter extends RecyclerView.Adapter<com.example.moviespatri.ui.favoritos.FavoritosAdapter.favoritosViewHolder> {
    /*
    Atributos
    */
    public final Context context; //Almacena el contexto donde se ejecutará
    private ArrayList<Favoritos_tab_peliculas> list; //Almacenará las películas a mostrar
    private FavoritosAdapter.OnItemClickListener listener; //Listener para cuando se haga click

    //Defino un interface con el OnItemClickListener
    public interface OnItemClickListener {
        void onItemClick(Favoritos_tab_peliculas movie);
    }

    /*
    Constructor
    */
    public FavoritosAdapter(Context context) {
        this.list = new ArrayList<Favoritos_tab_peliculas>();
        this.context = context;
        setListener();
    }

    /*
    Asocio al atributo listener el onItemClickListener correspondiente y sobreescribo el método onItemClick pasando como
    argumento una película
    */
    private void setListener () {
        this.listener = new OnItemClickListener() {
            @Override
            public void onItemClick(Favoritos_tab_peliculas movie) {
                //Toast.makeText(context,  movie.getTitle(), Toast.LENGTH_LONG).show();

                //Intent intent = new Intent(context, FavoritosFragment.class);
                //intent.putExtra("nombre", movie.getTitle());
                //context.startActivity(intent);
            }
        };
    }

    /*
    Sobreescribo onCreateViewHolder, donde  "inflo" la vista de cada item  y devuelve el viewholder que se crea pasándole la vista
    como parámetro
    */
    @Override
    public favoritosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_favoritos, parent, false);
        favoritosViewHolder tvh = new favoritosViewHolder(itemView);


        return tvh;
    }

    /*
    Sobreescribe onViewHolder, haciendo que muestre la película que hay en el arraylist list en la posición que pasamos como
    parámetro
     */
    @Override
    public void onBindViewHolder(favoritosViewHolder holder, int position) {

        final Favoritos_tab_peliculas movie = list.get(position);
       // Log.i("Mi id: ", String.valueOf(movie.get()));
        holder.bindMovie(movie);
    }

    /*
    Sobreescribe getItemCount devolviendo el número de películas que tenemos en el arraylist list.
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    /*
    Defino el viewHolder anidado que hereda de Recycler.ViewHolder necesario para que funcione el adaptador
     */
    public class favoritosViewHolder extends RecyclerView.ViewHolder {
        /*
        Atributos
         */
        TextView titulo;
        ImageView imag;
        RatingBar ratingBar;



        /*
            Constructor, enlazo los atributos con los elementos del layout
         */


        public favoritosViewHolder(View itemView) {
            super(itemView);

            titulo = (TextView) itemView.findViewById(R.id.textView_textfav);
            imag = itemView.findViewById(R.id.imageView4);
            ratingBar = itemView.findViewById(R.id.ratingBar_fav_pel);
        }

        /*
        Defino un método que servirá para poner los datos de la película en los correspondientes textviews del layout e
        invocará al listener del adaptador cuando se haga click sobre la vista del viewHolder.
         */
        public void bindMovie(final Favoritos_tab_peliculas movie) {
            System.out.println("Pelicula = " + movie.getTitulo());
            titulo.setText(movie.getTitulo());
            ratingBar.setNumStars(5);
            ratingBar.setRating((float) movie.getRating());
            Picasso.get().load("https://image.tmdb.org/t/p/w500"+movie.getUrl_img()).into(imag);

            /*Coloco el Listener a la vista)*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(movie);
                }
            });
        }
    }

    /*
    Método que limpia el array list de contenidos, carga los nuevos contenidos que se le pasan por parámetro e invoca a
    notifyDataSetChanged para hacer que se refresque la vista del RecyclerView
     */
    public void swap(List<Favoritos_tab_peliculas> newListMovies) {

        list.clear();
        //list.addAll(newListMovies);
        for(int i = 0; i<newListMovies.size();i++){
            list.add(newListMovies.get(i));
        }

        notifyDataSetChanged();
    }


}
