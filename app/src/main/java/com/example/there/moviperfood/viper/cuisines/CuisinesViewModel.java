package com.example.there.moviperfood.viper.cuisines;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.there.moviperfood.data.food.cuisine.Cuisine;

import java.util.Arrays;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CuisinesViewModel implements Parcelable {
    public ObservableField<Boolean> isLoading = new ObservableField<>(false);

    public ObservableList<Cuisine> cuisines = new ObservableArrayList<>();

    private CuisinesViewModel(Parcel in) {
        isLoading.set(in.readByte() != 0);
        cuisines.addAll(Arrays.asList(in.createTypedArray(Cuisine.CREATOR)));
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
        dest.writeTypedArray(cuisines.toArray(new Cuisine[cuisines.size()]), flags);
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
