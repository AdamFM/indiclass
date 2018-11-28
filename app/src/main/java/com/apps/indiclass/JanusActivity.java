package com.apps.indiclass;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.opengl.EGLContext;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.apps.indiclass.api.ApiCall;
import com.apps.indiclass.api.ApiClient;
import com.apps.indiclass.api.ApiUtils;
import com.apps.indiclass.model.SessionRest;
import com.apps.indiclass.model.StaticDRest;
import com.apps.indiclass.util.Method;
import com.apps.indiclass.util.SessionManager;

import org.webrtc.VideoRenderer;
import org.webrtc.VideoRendererGui;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class JanusActivity extends Activity {
    private static final String TAG = "JanusActivity";
    String name = "", token = "";
    String id_user, sToken;
    String sSession;
    int roomid;
    ApiCall mAPIService, mApi2;
    Retrofit retrofit,retrofit2;
    SessionManager session;
    private GLSurfaceView vsv;
    private VideoRenderer.Callbacks localRender;

//    private VideoRoomTest videoRoomTest;
    private VideoRenderer.Callbacks remoteRender;
    // private EchoTest echoTest;
    private VideoRoomTest videoRoomTest;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        videoRoomTest.Disconnect();

    }

    String wb ="https://c2.classmiles.com/wb/78767615/session/a855b79ac3be68ee747085adb803c4be9ea9a74e/ut/tutor/ct/id.classmiles.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        java.lang.System.setProperty("java.net.preferIPv6Addresses", "false");
        java.lang.System.setProperty("java.net.preferIPv4Stack", "true");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_janus);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        token = getIntent().getStringExtra("token");
        name = getIntent().getStringExtra("name");
        roomid = Integer.parseInt(getIntent().getStringExtra("class_id"));
        Log.w(TAG, "onCreate: TOKEN " + token );

        vsv = findViewById(R.id.glview);
        vsv.setPreserveEGLContextOnPause(true);
        vsv.setKeepScreenOn(true);

//        vsv.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
//        vsv.getHolder().setFormat(PixelFormat.RGBA_8888);
        vsv.setZOrderOnTop(true);
        VideoRendererGui.setView(vsv, new MyInit());

        localRender = VideoRendererGui.create(0, 52, 100, 25, VideoRendererGui.ScalingType.SCALE_ASPECT_FIT, false);
        remoteRender = VideoRendererGui.create(0, 0, 100, 45, VideoRendererGui.ScalingType.SCALE_ASPECT_FIT, true);

        session = new SessionManager(this);
        id_user = session.getIds();
        sToken = session.getToken();
//        mAPIService = ApiUtils.getRestAPI("https://indiclass.co.id/V1.0.0/");
//        mApi2 = ApiUtils.getRestAPI("https://id.classmiles.com/Rest/");
//
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
//
//        retrofit = new Retrofit.Builder()
//                .baseUrl("https://indiclass.id/V1.0.0/")
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        retrofit2 = new Retrofit.Builder()
//                .baseUrl("https://id.classmiles.com/Rest/")
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        mAPIService = retrofit.create(ApiCall.class);
//
//        mApi2 = retrofit2.create(ApiCall.class);
//
//        mAPIService.getSession(id_user, String.valueOf(roomid)).enqueue(new Callback<SessionRest>() {
//            @Override
//            public void onResponse(Call<SessionRest> call, Response<SessionRest> response) {
//                if (response.body() != null) {
//                    if (response.body().getAccess_session() != null) {
//                        sSession = response.body().getAccess_session();
//                        mApi2.getStaticData(response.body().getAccess_session()).enqueue(new Callback<StaticDRest>() {
//                            @Override
//                            public void onResponse(Call<StaticDRest> call, Response<StaticDRest> response) {
//                                if (response.body() != null) {
//                                    if (response.body().getStaticDRes() != null) {
//
//                                        name = response.body().getStaticDRes().getDisplay_name();
////                                        roomid = Integer.valueOf(response.body().getStaticDRes().getClass_id());
//                                        getToken(response.body().getStaticDRes().getJanus());
//                                    }
//
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<StaticDRest> call, Throwable t) {
//
//                            }
//                        });
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SessionRest> call, Throwable t) {
//
//            }
//        });
    }

    public void getToken(final String janus) {
        mApi2.getToken(sSession, Method.getUTCTime(), janus, "student", String.valueOf(roomid)).enqueue(new Callback<StaticDRest>() {
            @Override
            public void onResponse(Call<StaticDRest> call, Response<StaticDRest> response) {
                if (response.body() != null) {
                    if (response.body().getStaticDRes() != null) {
                        if (response.body().getStaticDRes().getToen() != null) {
                            token = response.body().getStaticDRes().getToen();
                        }
                    } else
                        getToken(janus);
                }
            }

            @Override
            public void onFailure(Call<StaticDRest> call, Throwable t) {

            }
        });
    }

    private class MyInit implements Runnable {

        public void run() {
            init();
        }

        private void init() {
            try {
                EGLContext con = VideoRendererGui.getEGLContext();
//                echoTest = new EchoTest(localRender, remoteRender);
//                echoTest.initializeMediaContext(JanusActivity.this, true, true, true, con);
//                echoTest.Start();
                VideoRenderer.Callbacks[] videoRenderers = new VideoRenderer.Callbacks[1];
                videoRenderers[0] = remoteRender;
                Log.w(TAG, "onCreate: TOKEN " + token );
                videoRoomTest = new VideoRoomTest(localRender, videoRenderers, token, name, roomid);
                videoRoomTest.initializeMediaContext(JanusActivity.this, true, true, true, con);
                videoRoomTest.Start();

            } catch (Exception ex) {
                Log.e("computician.janusclient", ex.getMessage());
            }
        }
    }
}
