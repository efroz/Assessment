package com.ccomputing.assessment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ccomputing.assessment.adapters.AdtPopularRestaurant;
import com.ccomputing.assessment.utils.RippleView;

public class ActPopularRestaurants extends AppCompatActivity implements RippleView.OnRippleCompleteListener {

    RecyclerView recyViewList;
    AdtPopularRestaurant adtPopularRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_restaurants);
        setFindVByIds();
    }

    private void setFindVByIds() {

        RippleView rvBack = findViewById(R.id.rvBack);
        rvBack.setOnRippleCompleteListener(this);

        recyViewList = findViewById(R.id.recyViewList);
        setRecyclerListView();
        setAdapter();
    }

    private void setRecyclerListView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyViewList.setLayoutManager(mLayoutManager);

        recyViewList.setItemAnimator(new DefaultItemAnimator());
    }
    private void setAdapter() {
        adtPopularRestaurant = new AdtPopularRestaurant(this);
        recyViewList.setAdapter(adtPopularRestaurant);
    }

    @Override
    public void onComplete(RippleView rippleView) {
        switch (rippleView.getId()){
            case R.id.rvBack:
                finish();
                break;
        }
    }
}
