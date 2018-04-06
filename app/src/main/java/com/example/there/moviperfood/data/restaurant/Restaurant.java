package com.example.there.moviperfood.data.restaurant;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class Restaurant implements Parcelable {
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

    private Restaurant(Parcel in) {
        cuisines = in.readString();
        photosUrl = in.readString();
        hasOnlineDelivery = in.readString();
        featuredImage = in.readString();
        offers = in.createStringArray();
        menuUrl = in.readString();
        isDeliveringNow = in.readString();
        establishmentTypes = in.createStringArray();
        url = in.readString();
        switchToOrderMenu = in.readString();
        currency = in.readString();
        id = in.readString();
        priceRange = in.readString();
        name = in.readString();
        deeplink = in.readString();
        eventsUrl = in.readString();
        averageCostForTwo = in.readString();
        thumb = in.readString();
        hasTableBooking = in.readString();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cuisines);
        dest.writeString(photosUrl);
        dest.writeString(hasOnlineDelivery);
        dest.writeString(featuredImage);
        dest.writeStringArray(offers);
        dest.writeString(menuUrl);
        dest.writeString(isDeliveringNow);
        dest.writeStringArray(establishmentTypes);
        dest.writeString(url);
        dest.writeString(switchToOrderMenu);
        dest.writeString(currency);
        dest.writeString(id);
        dest.writeString(priceRange);
        dest.writeString(name);
        dest.writeString(deeplink);
        dest.writeString(eventsUrl);
        dest.writeString(averageCostForTwo);
        dest.writeString(thumb);
        dest.writeString(hasTableBooking);
    }
}
