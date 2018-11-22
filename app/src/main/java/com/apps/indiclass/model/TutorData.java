package com.apps.indiclass.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dell_Cleva on 12/11/2018.
 */

public class TutorData {
    @SerializedName("tutor_id")
    private String tutor_id;
    @SerializedName("tutor_name")
    private String tutor_name;
    @SerializedName("tutor_image")
    private String tutor_image;
    @SerializedName("tutor_country")
    private String tutor_country;

    @Override
    public String toString() {
        return "TutorData{" +
                "tutor_id='" + tutor_id + '\'' +
                ", tutor_name='" + tutor_name + '\'' +
                ", tutor_image='" + tutor_image + '\'' +
                ", tutor_country='" + tutor_country + '\'' +
                '}';
    }

    public String getTutor_id() {
        return tutor_id;
    }

    public void setTutor_id(String tutor_id) {
        this.tutor_id = tutor_id;
    }

    public String getTutor_name() {
        return tutor_name;
    }

    public void setTutor_name(String tutor_name) {
        this.tutor_name = tutor_name;
    }

    public String getTutor_image() {
        return tutor_image;
    }

    public void setTutor_image(String tutor_image) {
        this.tutor_image = tutor_image;
    }

    public String getTutor_country() {
        return tutor_country;
    }

    public void setTutor_country(String tutor_country) {
        this.tutor_country = tutor_country;
    }
}
