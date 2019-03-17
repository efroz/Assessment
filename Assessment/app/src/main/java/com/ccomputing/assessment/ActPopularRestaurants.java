package com.ccomputing.assessment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.ccomputing.assessment.adapters.AdtPopularRestaurant;
import com.ccomputing.assessment.interfaces.OnVolleyResult;
import com.ccomputing.assessment.model.NearbyRestaurant;
import com.ccomputing.assessment.net.VolleyHelper;
import com.ccomputing.assessment.utils.DialogGlobal;
import com.ccomputing.assessment.utils.RippleView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActPopularRestaurants extends AppCompatActivity implements RippleView.OnRippleCompleteListener, OnVolleyResult {

    RecyclerView recyViewList;
    AdtPopularRestaurant adtPopularRestaurant;

    List<NearbyRestaurant> nearbyRestaurantList = new ArrayList<>();

    String strLocName = "";
    Double lat = 0.0,lon = 0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_restaurants);
        getIntentData();
        setFindVByIds();
        getNearByResturants();
    }

    private void getIntentData() {
        strLocName = getIntent().getExtras().getString("loc_name");
        lat = getIntent().getExtras().getDouble("lat");
        lon = getIntent().getExtras().getDouble("lon");
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
        adtPopularRestaurant = new AdtPopularRestaurant(this,nearbyRestaurantList);
        recyViewList.setAdapter(adtPopularRestaurant);
    }

    private void getNearByResturants(){
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lat +","+lon+"&radius=500&rating=5.0&types=restaurant&key=AIzaSyBGShr295zGroTO8alENLcsNQauNbiuWbg";
        Log.e("TAG", url);
        VolleyHelper.getInstance().sendRequest(this, Request.Method.GET, null, true, url, "NearByRestaurant");
    }

    @Override
    public void onComplete(RippleView rippleView) {
        switch (rippleView.getId()){
            case R.id.rvBack:
                finish();
                break;
        }
    }

    @Override
    public void onResultSuccess(String response, String from) {
        try {
            if(from.equalsIgnoreCase("NearByRestaurant")){
                if(!response.isEmpty()){
                    JSONObject jsonObject = new JSONObject(response);
                    String strStatus = jsonObject.getString("status");
                    if(strStatus.equalsIgnoreCase("OK")){
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        nearbyRestaurantList.clear();
                        if(jsonArray!=null)
                            if(jsonArray.length()>0){
                                for (int i = 0; i< jsonArray.length(); i++){
                                    Double valRating;
                                   String strName = jsonArray.getJSONObject(i).getString("name");
                                   try {
                                       valRating = jsonArray.getJSONObject(i).getDouble("rating");

                                   }catch (Exception e){
                                       valRating = 0.0;
                                   }
                                  int strReviews = jsonArray.getJSONObject(i).getInt("user_ratings_total");

                                    NearbyRestaurant nearbyRestaurant = new NearbyRestaurant();
                                    nearbyRestaurant.setName(strName);
                                    nearbyRestaurant.setCruise("");
                                    nearbyRestaurant.setLocName(strLocName);
                                    nearbyRestaurant.setRating(valRating);
                                    nearbyRestaurant.setReviews(strReviews);

                                    nearbyRestaurantList.add(nearbyRestaurant);
                                }
                            }
                        Log.d("==Size","NearBy:"+nearbyRestaurantList.size());
                    setAdapter();
                    }else{
                        String error_message = jsonObject.getString("error_message");
                        DialogGlobal.getInstance().showAlertDialog(this,strStatus,error_message)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
                    }
                }
            }
        }catch (Exception e){
            Log.d("==Error","NearBy:"+e);
        }

    }


}
