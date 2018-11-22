package com.apps.indiclass.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dell_Cleva on 14/11/2018.
 */

public class LoginResponse {
    @SerializedName("response")
    private LoginRest loginRest;

    @Override
    public String toString() {
        return "LoginResponse{" +
                "loginRest=" + loginRest +
                '}';
    }

    public LoginRest getLoginRest() {
        return loginRest;
    }

    public void setLoginRest(LoginRest loginRest) {
        this.loginRest = loginRest;
    }
}
