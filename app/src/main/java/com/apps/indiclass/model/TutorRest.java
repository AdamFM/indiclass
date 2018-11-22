package com.apps.indiclass.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dell_Cleva on 12/11/2018.
 */

public class TutorRest {
    private
    @SerializedName("status")
    boolean status;

    private
    @SerializedName("message")
    String message;

    private
    @SerializedName("data")
    List<TutorData> tutorData;

    @Override
    public String toString() {
        return "TutorRest{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", tutorData=" + tutorData +
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

    public List<TutorData> getTutorData() {
        return tutorData;
    }

    public void setTutorData(List<TutorData> tutorData) {
        this.tutorData = tutorData;
    }
}
