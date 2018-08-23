package com.example.there.moviperfood.viper.cuisines;

import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.there.moviperfood.data.food.cuisine.Cuisine;
import com.example.there.moviperfood.util.ObservableSortedList;

import java.util.Arrays;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CuisinesViewModel implements Parcelable {
    private ObservableField<Boolean> isLoading = new ObservableField<>(false);

    private ObservableField<String> filterText = new ObservableField<>("");

    private ObservableList<Cuisine> cuisines = new ObservableSortedList<>(Cuisine.class, new ObservableSortedList.Callback<Cuisine>() {
        @Override
        public int compare(Cuisine o1, Cuisine o2) {
            return o1.getCuisineName().compareTo(o2.getCuisineName());
        }

        @Override
        public boolean areItemsTheSame(Cuisine item1, Cuisine item2) {
            return item1.getCuisineId() == item2.getCuisineId();
        }

        @Override
        public boolean areContentsTheSame(Cuisine oldItem, Cuisine newItem) {
            return newItem.getCuisineId() == oldItem.getCuisineId();
        }
    });

    private CuisinesViewModel(Parcel in) {
        isLoading.set(in.readByte() != 0);
        cuisines.addAll(Arrays.asList(in.createTypedArray(Cuisine.CREATOR)));
        filterText.set(in.readString());
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
        dest.writeString(filterText.get());
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
