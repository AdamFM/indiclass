package com.apps.indiclass.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dell_Cleva on 12/11/2018.
 */

public class ClassData {

    private
    @SerializedName("class_id")
    String class_id;

    private
    @SerializedName("user_name")
    String tutorName;

    private
    @SerializedName("subject_name")
    String subject_name;

    private
    @SerializedName("description")
    String description;

    private
    @SerializedName("date")
    String date;

    private
    @SerializedName("time")
    String time;

    private
    @SerializedName("enddate")
    String enddate;

    private
    @SerializedName("endtime")
    String endtime;

    private
    @SerializedName("tutor_id")
    String tutor_id;

    private
    @SerializedName("user_image")
    String user_image;

    @Override
    public String toString() {
        return "ClassData{" +
                "class_id='" + class_id + '\'' +
                ", tutorName='" + tutorName + '\'' +
                ", subject_name='" + subject_name + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", enddate='" + enddate + '\'' +
                ", endtime='" + endtime + '\'' +
                ", tutor_id='" + tutor_id + '\'' +
                ", user_image='" + user_image + '\'' +
                '}';
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getTutor_id() {
        return tutor_id;
    }

    public void setTutor_id(String tutor_id) {
        this.tutor_id = tutor_id;
    }
}
