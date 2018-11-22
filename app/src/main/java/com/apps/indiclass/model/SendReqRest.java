package com.apps.indiclass.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dell_Cleva on 13/11/2018.
 */

public class SendReqRest {

    private
    @SerializedName("status")
    boolean status;

    private
    @SerializedName("message")
    String message;

    @Override
    public String toString() {
        return "SendReqRest{" +
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
