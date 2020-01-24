package com.example.moviespatri.ui;

import android.provider.BaseColumns;

public class Favoritos_tab_peliculas {

    String id;
    String titulo;
    Float rating;
    String url_img;

    public Favoritos_tab_peliculas(String id, String titulo, Float rating, String url_img) {
        this.id = id;
        this.titulo = titulo;
        this.rating = rating;
        this.url_img = url_img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }
}
