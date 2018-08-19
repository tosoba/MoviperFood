package com.example.there.moviperfood.viper.search;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.data.place.CachedPlace;
import com.example.there.moviperfood.util.ObservableSortedList;

import java.util.Arrays;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;

@NoArgsConstructor
@Getter
public class SearchViewModel implements Parcelable {

    private ObservableSortedList<CachedPlace> places = new ObservableSortedList<>(CachedPlace.class, new ObservableSortedList.Callback<CachedPlace>() {
        @Override
        public int compare(CachedPlace o1, CachedPlace o2) {
            return o2.getLastSearched().compareTo(o1.getLastSearched());
        }

        @Override
        public boolean areItemsTheSame(CachedPlace item1, CachedPlace item2) {
            return item1.getId().equals(item2.getId());
        }

        @Override
        public boolean areContentsTheSame(CachedPlace oldItem, CachedPlace newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    });

    private ObservableSortedList<Restaurant> restaurants = new ObservableSortedList<>(Restaurant.class, new ObservableSortedList.Callback<Restaurant>() {
        @Override
        public int compare(Restaurant o1, Restaurant o2) {
            return o2.getLastSearched().compareTo(o1.getLastSearched());
        }

        @Override
        public boolean areItemsTheSame(Restaurant item1, Restaurant item2) {
            return item1.getId().equals(item2.getId());
        }

        @Override
        public boolean areContentsTheSame(Restaurant oldItem, Restaurant newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    });

    private SearchViewModel(Parcel in) {
        val savedPlaces = in.createTypedArray(CachedPlace.CREATOR);
        val savedRestaurants = in.createTypedArray(Restaurant.CREATOR);
        places.addAll(Arrays.asList(savedPlaces));
        restaurants.addAll(Arrays.asList(savedRestaurants));
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(places.toArray(new CachedPlace[places.size()]), flags);
        dest.writeTypedArray(restaurants.toArray(new Restaurant[restaurants.size()]), flags);
    }
}
