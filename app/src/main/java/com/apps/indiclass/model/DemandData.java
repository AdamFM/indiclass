package com.apps.indiclass.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dell_Cleva on 13/11/2018.
 */

public class DemandData {
//    "uid": "1",
//    "tutor_id": "32",
//    "subject_id": "6",
//    "class_type": "private",
//    "harga": "20000",
//    "harga_cm": 5000,
//    "created_at": "2017-07-20 12:36:28",
//    "jenjang_id": "[\"1\",\"2\"]",
//    "subject_name": "Matematika Kelas 5 & 6",
//    "user_name": "Robith Ritz",
//    "status": "1",
//    "country": "Indonesia",
//    "user_image": "https://cdn.classmiles.com/usercontent/dXNlci8zMnJ0dUlmT1VuLmpwZw==",
//    "harga_tutor": 0,
//    "hargaakhir": "5.000",

    @SerializedName("tutor_id")
    private String tutor_id;

    @SerializedName("user_name")
    private String user_name;

    @SerializedName("user_image")
    private String user_image;

    @SerializedName("harga_cm")
    private String harga;

    @Override
    public String toString() {
        return "DemandData{" +
                "tutor_id='" + tutor_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_image='" + user_image + '\'' +
                ", harga='" + harga + '\'' +
                '}';
    }

    public String getTutor_id() {
        return tutor_id;
    }

    public void setTutor_id(String tutor_id) {
        this.tutor_id = tutor_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
