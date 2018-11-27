package com.apps.indiclass;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import org.apache.cordova.CordovaActivity;

public class Classroom extends CordovaActivity {
    WebView wV;
    WebSettings ws;
    String session = "", visibles= "", subject= "", template= "", id_user= "", class_id= "", p= "";
    int iPause = 0;
    int NOTIFICATION_IDs = 101;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // Set by <content src="index.html" /> in config.xml
        session = getIntent().getStringExtra("session");
        visibles = getIntent().getStringExtra("visible");
        subject = getIntent().getStringExtra("subject");
        template = getIntent().getStringExtra("template");
        id_user = getIntent().getStringExtra("id_user");
        class_id = getIntent().getStringExtra("class_id");
        p = getIntent().getBooleanExtra("isTV", false) ? "web" : "android";

//        if (visibles.equalsIgnoreCase("private") || visibles.equalsIgnoreCase("private_channel"))
////            loadUrl("https://classmiles.com/master/tps_me_priv/" + class_id + "?t=" + template + "&id_user=" + id_user + "&p=" + p);
//            loadUrl("file:///android_asset/www/index.html?class_id=" + class_id + "&template=" + template + "&id_user=" + id_user + "&p=" + p + "&type=priv");
//        else if (visibles.equalsIgnoreCase("group") || visibles.equalsIgnoreCase("group_channel"))
////            loadUrl("https://classmiles.com/master/tps_me_group/" + class_id + "?t=" + template + "&id_user=" + id_user + "&p=" + p);
//            loadUrl("file:///android_asset/www/index.html?class_id=" + class_id + "&template=" + template + "&id_user=" + id_user + "&p=" + p + "&type=grup");
//        else
//            loadUrl("https://classmiles.com/master/tps_me/" + class_id + "?t=" + template + "&id_user=" + id_user + "&p=" + p);
            loadUrl("file:///android_asset/www/index.html?class_id=" + class_id + "&template=" + template + "&id_user=" + id_user + "&p=" + p + "&type=mult");

        wV = (WebView) appView.getEngine().getView();
        wV.addJavascriptInterface(new JavaScriptInterface(this), "AndroidFunction");
        wV.getSettings().setDomStorageEnabled(true);

        ws = wV.getSettings();

        ws.setAppCacheEnabled(false);
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setDatabaseEnabled(true);
        ws.setRenderPriority(WebSettings.RenderPriority.HIGH);
        ws.setDatabasePath(getFilesDir().getParentFile().getPath() + "/databases");
        Log.e(TAG, "onCreate: " + wV.getUrl());

//        makeNotification(this);

    }

    @Override
    protected void onPause() {
        loadUrl("file:///android_asset/www/paused.html");
        iPause = 1;
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (iPause == 1) {
            Intent in = new Intent(Classroom.this, Classroom.class);

            in.putExtra("session", session);
            in.putExtra("visible", visibles);
            in.putExtra("subject", subject);
            in.putExtra("template", template);
            in.putExtra("id_user", id_user);
            in.putExtra("class_id", class_id);
            startActivity(in);
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(R.string.exit_classroom);
        builder.setCancelable(false);
        builder.setPositiveButton("Tidak", new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton("Iya", new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        // TODO Auto-generated method stub
                        finish();
                    }
                }).show();

    }

    private void makeNotification(Context context) {
        Intent intent = new Intent(context, Classroom.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                NOTIFICATION_IDs, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notif_entering, subject))
                .setContentIntent(pendingIntent)
                .setColor(Color.parseColor("#128382"))
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setOngoing(true);
        Notification n;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            n = builder.build();
        } else {
            n = builder.getNotification();
        }

        n.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        NotificationManager notificationManager;
        notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        n = builder.build();
        notificationManager.notify(NOTIFICATION_IDs, n);
    }

    public class JavaScriptInterface {
        Context mContext;

        JavaScriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void showToast(String webMessage) {
            Toast.makeText(mContext, webMessage, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void ReloginClass(String message) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Classroom.this);
            builder.setMessage("Relogin Class");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new
                    DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent in = new Intent(Classroom.this, Classroom.class);

                            in.putExtra("session", session);
                            in.putExtra("visible", visibles);
                            in.putExtra("subject", subject);
                            in.putExtra("template", template);
                            in.putExtra("id_user", id_user);
                            in.putExtra("class_id", class_id);
                            startActivity(in);
                            finish();
                        }
                    }).show();
        }

        @JavascriptInterface
        public void ErrorClass() {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Classroom.this);
            builder.setMessage("You probably have been entering this classroom from other devices");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new
                    DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    }).show();
        }

        @JavascriptInterface
        public void FinishClass() {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Classroom.this);
            builder.setMessage("Kelas Kamu telah berakhir");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new
                    DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    }).show();
        }
    }

}
