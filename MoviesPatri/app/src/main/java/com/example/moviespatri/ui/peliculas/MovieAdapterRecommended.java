package com.example.moviespatri.ui.peliculas;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.moviespatri.R;
import com.example.moviespatri.models.Genre;
import com.example.moviespatri.models.MovieListed;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/*
    Defino un adaptador que hereda de RecyclerView.Adaptar y que definirá una clase aninada llamada moviesViewHolder
 */
public class MovieAdapterRecommended extends RecyclerView.Adapter<MovieAdapterRecommended.moviesViewHolder> {
    /*
    Atributos
    */
    public final Context context; //Almacena el contexto donde se ejecutará
    private ArrayList<MovieListed> list; //Almacenará las películas a mostrar

    private MovieAdapterRecommended.OnItemClickListener listener; //Listener para cuando se haga click

    //Defino un interface con el OnItemClickListener
    public interface OnItemClickListener {
        void onItemClick(MovieListed movie);
    }

    /*
    Constructor
    */
    public MovieAdapterRecommended(Context context) {
        this.list = new ArrayList<MovieListed>();

        this.context = context;
        setListener();
    }

    /*
    Asocio al atributo listener el onItemClickListener correspondiente y sobreescribo el método onItemClick pasando como
    argumento una película
    */
    private void setListener () {
        this.listener = new MovieAdapterRecommended.OnItemClickListener() {
            @Override
            public void onItemClick(MovieListed movie) {
                //Toast.makeText(context,  movie.getTitle(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context, Detalles_Peliculas.class);
                intent.putExtra("id",String.valueOf(movie.getId()));
                intent.putExtra("nombre", movie.getTitle());
                intent.putExtra("resumen", movie.getOverview());
                intent.putExtra("imagen", movie.getBackdrop_path());
                intent.putExtra("anio", movie.getRelease_date());
                intent.putExtra("puntuacion", Float.toString(movie.getVote_average()));
                context.startActivity(intent);

            }
        };
    }

    /*
    Sobreescribo onCreateViewHolder, donde  "inflo" la vista de cada item  y devuelve el viewholder que se crea pasándole la vista
    como parámetro
    */
    @Override
    public moviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movie_item, parent, false);
        moviesViewHolder tvh = new moviesViewHolder(itemView);


        return tvh;
    }

    /*
    Sobreescribe onViewHolder, haciendo que muestre la película que hay en el arraylist list en la posición que pasamos como
    parámetro
     */
    @Override
    public void onBindViewHolder(moviesViewHolder holder, int position) {

        final MovieListed movie = list.get(position);
        Log .i("Mi id: ", String.valueOf(movie.getId()));
        holder.bindMovie(movie, listener);
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
    public class moviesViewHolder extends RecyclerView.ViewHolder {
        /*
        Atributos
         */
        TextView tvName;
        ImageView imag;
        RatingBar ratingBar;

        /*
            Constructor, enlazo los atributos con los elementos del layout
         */
        public moviesViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            imag = (ImageView) itemView.findViewById(R.id.imageView);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
        }

        /*
        Defino un método que servirá para poner los datos de la película en los correspondientes textviews del layout e
        invocará al listener del adaptador cuando se haga click sobre la vista del viewHolder.
         */
        public void bindMovie(final MovieListed movie, final OnItemClickListener listener) {
            tvName.setText(movie.getTitle());
            ratingBar.setNumStars(5);
            ratingBar.setRating((float) movie.getVote_average()/2);
            Picasso.get().load("https://image.tmdb.org/t/p/w500"+movie.getPoster_path()).into(imag);

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
    public void swap(List<MovieListed> newListMovies) {
        list.clear();
        list.addAll(newListMovies);
        notifyDataSetChanged();
    }
}