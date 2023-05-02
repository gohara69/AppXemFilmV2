package com.example.appxemfilm.utils;

import com.example.appxemfilm.response.GenreResponse;
import com.example.appxemfilm.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GenreApi {

    @GET("genre/movie/list")
    Call<GenreResponse> getGenres(
            @Query("api_key") String key,
            @Query("language") String language
    );
}
