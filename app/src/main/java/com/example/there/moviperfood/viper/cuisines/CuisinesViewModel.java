package com.example.there.moviperfood.viper.cuisines;

import android.databinding.ObservableField;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.there.moviperfood.data.food.cuisine.Cuisine;

import java.util.Collections;
import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CuisinesViewModel implements Parcelable {
    public ObservableField<Boolean> isLoading = new ObservableField<>(false);

    public ObservableField<List<Cuisine>> lastCuisines = new ObservableField<>(Collections.emptyList());

    private CuisinesViewModel(Parcel in) {
        isLoading.set(in.readByte() != 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (isLoading == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) (isLoading.get() ? 1 : 0));
        }
    }

    public static final Creator<CuisinesViewModel> CREATOR = new Creator<CuisinesViewModel>() {
        @Override
        public CuisinesViewModel createFromParcel(Parcel in) {
            return new CuisinesViewModel(in);
        }

        @Override
        public CuisinesViewModel[] newArray(int size) {
            return new CuisinesViewModel[size];
        }
    };
}
