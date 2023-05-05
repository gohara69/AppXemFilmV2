package com.example.appxemfilm.response;

import androidx.annotation.NonNull;

import com.example.appxemfilm.model.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieResponse {
    @SerializedName("result")
    @Expose()
    private MovieModel movie;

    public MovieModel getMovie() {
        return movie;
    }
}
