package com.example.appxemfilm.response;

import com.example.appxemfilm.model.CastModel;
import com.example.appxemfilm.model.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilmByCastResponse {
    @SerializedName("cast")
    @Expose()
    private List<MovieModel> movies;

    public List<MovieModel> getMovies() {
        return movies;
    }
}
