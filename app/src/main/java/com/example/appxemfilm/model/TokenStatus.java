package com.example.appxemfilm.model;

import com.google.gson.annotations.SerializedName;

public class TokenStatus {
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
