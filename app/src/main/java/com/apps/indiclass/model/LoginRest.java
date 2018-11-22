package com.apps.indiclass.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dell_Cleva on 09/11/2018.
 */

public class LoginRest {

    private
    @SerializedName("status")
    boolean status;

    private
    @SerializedName("message")
    String message;

    private
    @SerializedName("data")
    LoginData loginData;

    private
    @SerializedName("access_token")
    String access_token;

    public LoginRest() {

    }

    @Override
    public String toString() {
        return "LoginRest{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", loginData=" + loginData +
                ", access_token='" + access_token + '\'' +
                '}';
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginData getLoginData() {
        return loginData;
    }

    public void setLoginData(LoginData loginData) {
        this.loginData = loginData;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
