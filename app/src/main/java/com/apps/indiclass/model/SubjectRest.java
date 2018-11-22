package com.apps.indiclass.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dell_Cleva on 13/11/2018.
 */

public class SubjectRest {

    @SerializedName("status")
    private boolean status;

    @SerializedName("data")
    private List<SubjectData> subjectData;

    @Override
    public String toString() {
        return "SubjectRest{" +
                "status=" + status +
                ", subjectData=" + subjectData +
                '}';
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<SubjectData> getSubjectData() {
        return subjectData;
    }

    public void setSubjectData(List<SubjectData> subjectData) {
        this.subjectData = subjectData;
    }
}
