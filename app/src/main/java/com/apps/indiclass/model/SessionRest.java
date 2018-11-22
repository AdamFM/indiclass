package com.apps.indiclass.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dell_Cleva on 19/11/2018.
 */

public class SessionRest {

    private
    @SerializedName("status")
    boolean status;
    private
    @SerializedName("message")
    String message;
    private
    @SerializedName("cm")
    String cm;
    private
    @SerializedName("access_session")
    String access_session;

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

    public String getCm() {
        return cm;
    }

    public void setCm(String cm) {
        this.cm = cm;
    }

    public String getAccess_session() {
        return access_session;
    }

    public void setAccess_session(String access_session) {
        this.access_session = access_session;
    }
}
