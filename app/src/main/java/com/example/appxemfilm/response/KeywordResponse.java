package com.example.appxemfilm.response;

import com.example.appxemfilm.model.Keyword;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KeywordResponse {
    @SerializedName("keywords")
    @Expose
    private List<Keyword> listKeyword;

    public List<Keyword> getListKeyword(){
        return listKeyword;
    }
}
