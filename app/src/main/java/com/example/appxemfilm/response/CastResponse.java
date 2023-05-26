package com.example.appxemfilm.response;

import com.example.appxemfilm.model.CastModel;
import com.example.appxemfilm.model.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastResponse {
    @SerializedName("cast")
    @Expose()
    private List<CastModel> casts;

    @SerializedName("results")
    @Expose()
    private List<CastModel> results;

    public List<CastModel> getCasts() {
        return casts;
    }
    public List<CastModel> getResults() {
        return results;
    }
}
