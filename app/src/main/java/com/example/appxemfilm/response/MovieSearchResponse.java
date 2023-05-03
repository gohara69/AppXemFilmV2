package com.example.appxemfilm.response;

import androidx.annotation.NonNull;

import com.example.appxemfilm.model.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieSearchResponse {
    @SerializedName("results")
    @Expose()
    private List<MovieModel> movies;

    public List<MovieModel> getMovies() {
        return movies;
    }
}
