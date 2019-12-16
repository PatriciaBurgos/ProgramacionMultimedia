package com.example.moviespatri.ui.peliculas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.moviespatri.R;
import com.example.moviespatri.favoritos.com.example.moviespatri.Genre;
import com.example.moviespatri.favoritos.com.example.moviespatri.ProductionCompany;

import java.util.ArrayList;
import java.util.List;

public class DetallesAdapter_Studio extends RecyclerView.Adapter<DetallesAdapter_Studio.moviesViewHolder> {
        /*
        Atributos
        */
        public final Context context; //Almacena el contexto donde se ejecutará
        private List<ProductionCompany> list; //Almacenará los actores a mostrar
        private OnItemClickListener listener; //Listener para cuando se haga click

        //Defino un interface con el OnItemClickListener
        public interface OnItemClickListener {
            void onItemClick(ProductionCompany movie);
        }

        /*
        Constructor
        */
        public DetallesAdapter_Studio(Context context) {
            this.list = new ArrayList<ProductionCompany>();
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
                public void onItemClick(ProductionCompany movie) {
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

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_studio_peliculas,parent,false);
            moviesViewHolder tvh = new moviesViewHolder(itemView);


            return tvh;
        }

        /*
        Sobreescribe onViewHolder, haciendo que muestre la película que hay en el arraylist list en la posición que pasamos como
        parámetro
         */
        @Override
        public void onBindViewHolder(moviesViewHolder holder, int position) {

            final ProductionCompany movie = list.get(position);
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
                tvName = (TextView) itemView.findViewById(R.id.textView8);
            }

            /*
            Defino un método que servirá para poner los datos de la película en los correspondientes textviews del layout e
            invocará al listener del adaptador cuando se haga click sobre la vista del viewHolder.
             */
            public void bindMovie(final ProductionCompany movie, final OnItemClickListener listener) {
                tvName.setText(movie.getName()+" - ");

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
        public void swap(List<ProductionCompany> newListMovies) {
            list.clear();
            list.addAll(newListMovies);
            notifyDataSetChanged();
        }
    }