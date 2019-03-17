package com.ccomputing.assessment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ccomputing.assessment.adapters.AdtForecast;
import com.ccomputing.assessment.interfaces.OnVolleyResult;
import com.ccomputing.assessment.model.Forecast;
import com.ccomputing.assessment.net.VolleyHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ActWeather extends AppCompatActivity implements View.OnClickListener , OnVolleyResult {

    TextView tvCLocName,tvCLocDay,tvCLocDesc,tvCTemperature,tvPrecipitation,tvHumidity,
            tvWind;
    ImageView ivCWeatherIcon;
    RecyclerView recyViewList;

    AdtForecast adtForecast;

    String strDay = "",strLocName = "",strWeatherDesc = "",strWeatherIcon ="",strHumidity = "";
    int valPrecipitation = 0;
    Double valTemp = 0.0,valWind = 0.0;
    List<Forecast> forecastList = new ArrayList<>();

    Double current_lat = 24.874079,current_lon = 67.061078;
    String strCLocName = "Karachi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        setFindVByIds();
        getCWeather();
        getForecastWeather();
    }

    private void setFindVByIds() {
        tvCLocName = findViewById(R.id.tvCLocName);
        tvCLocDay = findViewById(R.id.tvCLocDay);
        tvCLocDesc = findViewById(R.id.tvCLocDesc);
        tvCTemperature = findViewById(R.id.tvCTemperature);
        tvPrecipitation = findViewById(R.id.tvPrecipitation);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvWind = findViewById(R.id.tvWind);

        ivCWeatherIcon = findViewById(R.id.ivCWeatherIcon);

        Button btnPopRestaurant = findViewById(R.id.btnPopRestaurant);
        btnPopRestaurant.setOnClickListener(this);

        recyViewList = findViewById(R.id.recyViewList);
        setRecyclerListView();
        setAdapter();
    }

    private void getCWeather(){
        String url = "http://api.openweathermap.org/data/2.5/weather?lat=24.8607&lon=67.0011&units=metric&appid=35d1dd37c93666f22c6cee61f9c34e3c";
        Log.e("TAG", url);
        VolleyHelper.getInstance().sendRequest(this, Request.Method.GET, null, false, url, "CWeather");
    }

    private void getForecastWeather(){
        String url = "http://api.openweathermap.org/data/2.5/forecast?lat=24.8607&lon=67.0011&units=metric&appid=35d1dd37c93666f22c6cee61f9c34e3c";
        Log.e("TAG", url);
        VolleyHelper.getInstance().sendRequest(this, Request.Method.GET, null, false, url, "CWeatherForecast");
    }

    private void setAdapter() {
        adtForecast = new AdtForecast(this,forecastList);
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
                intent.putExtra("loc_name",strCLocName);
                intent.putExtra("lat",current_lat);
                intent.putExtra("lon",current_lon);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onResultSuccess(String response, String from) {
        try {
            if(from.equalsIgnoreCase("CWeather")) {
                if (!response.isEmpty()) {
                    JSONObject jsonObject = new JSONObject(response);
                    int responseCode = jsonObject.getInt("cod");
                    if (responseCode == 200) {
                        long dt = (long) jsonObject.getInt("dt");
                        strDay = getDayOfWeek(dt);
                        strLocName = jsonObject.getString("name");
                        strWeatherDesc = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
                        strWeatherIcon = jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");
                        valPrecipitation = jsonObject.getJSONObject("clouds").getInt("all");
                        strHumidity = jsonObject.getJSONObject("main").getString("humidity");
                        valTemp = jsonObject.getJSONObject("main").getDouble("temp");
                        valWind = jsonObject.getJSONObject("wind").getDouble("speed");

                        setCurrentWeatherInfo();
                    } else {
                        String strMessage = jsonObject.getString("message");
                        Toast.makeText(this, strMessage, Toast.LENGTH_SHORT).show();
                    }

                }
            }else if(from.equalsIgnoreCase("CWeatherForecast")) {
                if (!response.isEmpty()) {
                    JSONObject jsonObject = new JSONObject(response);
                    int responseCode = jsonObject.getInt("cod");
                    if (responseCode == 200) {
                        JSONArray jsonArray = jsonObject.getJSONArray("list");
                        if(jsonArray != null){
                            forecastList.clear();
                            if(jsonArray.length()>0){
                                for (int i = 0; i<jsonArray.length(); i++){
                                    long dt = (long) jsonArray.getJSONObject(i).getInt("dt");
                                    String strWFDay = getDayOfWeek(dt).substring(0,3).toUpperCase();
                                    String strWFDtTxt = jsonArray.getJSONObject(i).getString("dt_txt");
                                    String strWFIcon = jsonArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon");
                                    Double valWFMinTemp = jsonArray.getJSONObject(i).getJSONObject("main").getDouble("temp_min");
                                    Double valWFMaxTemp = jsonArray.getJSONObject(i).getJSONObject("main").getDouble("temp_max");

                                    String[] split = strWFDtTxt.split(" ");
                                    if(split[1].equalsIgnoreCase("00:00:00")){
                                        Forecast forecast = new Forecast();
                                        forecast.setDt(dt);
                                        forecast.setDay(strWFDay);
                                        forecast.setDtTxt(strWFDtTxt);
                                        forecast.setWeatherIcon(strWFIcon);
                                        forecast.setMinTemp(valWFMinTemp);
                                        forecast.setMaxTemp(valWFMaxTemp);

                                        forecastList.add(forecast);
                                    }
                                }

                            }
                            Log.d("==Size",":"+forecastList.size());
                            setAdapter();
                        }

                    } else {
                        String strMessage = jsonObject.getString("message");
                        Toast.makeText(this, strMessage, Toast.LENGTH_SHORT).show();
                    }

                }
            }

        }catch (Exception e){
            Log.d("==Error",":"+e);
        }

    }

    private void setCurrentWeatherInfo() {
        tvCLocName.setText(strLocName);
        tvCLocDay.setText(strDay);
        tvCLocDesc.setText(strWeatherDesc);
        tvCTemperature.setText((valTemp.intValue()) + " \u2103");
        tvPrecipitation.setText(valPrecipitation + "%");
        tvHumidity.setText(strHumidity + "%");
        tvWind.setText(valWind.intValue() + "Km/h");
        String iconUrl = "http://openweathermap.org/img/w/" + strWeatherIcon + ".png";

        Glide.with(this)
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
                .into(ivCWeatherIcon);
    }

    private String getDayOfWeek(long milliSeconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (day) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
        }
        return "";
    }
}
