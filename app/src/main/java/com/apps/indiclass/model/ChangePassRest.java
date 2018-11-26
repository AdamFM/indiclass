package com.apps.indiclass.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dell_Cleva on 23/11/2018.
 */

public class ChangePassRest {

    private
    @SerializedName("status")
    boolean status;

    private
    @SerializedName("message")
    String message;

    @Override
    public String toString() {
        return "ChangePassRest{" +
                "status=" + status +
                ", message='" + message + '\'' +
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
}
