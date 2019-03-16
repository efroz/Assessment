package com.ccomputing.assessment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ccomputing.assessment.adapters.AdtForecast;

public class ActWeather extends AppCompatActivity implements View.OnClickListener {

    TextView tvCLocName,tvCLocDay,tvCLocDesc,tvPrecipitation,tvHumidity,
            tvWind;
    RecyclerView recyViewList;

    AdtForecast adtForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        setFindVByIds();
    }

    private void setFindVByIds() {
        tvCLocName = findViewById(R.id.tvCLocName);
        tvCLocDay = findViewById(R.id.tvCLocDay);
        tvCLocDesc = findViewById(R.id.tvCLocDesc);
        tvPrecipitation = findViewById(R.id.tvPrecipitation);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvWind = findViewById(R.id.tvWind);

        Button btnPopRestaurant = findViewById(R.id.btnPopRestaurant);
        btnPopRestaurant.setOnClickListener(this);

        recyViewList = findViewById(R.id.recyViewList);
        setRecyclerListView();
        setAdapter();
    }

    private void setAdapter() {
        adtForecast = new AdtForecast(this);
        recyViewList.setAdapter(adtForecast);
    }

    private void setRecyclerListView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyViewList.setLayoutManager(mLayoutManager);

        recyViewList.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPopRestaurant:
                Intent intent = new Intent(this,ActPopularRestaurants.class);
                startActivity(intent);
                break;
        }
    }
}
