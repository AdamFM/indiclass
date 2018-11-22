package com.apps.indiclass.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dell_Cleva on 12/11/2018.
 */

public class ClassRest {

    private
    @SerializedName("status")
    boolean status;

    private
    @SerializedName("message")
    String message;

    private
    @SerializedName("data")
    List<ClassData> classData;

    @Override
    public String toString() {
        return "ClassRest{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", classData=" + classData +
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

    public List<ClassData> getClassData() {
        return classData;
    }

    public void setClassData(List<ClassData> classData) {
        this.classData = classData;
    }
}
