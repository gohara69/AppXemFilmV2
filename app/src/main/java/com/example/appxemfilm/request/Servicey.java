package com.example.appxemfilm.request;

import com.example.appxemfilm.utils.AccountApi;
import com.example.appxemfilm.utils.CastApi;
import com.example.appxemfilm.utils.Credentials;
import com.example.appxemfilm.utils.GenreApi;
import com.example.appxemfilm.utils.MovieApi;
import com.example.appxemfilm.utils.RequestTokenApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Servicey {
    public Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());


    public Retrofit retrofit = retrofitBuilder.build();

    public MovieApi movieApi = retrofit.create(MovieApi.class);

    public GenreApi genreApi = retrofit.create(GenreApi.class);

    public CastApi castApi = retrofit.create(CastApi.class);

    public RequestTokenApi requestTokenApi = retrofit.create(RequestTokenApi.class);

    public AccountApi accountApi = retrofit.create(AccountApi.class);

    public MovieApi getMovieApi(){
        return movieApi;
    }

    public GenreApi getGenreApi() {return genreApi; }

    public CastApi getCastApi() {return castApi; }

    public RequestTokenApi getRequestTokenApi() {
        return requestTokenApi;
    }

    public AccountApi getAccountApi() {
        return accountApi;
    }

}
