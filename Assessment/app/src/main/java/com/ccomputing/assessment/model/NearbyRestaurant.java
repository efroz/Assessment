package com.ccomputing.assessment.model;

public class NearbyRestaurant {

    private String name;
    private String loc_name;
    private String cruise;
    private Double rating;
    private int reviews;

    //
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //
    public String getLocName() {
        return loc_name;
    }

    public void setLocName(String loc_name) {
        this.loc_name = loc_name;
    }

    //
    public String getCruise() {
        return cruise;
    }

    public void setCruise(String cruise) {
        this.cruise = cruise;
    }

    //
    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    //
    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }
}
