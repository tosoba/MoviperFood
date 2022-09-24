package com.example.there.moviperfood.data.place;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity(tableName = "places")
@Getter
@EqualsAndHashCode
public class CachedPlace implements Parcelable {
    @PrimaryKey
    @NonNull
    private final String id;

    private final String name;

    private final double lat;

    private final double lng;

    private final Date lastSearched;

    public CachedPlace(@NonNull String id, String name, double lat, double lng, @NonNull Date lastSearched) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.lastSearched = lastSearched;
    }

    public LatLng getLatLng() {
        return new LatLng(lat, lng);
    }

    protected CachedPlace(Parcel in) {
        id = in.readString();
        name = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        lastSearched = new Date(in.readLong());
    }

    public static final Creator<CachedPlace> CREATOR = new Creator<CachedPlace>() {
        @Override
        public CachedPlace createFromParcel(Parcel in) {
            return new CachedPlace(in);
        }

        @Override
        public CachedPlace[] newArray(int size) {
            return new CachedPlace[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeLong(lastSearched.getTime());
    }
}
