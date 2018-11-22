package com.apps.indiclass.adapter;

import android.content.Context;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.indiclass.R;
import com.apps.indiclass.model.ProgamModel;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Dell_Cleva on 16/10/2018.
 */

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.MyViewHolder> {

private Context context;
private List<ProgamModel> progamModels;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView name, price;
    public ImageView thumbnail;

    public MyViewHolder(View view) {
        super(view);
        name = view.findViewById(R.id.title);
        price = view.findViewById(R.id.price);
        thumbnail = view.findViewById(R.id.thumbnail);
    }
}


    public ProgramAdapter(Context context, List<ProgamModel> progamModelList) {
        this.context = context;
        this.progamModels = progamModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.program_item_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ProgamModel progamModel = progamModels.get(position);
        holder.name.setText(progamModel.getsProgamName());
        holder.price.setText(progamModel.getsPrice());

        if (position == 0) {
            Glide.with(context)
                    .load(R.drawable.sd)
                    .into(holder.thumbnail);
        }else if (position == 1) {
            Glide.with(context)
                    .load(R.drawable.smp)
                    .into(holder.thumbnail);
        }else if (position == 2) {
            Glide.with(context)
                    .load(R.drawable.smaipa)
                    .into(holder.thumbnail);
        }else {
            Glide.with(context)
                    .load(R.drawable.smaips)
                    .into(holder.thumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return progamModels.size();
    }
}
