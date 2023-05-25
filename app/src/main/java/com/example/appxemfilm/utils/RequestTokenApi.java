package com.example.appxemfilm.utils;

import com.example.appxemfilm.model.RequestToken;
import com.example.appxemfilm.model.Session;
import com.example.appxemfilm.model.TokenStatus;
import com.example.appxemfilm.response.TokenStatusResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestTokenApi {
    @GET("authentication/token/new")
    Call<RequestToken> getToken(
            @Query("api_key") String key
    );

    @POST("authentication/token/validate_with_login")
    Call<RequestToken> getTokenSignIn(
            @Query("api_key") String key,
            @Query("request_token") String requestToken,
            @Query("username") String userName,
            @Query("password") String passWord
    );

    @POST("authentication/token/validate_with_login")
    Call<TokenStatusResponse> getTokenStatus(
            @Query("api_key") String key,
            @Query("request_token") String requestToken,
            @Query("username") String userName,
            @Query("password") String passWord
    );

    @POST("authentication/session/new")
    Call<Session> getSession(
            @Query("api_key") String key,
            @Query("request_token") String requestToken
    );
}
