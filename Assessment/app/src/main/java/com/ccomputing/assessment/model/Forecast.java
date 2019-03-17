package com.ccomputing.assessment.model;

public class Forecast {

    private long dt;
    private String dt_txt;
    private String day;
    private String weather_icon;
    private Double min_temp;
    private Double max_temp;

    //
    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    //
    public String getDtTxt() {
        return dt_txt;
    }

    public void setDtTxt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    //
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    //
    public String getWeatherIcon() {
        return weather_icon;
    }

    public void setWeatherIcon(String weather_icon) {
        this.weather_icon = weather_icon;
    }

    //
    public Double getMinTemp() {
        return min_temp;
    }

    public void setMinTemp(Double min_temp) {
        this.min_temp = min_temp;
    }

    //
    public Double getMaxTemp() {
        return max_temp;
    }

    public void setMaxTemp(Double max_temp) {
        this.max_temp = max_temp;
    }
}
