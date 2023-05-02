package com.example.appxemfilm.response;

import com.example.appxemfilm.model.ChuDe;
import com.example.appxemfilm.model.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenreResponse {
    @SerializedName("genres")
    @Expose()
    private List<ChuDe> chuDes;

    public List<ChuDe> getChuDes() {
        return chuDes;
    }
}
