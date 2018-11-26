package com.apps.indiclass.api;

import com.apps.indiclass.model.ChangePassRest;
import com.apps.indiclass.model.ClassRest;
import com.apps.indiclass.model.LoginResponse;
import com.apps.indiclass.model.DemandRest;
import com.apps.indiclass.model.LoginRest;
import com.apps.indiclass.model.SendReqRest;
import com.apps.indiclass.model.SessionRest;
import com.apps.indiclass.model.StaticDRest;
import com.apps.indiclass.model.SubjectRest;
import com.apps.indiclass.model.TutorRest;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Dell_Cleva on 08/11/2018.
 */

public interface ApiCall {

    @POST("login")
    @FormUrlEncoded
    Call<LoginRest> login(@Field("email") String email,
                          @Field("password") String password);

    @POST("scheduleone/access_token/{token}")
    @FormUrlEncoded
    Call<ClassRest> myclass(@Field("id_user") String id_user,
                            @Field("date") String date,
                            @Field("user_utc") String utc,
                            @Path("token") String stoken);

    @GET("allsubject_bytutor/id_user/{id_user}/access_token/{token}")
    Call<TutorRest> gettutor(@Path("id_user") String iduser, @Path("token") String token);

    @GET("allsubject/id_user/{id_user}/access_token/{token}/for/tutor")
    Call<SubjectRest> getsubject(@Path("id_user") String iduser, @Path("token") String token);

    @GET("ajax_get_ondemand")
    Call<DemandRest> requestsearch(@Query("subject_id") String subid, @Query("dtrq") String tgl,
                                   @Query("tmrq") String time, @Query("drrq") String dur,
                                   @Query("user_utc") String utc);
//"?start_time=" + ECTime + "&tutor_id=" + av_id + "&subject_id=" + ECSubid + "&duration=" + ECDuration + "&date="
//            + ECTgl + "&id_user=" + id_user + "&username=" + sUsername + "&user_utc=" + Method.getUTCTime() + "&access_token=" + sessionManager.getToken() + "&topic=" + ECTopic
//                + "&hr=" + ECTimes[position] + "&hrt=" + ECHargaT[position]
    @GET("requestprivate")
    Call<SendReqRest> sendreqprivate(@Query("start_time") String start_time, @Query("tutor_id") String tutor_id,
                                     @Query("subject_id") String subject_id, @Query("duration") String duration,
                                     @Query("date") String date, @Query("id_user") String id_user,
                                     @Query("user_utc") String user_utc, @Query("access_token") String access_token,
                                     @Query("hr") String hr, @Query("hrt") String hrt,
                                     @Query("topic") String topic);
    @POST("changePassword")
    @FormUrlEncoded
    Call<ChangePassRest> changePassword(@Field("id_user") String id_user, @Field("oldpass") String oldpass, @Field("newpass") String newpass);

    @POST("tps_me_r")
    @FormUrlEncoded
    Call<SessionRest> getSession(@Field("id_user") String id_user, @Field("class_id") String classid);

    @POST("get_static_data")
    @FormUrlEncoded
    Call<StaticDRest> getStaticData(@Field("session") String s);

    @POST("get_token")
    @FormUrlEncoded
    Call<StaticDRest> getToken(@Field("session") String s, @Field("user_utc") String utc, @Field("janus") String janusServer, @Field("usertype") String usertype,@Field("classid") String classid);
}
