package com.example.there.moviperfood.data.restaurant;

import com.google.android.gms.maps.model.LatLng;

import lombok.Data;

public @Data class RestaurantLocation {
    private String city_id;

    private String locality_verbose;

    private String address;

    private String country_id;

    private String zipcode;

    private String locality;

    private String longitude;

    private String latitude;

    private String city;

    public LatLng getLatLng() {
        return new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
    }
}
