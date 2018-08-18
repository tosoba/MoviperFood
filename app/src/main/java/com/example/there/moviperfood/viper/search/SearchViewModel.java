package com.example.there.moviperfood.viper.search;

import android.databinding.ObservableField;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.there.moviperfood.data.food.cuisine.Cuisine;

import java.util.Collections;
import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SearchViewModel implements Parcelable {
    public ObservableField<Boolean> isLoading = new ObservableField<>(false);

    public ObservableField<List<Cuisine>> lastCuisines = new ObservableField<>(Collections.emptyList());

    private SearchViewModel(Parcel in) {
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

    public static final Creator<SearchViewModel> CREATOR = new Creator<SearchViewModel>() {
        @Override
        public SearchViewModel createFromParcel(Parcel in) {
            return new SearchViewModel(in);
        }

        @Override
        public SearchViewModel[] newArray(int size) {
            return new SearchViewModel[size];
        }
    };
}
