package com.example.moviespatri.ui.peliculas;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.moviespatri.R;
import com.example.moviespatri.favoritos.com.example.moviespatri.Genre;
import com.example.moviespatri.models.CreditsListed;
import com.example.moviespatri.models.MovieDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetallesAdapter_Generos extends RecyclerView.Adapter<DetallesAdapter_Generos.moviesViewHolder> {
        /*
        Atributos
        */
        public final Context context; //Almacena el contexto donde se ejecutará
        private List<Genre> list; //Almacenará los actores a mostrar
        private OnItemClickListener listener; //Listener para cuando se haga click

        //Defino un interface con el OnItemClickListener
        public interface OnItemClickListener {
            void onItemClick(Genre movie);
        }

        /*
        Constructor
        */
        public DetallesAdapter_Generos(Context context) {
            this.list = new ArrayList<Genre>();
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
                public void onItemClick(Genre movie) {
                    //Toast.makeText(context,  movie.getTitle(), Toast.LENGTH_LONG).show();

                }
            };
        }

        /*
        Sobreescribo onCreateViewHolder, donde  "inflo" la vista de cada item  y devuelve el viewholder que se crea pasándole la vista
        como parámetro
        */
        @Override
        public moviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_generos_peliculas,parent,false);
            moviesViewHolder tvh = new moviesViewHolder(itemView);


            return tvh;
        }

        /*
        Sobreescribe onViewHolder, haciendo que muestre la película que hay en el arraylist list en la posición que pasamos como
        parámetro
         */
        @Override
        public void onBindViewHolder(moviesViewHolder holder, int position) {

            final Genre movie = list.get(position);
           // Log.i("Mi id: ", String.valueOf(movie.getId()));
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

            /*
                Constructor, enlazo los atributos con los elementos del layout
             */
            public moviesViewHolder(View itemView) {
                super(itemView);
                tvName = (TextView) itemView.findViewById(R.id.textView7);
            }

            /*
            Defino un método que servirá para poner los datos de la película en los correspondientes textviews del layout e
            invocará al listener del adaptador cuando se haga click sobre la vista del viewHolder.
             */
            public void bindMovie(final Genre movie, final OnItemClickListener listener) {
                tvName.setText(movie.getName()+" ");

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
        public void swap(List<Genre> newListMovies) {
            list.clear();
            list.addAll(newListMovies);
            notifyDataSetChanged();
        }
    }