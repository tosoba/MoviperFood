package com.example.there.moviperfood.data.restaurant;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class Restaurant {
    private String cuisines;

    @SerializedName("photos_url")
    private String photosUrl;

    @SerializedName("has_online_delivery")
    private String hasOnlineDelivery;

    private RestaurantLocation location;

    @SerializedName("featured_image")
    private String featuredImage;

    private String[] offers;

    @SerializedName("menu_url")
    private String menuUrl;

    @SerializedName("is_delivering_now")
    private String isDeliveringNow;

    @SerializedName("establishment_types")
    private String[] establishmentTypes;

    private String url;

    @SerializedName("switch_to_order_menu")
    private String switchToOrderMenu;

    @SerializedName("user_rating")
    private RestaurantUserRating userRating;

    private String currency;

    private String id;

    @SerializedName("price_range")
    private String priceRange;

    private String name;

    private String deeplink;

    @SerializedName("events_url")
    private String eventsUrl;

    @SerializedName("average_cost_for_two")
    private String averageCostForTwo;

    private String thumb;

    @SerializedName("has_table_booking")
    private String hasTableBooking;
}
