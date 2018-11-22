package com.apps.indiclass.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dell_Cleva on 09/11/2018.
 */

public class LoginData {

    private
    @SerializedName("id_user")
    int id_user;

    private
    @SerializedName("user_name")
    String user_name;

    private
    @SerializedName("user_image")
    String user_image;

    private
    @SerializedName("email")
    String email;

    private
    @SerializedName("usertype_id")
    String usertype_id;

    private
    @SerializedName("status")
    String status;

    private
    @SerializedName("jenjang_id")
    String jenjang_id;

    public LoginData() {

    }

    @Override
    public String toString() {
        return "LoginData{" +
                "id_user=" + id_user +
                ", user_name='" + user_name + '\'' +
                ", user_image='" + user_image + '\'' +
                ", email='" + email + '\'' +
                ", usertype_id='" + usertype_id + '\'' +
                ", status='" + status + '\'' +
                ", jenjang_id='" + jenjang_id + '\'' +
                '}';
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsertype_id() {
        return usertype_id;
    }

    public void setUsertype_id(String usertype_id) {
        this.usertype_id = usertype_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJenjang_id() {
        return jenjang_id;
    }

    public void setJenjang_id(String jenjang_id) {
        this.jenjang_id = jenjang_id;
    }
}
