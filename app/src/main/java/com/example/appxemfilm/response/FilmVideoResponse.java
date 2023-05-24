package com.example.appxemfilm.response;

import com.example.appxemfilm.model.CastImage;
import com.example.appxemfilm.model.FilmVideoUrl;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilmVideoResponse {
    @SerializedName("results")
    @Expose()
    private List<FilmVideoUrl> listUrl;

    public List<FilmVideoUrl> getListUrl() {
        return listUrl;
    }
}
