package com.apps.indiclass.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dell_Cleva on 19/11/2018.
 */

public class StaticDRes {
//    "janus": "c2.classmiles.com",
//            "id_user": "32",
//            "usertype": "tutor",
//            "display_name": "Robith Ritz",
//            "first_name": "Robith",
//            "display_email": "robithritz@gmail.com",
//            "class_id": "78767610",
//            "id_tutor": "32",
//            "class_name": "IPA",
//            "class_description": "Test Class C3",
//            "start_time": "2018-11-19 01:30:00",
//            "finish_time": "2018-11-19 19:00:00",
//            "endtimee": "19:00",
//            "end_time": "19:00:00",
//            "st_tour": "1",
//            "template_type": "all_featured",
//            "class_type": "multicast",
//            "nama_tutor": "Robith Ritz"

    //    body: "session=" + accessSession + "&user_utc=" + user_utc + "&janus=" + janusServer + "&usertype=student"+"&classid=" + classid
    @SerializedName("janus")
    private String janus;
    @SerializedName("class_id")
    private String class_id;
    @SerializedName("token")
    private String toen;
    @SerializedName("display_name")
    private String display_name;

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getToen() {
        return toen;
    }

    public void setToen(String toen) {
        this.toen = toen;
    }

    public String getJanus() {
        return janus;
    }

    public void setJanus(String janus) {
        this.janus = janus;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }
}
