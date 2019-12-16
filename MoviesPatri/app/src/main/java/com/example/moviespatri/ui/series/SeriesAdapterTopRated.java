package com.example.moviespatri.ui.series;

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
import com.example.moviespatri.models.MovieListed;
import com.example.moviespatri.series_toprated.com.example.moviespatri.Result;
import com.example.moviespatri.series_toprated.com.example.moviespatri.SeriesToprated;
import com.example.moviespatri.ui.peliculas.Detalles_Peliculas;
import com.example.moviespatri.ui.peliculas.MovieAdapterTopRated;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SeriesAdapterTopRated extends RecyclerView.Adapter<SeriesAdapterTopRated.moviesViewHolder> {
        /*
        Atributos
        */
        public final Context context; //Almacena el contexto donde se ejecutará
        private ArrayList<Result> list; //Almacenará las películas a mostrar
        private OnItemClickListener listener; //Listener para cuando se haga click

        //Defino un interface con el OnItemClickListener
        public interface OnItemClickListener {
            void onItemClick(Result movie);
        }

        /*
        Constructor
        */
        public SeriesAdapterTopRated(Context context) {
            this.list = new ArrayList<Result>();
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
                public void onItemClick(Result movie) {
                    //Toast.makeText(context,  movie.getTitle(), Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(context, Detalles_Series.class);
                    intent.putExtra("id",String.valueOf(movie.getId()));
                    intent.putExtra("nombre", movie.getName());
                    intent.putExtra("resumen", movie.getOverview());
                    intent.putExtra("imagen", movie.getBackdropPath());
                    intent.putExtra("puntuacion", Double.toString(movie.getVoteAverage()));
                    intent.putExtra("fecha", movie.getFirstAirDate());
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

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_series_item, parent, false);
            moviesViewHolder tvh = new moviesViewHolder(itemView);


            return tvh;
        }

        /*
        Sobreescribe onViewHolder, haciendo que muestre la película que hay en el arraylist list en la posición que pasamos como
        parámetro
         */
        @Override
        public void onBindViewHolder(moviesViewHolder holder, int position) {

            final Result movie = list.get(position);
            //Log.i("Mi id: ", String.valueOf(movie.getId()));
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
                tvName = (TextView) itemView.findViewById(R.id.tv_name_series);
                imag = (ImageView) itemView.findViewById(R.id.imageView_series);
                ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar_series);
            }

            /*
            Defino un método que servirá para poner los datos de la película en los correspondientes textviews del layout e
            invocará al listener del adaptador cuando se haga click sobre la vista del viewHolder.
             */
            public void bindMovie(final Result movie, final OnItemClickListener listener) {
                tvName.setText(movie.getName());
                ratingBar.setNumStars(5);
                ratingBar.setRating((float) (movie.getVoteAverage()/2));
                Picasso.get().load("https://image.tmdb.org/t/p/w500"+movie.getPosterPath()).into(imag);

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
        public void swap(List<Result> newListMovies) {
            list.clear();
            list.addAll(newListMovies);
            notifyDataSetChanged();
        }
    }
