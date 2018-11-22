package com.apps.indiclass.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dell_Cleva on 13/11/2018.
 */

public class SubjectData {
//    "subject_id": "4",
//        "jenjang_id": "[\"1\",\"2\"]",
//        "subject_name": "Bahasa Indonesia Kelas 5 & 6",
//        "indonesia": "Bahasa Indonesia",
//        "english": "Bahasa",
//        "iconweb": "bahasaindonesia.png",
//        "icon": "indo_android.png",
//        "tutors": 1

    @SerializedName("subject_id")
    private String subject_id;
    @SerializedName("subject_name")
    private String subject_name;

    @Override
    public String toString() {
        return "SubjectData{" +
                "subject_id='" + subject_id + '\'' +
                ", subject_name='" + subject_name + '\'' +
                '}';
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }
}
