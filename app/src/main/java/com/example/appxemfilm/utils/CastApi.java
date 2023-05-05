package com.example.appxemfilm.utils;

import com.example.appxemfilm.model.CastModel;
import com.example.appxemfilm.response.CastResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CastApi {
    @GET("movie/{movie_id}/credits")
    Call<CastResponse> getCasts(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key,
            @Query("language") String language
    );

    @GET("person/{person_id}")
    Call<CastModel> getCast(
            @Path("person_id") int person_id,
            @Query("api_key") String api_key,
            @Query("language") String language
    );
}
