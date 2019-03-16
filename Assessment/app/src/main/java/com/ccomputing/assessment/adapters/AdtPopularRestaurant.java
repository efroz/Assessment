package com.ccomputing.assessment.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ccomputing.assessment.R;

public class AdtPopularRestaurant extends RecyclerView.Adapter<AdtPopularRestaurant.MyViewHolder> {

    private Activity context;


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPlaceImg;
        TextView tvRestaurantName,tvLocName,tvCuisines,tvReviewsCount;
        RatingBar rBarRestaurant;

        MyViewHolder(View view) {
            super(view);
            ivPlaceImg = view.findViewById(R.id.ivPlaceImg);

            tvRestaurantName = view.findViewById(R.id.tvRestaurantName);
            tvLocName = view.findViewById(R.id.tvLocName);
            tvCuisines = view.findViewById(R.id.tvCuisines);
            tvReviewsCount = view.findViewById(R.id.tvReviewsCount);

            rBarRestaurant = view.findViewById(R.id.rBarRestaurant);

        }
    }


    public AdtPopularRestaurant(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AdtPopularRestaurant.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_popular_restaurant, parent, false);

        return new AdtPopularRestaurant.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdtPopularRestaurant.MyViewHolder holder, final int position) {

    }


    @Override
    public int getItemCount() {
        return 3;
    }
}
