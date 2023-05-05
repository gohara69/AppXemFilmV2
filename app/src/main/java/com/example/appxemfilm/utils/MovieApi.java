package com.example.appxemfilm.utils;

import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.response.MovieResponse;
import com.example.appxemfilm.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int pageNumber,
            @Query("language") String language
    );

    @GET("movie/{movie_id}")
    Call<MovieModel> getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String key,
            @Query("language") String language
    );

    @GET("discover/movie")
    Call<MovieSearchResponse> getMoviesByGenresId(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") int pageNumber,
            @Query("with_genres") String withGenres
    );
}
