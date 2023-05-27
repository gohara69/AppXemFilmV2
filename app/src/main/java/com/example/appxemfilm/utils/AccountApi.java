package com.example.appxemfilm.utils;

import com.example.appxemfilm.model.account;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AccountApi {
    @GET("account")
    Call<account> getAccout(
            @Query("api_key") String api_key,
            @Query("session_id") String language
    );
}
