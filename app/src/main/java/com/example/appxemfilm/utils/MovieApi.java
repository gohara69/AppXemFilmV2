package com.example.appxemfilm.utils;

import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.response.FilmVideoResponse;
import com.example.appxemfilm.response.KeywordResponse;
import com.example.appxemfilm.response.MovieResponse;
import com.example.appxemfilm.response.MovieSearchResponse;

import java.util.List;

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

    @GET("movie/{movie_id}/videos")
    Call<FilmVideoResponse> getFilmVideos(
            @Path("movie_id") int movie_id,
            @Query("api_key") String key
    );

    @GET("movie/{movie_id}/keywords")
    Call<KeywordResponse> getKeywords(
            @Path("movie_id") int movie_id,
            @Query("api_key") String key,
            @Query("language") String language
    );

    @GET("movie/{movie_id}/recommendations")
    Call<MovieSearchResponse> getRecommendations(
            @Path("movie_id") int movie_id,
            @Query("api_key") String key,
            @Query("page") int pageNumber,
            @Query("language") String language
    );

    @GET("movie/{movie_id}/similar")
    Call<MovieSearchResponse> getSimilar(
            @Path("movie_id") int movie_id,
            @Query("api_key") String key,
            @Query("page") int pageNumber,
            @Query("language") String language
    );

    @GET("movie/now_playing")
    Call<MovieSearchResponse> getMovieOnScreen(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") int pageNumber
            );

    @GET("trending/movie/day")
    Call<MovieSearchResponse> getMovieOnTrend(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") int pageNumber
    );

    @GET("movie/top_rated")
    Call<MovieSearchResponse> getMovieTopRate(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") int pageNumber
    );

    @GET("movie/popular")
    Call<MovieSearchResponse> getMovieOnPopular(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") int pageNumber,
            @Query("region") String region
    );
}
