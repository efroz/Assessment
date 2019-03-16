package com.ccomputing.assessment.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccomputing.assessment.R;

public class AdtForecast extends RecyclerView.Adapter<AdtForecast.MyViewHolder> {

    private Activity context;


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivIcon;
        TextView tvDay,tvTemperature;

        MyViewHolder(View view) {
            super(view);
            ivIcon = view.findViewById(R.id.ivIcon);

            tvDay = view.findViewById(R.id.tvDay);
            tvTemperature = view.findViewById(R.id.tvTemperature);

        }
    }


    public AdtForecast(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AdtForecast.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forecast, parent, false);

        return new AdtForecast.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdtForecast.MyViewHolder holder, final int position) {

    }


    @Override
    public int getItemCount() {
        return 5;
    }
}
