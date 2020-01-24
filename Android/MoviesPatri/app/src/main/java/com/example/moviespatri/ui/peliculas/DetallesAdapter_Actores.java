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
import com.example.moviespatri.models.CreditsListed;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;



public class DetallesAdapter_Actores extends RecyclerView.Adapter<DetallesAdapter_Actores.moviesViewHolder> {
        /*
        Atributos
        */
        public final Context context; //Almacena el contexto donde se ejecutará
        private ArrayList<CreditsListed> list; //Almacenará los actores a mostrar
        private DetallesAdapter_Actores.OnItemClickListener listener; //Listener para cuando se haga click

        //Defino un interface con el OnItemClickListener
        public interface OnItemClickListener {
            void onItemClick(CreditsListed movie);
        }

        /*
        Constructor
        */
        public DetallesAdapter_Actores(Context context) {
            this.list = new ArrayList<CreditsListed>();
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
                public void onItemClick(CreditsListed movie) {
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

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_actores_peliculas,parent,false);
            moviesViewHolder tvh = new moviesViewHolder(itemView);


            return tvh;
        }

        /*
        Sobreescribe onViewHolder, haciendo que muestre la película que hay en el arraylist list en la posición que pasamos como
        parámetro
         */
        @Override
        public void onBindViewHolder(moviesViewHolder holder, int position) {

            final CreditsListed movie = list.get(position);
            Log.i("Mi id: ", String.valueOf(movie.getId()));
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

            /*
                Constructor, enlazo los atributos con los elementos del layout
             */
            public moviesViewHolder(View itemView) {
                super(itemView);
                tvName = (TextView) itemView.findViewById(R.id.textView4);
                imag = (ImageView) itemView.findViewById(R.id.imageView3);
            }

            /*
            Defino un método que servirá para poner los datos de la película en los correspondientes textviews del layout e
            invocará al listener del adaptador cuando se haga click sobre la vista del viewHolder.
             */
            public void bindMovie(final CreditsListed movie, final OnItemClickListener listener) {
                tvName.setText(movie.getName());
                Picasso.get().load("https://image.tmdb.org/t/p/w500"+movie.getProfile_path()).into(imag);

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
        public void swap(List<CreditsListed> newListMovies) {
            list.clear();
            list.addAll(newListMovies);
            notifyDataSetChanged();
        }
    }
