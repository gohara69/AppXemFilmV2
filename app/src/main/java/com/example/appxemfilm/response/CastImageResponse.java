package com.example.appxemfilm.response;

import com.example.appxemfilm.model.CastImage;
import com.example.appxemfilm.model.CastModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastImageResponse {
    @SerializedName("profiles")
    @Expose()
    private List<CastImage> castImages;

    public List<CastImage> getCastImages() {
        return castImages;
    }
}
