package com.example.there.moviperfood.data.food.restaurant;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class RestaurantLocation implements Parcelable {
    @SerializedName("city_id")
    private String cityId;

    private String address;

    @SerializedName("country_id")
    private String countryId;

    @SerializedName("zipcode")
    private String zipCode;

    private String locality;

    private String longitude;

    private String latitude;

    private String city;

    private RestaurantLocation(Parcel in) {
        cityId = in.readString();
        address = in.readString();
        countryId = in.readString();
        zipCode = in.readString();
        locality = in.readString();
        longitude = in.readString();
        latitude = in.readString();
        city = in.readString();
    }

    public static final Creator<RestaurantLocation> CREATOR = new Creator<RestaurantLocation>() {
        @Override
        public RestaurantLocation createFromParcel(Parcel in) {
            return new RestaurantLocation(in);
        }

        @Override
        public RestaurantLocation[] newArray(int size) {
            return new RestaurantLocation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cityId);
        dest.writeString(address);
        dest.writeString(countryId);
        dest.writeString(zipCode);
        dest.writeString(locality);
        dest.writeString(longitude);
        dest.writeString(latitude);
        dest.writeString(city);
    }

    public LatLng getLatLng() {
        return new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
    }

}
