package com.example.appxemfilm.response;

import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.model.TokenStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TokenStatusResponse {
    @SerializedName("success")
    @Expose()
    private TokenStatus status;

    public TokenStatus getStatus() {
        return status;
    }
}
