package com.example.there.moviperfood.data.cuisine;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class Cuisine implements Parcelable {
    @SerializedName("cuisine_id") private int cuisineId;
    @SerializedName("cuisine_name") private String cuisineName;

    private Cuisine(Parcel in) {
        cuisineId = in.readInt();
        cuisineName = in.readString();
    }

    public static final Creator<Cuisine> CREATOR = new Creator<Cuisine>() {
        @Override
        public Cuisine createFromParcel(Parcel in) {
            return new Cuisine(in);
        }

        @Override
        public Cuisine[] newArray(int size) {
            return new Cuisine[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cuisineId);
        dest.writeString(cuisineName);
    }
}
