package com.example.appxemfilm.model;

import com.google.gson.annotations.SerializedName;

public class RequestToken {
    @SerializedName("success")
    private boolean success;

    @SerializedName("expires_at")
    private String expires_at;

    @SerializedName("request_token")
    private String request_token;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(String expires_at) {
        this.expires_at = expires_at;
    }

    public String getRequest_token() {
        return request_token;
    }

    public void setRequest_token(String request_token) {
        this.request_token = request_token;
    }
}
