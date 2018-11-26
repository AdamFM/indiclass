package com.apps.indiclass.adapter;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.indiclass.JanusActivity;
import com.apps.indiclass.R;
import com.apps.indiclass.fragment.ClassFragment;
import com.apps.indiclass.model.MyClassModel;
import com.apps.indiclass.util.Config;
import com.apps.indiclass.util.Constant;
import com.apps.indiclass.util.SessionManager;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.apps.indiclass.util.Method.dbZero;

/**
 * Created by Dell_Cleva on 05/10/2017.
 */

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.MyViewHolder> {

    private static final String TAG = "ClassAdapter";
    private static final SimpleDateFormat FORMATTERSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final ArrayList<MyViewHolder> viewHoldersList;
    private final OnItemClickListener listener;
    SessionManager sessionManager;
    private Calendar c = Calendar.getInstance();
    private customButtonListener customListner;
    private Context context;
    private List<MyClassModel> moviesList;
    private Timer tmr;
    private Handler mHandler = new Handler();
    private Runnable updateRemainingTimeRunnable = new Runnable() {
        @Override
        public void run() {
            synchronized (viewHoldersList) {
                for (ClassAdapter.MyViewHolder holder : viewHoldersList) {
                    holder.updateTimeRemaining();
                }
            }
        }
    };

    Fragment fragments;

    public ClassAdapter(Context mcontext, List<MyClassModel> moviesList, OnItemClickListener listener, ClassFragment fragment) {
        this.context = mcontext;
        this.moviesList = moviesList;
        viewHoldersList = new ArrayList<>();
        sessionManager = new SessionManager(context);
        this.listener = listener;
        this.fragments = fragment;
        startUpdateTimer();
    }

    private void startUpdateTimer() {
        tmr = new Timer();
        tmr.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(updateRemainingTimeRunnable);
            }
        }, 1000, 1000);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.class_item_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyClassModel progamModel = moviesList.get(position);

        if (!progamModel.getsNama().equalsIgnoreCase("")) {
            holder.name.setText(progamModel.getsNama());
            holder.desc.setText(progamModel.getsSubject());
            holder.time.setText(progamModel.getsTime());

            holder.btnMasuk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(progamModel);
                }
            });
            holder.rlmasuk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.btnMasuk.isShown()) {
                        listener.onItemClick(progamModel);
                    } else
                        Toast.makeText(context, "Kelas belum dimulai.", Toast.LENGTH_SHORT).show();
                }
            });

            Glide.with(context)
                    .load(Constant.ImageTutor + progamModel.getsImage())
                    .into(holder.thumbnail);
            String jamsekarang = FORMATTERSQL.format(c.getTime());

            Date datenow = null, datestart = null, dateend = null;

            try {

                datenow = FORMATTERSQL.parse(jamsekarang);
                datestart = FORMATTERSQL.parse(progamModel.getsDateStart());
                dateend = FORMATTERSQL.parse(progamModel.getsDateEnd());

                if (datenow.compareTo(datestart) < 0) {

                    holder.btnMasuk.setVisibility(View.INVISIBLE);
                    holder.btnWait.setVisibility(View.VISIBLE);
                } else if (datenow.compareTo(datestart) == 0 || datenow.compareTo(datestart) > 0 && datenow.compareTo(dateend) < 0){

                    holder.btnWait.setVisibility(View.INVISIBLE);
                    holder.btnMasuk.setVisibility(View.VISIBLE);
                } else if (datenow.compareTo(dateend) > 0) {

                    holder.btnWait.setVisibility(View.INVISIBLE);
                    holder.btnMasuk.setVisibility(View.INVISIBLE);
                }
            }  catch (ParseException e) {
                e.printStackTrace();
            }
            synchronized (viewHoldersList) {
                holder.setData(moviesList.get(position));
                viewHoldersList.add(holder);
            }
        }
        holder.setData(moviesList.get(position));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(MyClassModel item);
    }

    public interface customButtonListener {
        void onButtonClickListener(int position, String value);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name, desc, time;
        public ImageView thumbnail;
        RelativeLayout rlmasuk;
        Button btnMasuk, btnWait;
        MyClassModel item;

        MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.title);
            desc = view.findViewById(R.id.desc);
            time = view.findViewById(R.id.tvTime);
            thumbnail = view.findViewById(R.id.icon);
            btnMasuk = view.findViewById(R.id.buttonEnter);
            btnWait = view.findViewById(R.id.buttonWait);
            rlmasuk = view.findViewById(R.id.rlmasuk);
        }

        public void setData(MyClassModel items) {
            item = items;
        }

        void updateTimeRemaining() {
//            Log.w(TAG, "updateTimeRemaining: EXPIRED " + item.getExpiredTime());
            if (item.getExpiredTime() != 0) {
                long currentTime = System.currentTimeMillis();
                long timeDiff = item.getExpiredTime() - currentTime;
                if (timeDiff > 0) {
                    int seconds = (int) (timeDiff / 1000) % 60;
                    int minutes = (int) ((timeDiff / (1000 * 60)) % 60);
                    int hours = (int) ((timeDiff / (1000 * 60 * 60)) % 24);
                    int days = (int) ((timeDiff / (1000 * 60 * 60 * 24)));
                    if (days > 0) {
                        hours = hours + (days * 24);
                        btnWait.setText(dbZero(hours) + ":" + dbZero(minutes) + ":" + dbZero(seconds));
                    } else if (hours > 0)
                        btnWait.setText(dbZero(hours) + ":" + dbZero(minutes) + ":" + dbZero(seconds));
                    else
                        btnWait.setText(dbZero(minutes) + ":" + dbZero(seconds));

                } else {
                    Intent pushNotification = new Intent(Config.NEW_CLASS);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(pushNotification);

                    btnWait.setText(context.getString(R.string.wait));
                    tmr.cancel();
                    Log.e(TAG, "updateTimeRemaining: "+timeDiff);
                }
            }
        }
    }
}
