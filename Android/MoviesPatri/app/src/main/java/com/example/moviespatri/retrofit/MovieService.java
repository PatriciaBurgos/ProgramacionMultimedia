package com.example.moviespatri.retrofit;


import com.example.moviespatri.credits.com.example.moviespatri.Credits;
import com.example.moviespatri.credits_series.com.example.moviespatri.CreditsSeries;
import com.example.moviespatri.details_series.com.example.moviespatri.DetailsSeries;
import com.example.moviespatri.favoritos.com.example.moviespatri.Favoritos;
import com.example.moviespatri.models.CreditsFeed;
import com.example.moviespatri.models.MovieDetail;
import com.example.moviespatri.models.MovieFeed;
import com.example.moviespatri.models.MovieListed;
import com.example.moviespatri.series_popular.com.example.moviespatri.SeriesPopular;
import com.example.moviespatri.series_toprated.com.example.moviespatri.SeriesToprated;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("movie/top_rated")
    Call<MovieFeed> getTopRated(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("movie/popular")
    Call<MovieFeed> getRecommended(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("movie/{id}/credits")
    Call<CreditsFeed> getCredits(@Path ("id") String id_movie, @Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<Favoritos> getGeneros(@Path ("id") String id_movie, @Query("api_key") String apiKey);

    @GET ("movie/{id}")
    Call <Favoritos> getStudio(@Path ("id") String id_movie, @Query("api_key") String apiKey);

    @GET ("tv/top_rated")
    Call <SeriesToprated> getTopRatedSeries(@Query("api_key") String apiKey, @Query("language") String language);

    @GET ("tv/popular")
    Call <SeriesPopular> getRecommendedSeries(@Query("api_key") String apiKey, @Query("language") String language);

    @GET ("tv/{tv_id}/credits")
    Call<CreditsSeries> getCreditsSeries(@Path ("tv_id") String tv_id, @Query("api_key") String apiKey);

    @GET("tv/{tv_id}")
    Call<DetailsSeries> getGenerosSeries(@Path ("tv_id") String tv_id, @Query("api_key") String apiKey);

}