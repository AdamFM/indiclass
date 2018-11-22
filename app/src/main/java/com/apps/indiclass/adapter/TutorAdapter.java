package com.apps.indiclass.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.indiclass.R;
import com.apps.indiclass.model.TutorModel;
import com.apps.indiclass.util.Constant;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Dell_Cleva on 16/10/2018.
 */

public class TutorAdapter extends RecyclerView.Adapter<TutorAdapter.MyViewHolder> {

    private final OnItemClickListener listener;
    private Context context;
    private List<TutorModel> progamModels;

    public TutorAdapter(Context context, List<TutorModel> progamModelList, OnItemClickListener listener) {
        this.context = context;
        this.progamModels = progamModelList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tutor_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final TutorModel progamModel = progamModels.get(position);
        holder.name.setText(progamModel.getsNameTutor());
        holder.exp.setText(progamModel.getsExpTutor());
        holder.subject.setText(progamModel.getsSubjectTutor());
        holder.price.setText(progamModel.getsPriceTutor());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(progamModel);
            }
        });

        Glide.with(context)
                .load(Constant.ImageTutor+progamModel.getsImageTutor())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return progamModels.size();
    }

    public interface OnItemClickListener {
        void onItemClick(TutorModel item);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, price, subject, exp;
        public ImageView thumbnail;
        View v;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tvNameT);
            exp = view.findViewById(R.id.tvExpT);
            subject = view.findViewById(R.id.tvSubjectT);
            price = view.findViewById(R.id.tvPriceT);
            thumbnail = view.findViewById(R.id.imageView1);
            v = view.findViewById(R.id.rlTutorItem);
        }
    }
}
