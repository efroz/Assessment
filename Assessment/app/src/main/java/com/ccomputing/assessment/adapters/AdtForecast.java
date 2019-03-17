package com.ccomputing.assessment.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ccomputing.assessment.R;
import com.ccomputing.assessment.model.Forecast;

import java.util.List;

public class AdtForecast extends RecyclerView.Adapter<AdtForecast.MyViewHolder> {

    private Activity context;
    private List<Forecast> list;
    private DisplayMetrics displayMetrics;
    private int item_W = 0;


    class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout lnItemForecast;
        ImageView ivIcon;
        TextView tvDay,tvTemperature;

        MyViewHolder(View view) {
            super(view);
            lnItemForecast = view.findViewById(R.id.lnItemForecast);

            ivIcon = view.findViewById(R.id.ivIcon);

            tvDay = view.findViewById(R.id.tvDay);
            tvTemperature = view.findViewById(R.id.tvTemperature);

        }
    }


    public AdtForecast(Activity context, List<Forecast> list) {
        this.context = context;
        this.list = list;
        if(displayMetrics==null)
            displayMetrics = context.getResources().getDisplayMetrics();

        float dpWidth = displayMetrics.widthPixels;
        item_W = (int) dpWidth/6;
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

        try {
            holder.lnItemForecast.getLayoutParams().width = item_W;
            holder.tvDay.setText(list.get(position).getDay());

            int minTemp = list.get(position).getMinTemp().intValue();
            int maxTemp = list.get(position).getMaxTemp().intValue();
            String text = "<font color=#242424>"+minTemp + "\u00B0"+"</font> <font color=#A2A59F>"+maxTemp + "\u00B0"+"</font>";
            holder.tvTemperature.setText(Html.fromHtml(text));

            String iconUrl = "http://openweathermap.org/img/w/" + list.get(position).getWeatherIcon() + ".png";

            Glide.with(context)
                    .load(iconUrl)
                    .error(R.mipmap.ic_launcher)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            // log exception
                            Log.e("==IMG", "Error loading image", e);
                            return false; // important to return false so the error placeholder can be placed
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(holder.ivIcon);
        }catch (Exception e){

        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
