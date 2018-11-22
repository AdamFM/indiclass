package com.apps.indiclass.adapter;

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

import com.apps.indiclass.Classroom;
import com.apps.indiclass.R;
import com.apps.indiclass.model.MyClassModel;
import com.apps.indiclass.util.Constant;
import com.apps.indiclass.util.SessionManager;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.apps.indiclass.util.Method.dbZero;

/**
 * Created by Dell_Cleva on 16/10/2018.
 */

public class MyClassAdapter extends RecyclerView.Adapter<MyClassAdapter.MyViewHolder> {

    private final OnItemClickListener listener;
    private static final String TAG = "MyClassAdapter";
    private Context context;
    private List<MyClassModel> progamModels;
    SessionManager sessionManager;
    private final List<MyViewHolder> lstHolders;
    private Handler mHandler = new Handler();
    private Timer tmr;
    private Runnable updateRemainingTimeRunnable = new Runnable() {
        @Override
        public void run() {
            synchronized (lstHolders) {
                for (MyViewHolder holder : lstHolders) {
                    holder.updateTimeRemaining();
                }
            }
        }
    };

    public interface OnItemClickListener {
        void onItemClick(MyClassModel item);
    }
    public MyClassAdapter(Context context, List<MyClassModel> progamModelList, OnItemClickListener listener) {
        this.context = context;
        this.progamModels = progamModelList;
        sessionManager = new SessionManager(context);
        lstHolders = new ArrayList<>();
        this.listener = listener;
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

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.class_item_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyClassModel progamModel = progamModels.get(position);
        holder.name.setText(progamModel.getsNama());
        holder.desc.setText(progamModel.getsSubject());
        holder.time.setText(progamModel.getsTime());

        holder.btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: " + progamModel.toString());
                Intent in = new Intent(context, Classroom.class);
                in.putExtra("visible", "");
                in.putExtra("subject", progamModel.getsSubject());
                in.putExtra("template", "all_featured");
                in.putExtra("id_user", sessionManager.getIds());
                in.putExtra("class_id", progamModel.getsClassID());
                in.putExtra("isTV", false);
                context.startActivity(in);
            }
        });
        holder.rlmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.btnMasuk.isShown()) {
                    Log.i(TAG, "onClick: " + progamModel.toString());
                    Intent in = new Intent(context, Classroom.class);
                    in.putExtra("visible", "");
                    in.putExtra("subject", progamModel.getsSubject());
                    in.putExtra("template", "all_featured");
                    in.putExtra("id_user", sessionManager.getIds());
                    in.putExtra("class_id", progamModel.getsClassID());
                    in.putExtra("isTV", false);
                    context.startActivity(in);
                } else
                    Toast.makeText(context, "Kelas belum dimulai.", Toast.LENGTH_SHORT).show();
            }
        });



        synchronized (lstHolders) {
            holder.setData(progamModel);
            lstHolders.add(holder);
        }

        Glide.with(context)
                .load(Constant.ImageTutor + progamModel.getsImage())
                .into(holder.thumbnail);

        holder.setData(progamModel);
    }

    @Override
    public int getItemCount() {
        return progamModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
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
            if (item.getExpiredTime() != 0) {
                long currentTime = System.currentTimeMillis();
                long timeDiff = item.getExpiredTime() - currentTime;
                if (timeDiff > 0) {
                    int seconds = (int) (timeDiff / 1000) % 60;
                    int minutes = (int) ((timeDiff / (1000 * 60)) % 60);
                    int hours = (int) ((timeDiff / (1000 * 60 * 60)) % 24);
                    int days = (int) ((timeDiff / (1000*60*60*24)) );
                    int weeks = (int) (timeDiff / (1000*60*60*24*7));
                    if (days > 0) {
                        hours = hours+(days*24);
                        btnWait.setText(dbZero(hours) + ":" + dbZero(minutes) + ":" + dbZero(seconds));
                    } else if (hours > 0)
                        btnWait.setText(dbZero(hours) + ":" + dbZero(minutes) + ":" + dbZero(seconds));
                    else
                        btnWait.setText(dbZero(minutes) + ":" + dbZero(seconds));

                Log.i("TAG", "updateTimeRemaining: " + hours + " hrs " + minutes + " mins " + seconds + " sec");
                } else {
                    btnMasuk.setVisibility(View.VISIBLE);
                    btnWait.setVisibility(View.GONE);

                    tmr.cancel();
                }
            }
        }
    }
}
