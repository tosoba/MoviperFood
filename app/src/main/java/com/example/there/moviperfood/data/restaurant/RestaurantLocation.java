package com.example.there.moviperfood.data.restaurant;

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
}
