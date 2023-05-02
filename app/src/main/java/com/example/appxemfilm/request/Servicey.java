package com.example.appxemfilm.request;

import com.example.appxemfilm.utils.Credentials;
import com.example.appxemfilm.utils.GenreApi;
import com.example.appxemfilm.utils.MovieApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Servicey {
    public Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());


    public Retrofit retrofit = retrofitBuilder.build();

    public MovieApi movieApi = retrofit.create(MovieApi.class);

    public GenreApi genreApi = retrofit.create(GenreApi.class);

    public MovieApi getMovieApi(){
        return movieApi;
    }

    public GenreApi getGenreApi() {return genreApi; }
}
