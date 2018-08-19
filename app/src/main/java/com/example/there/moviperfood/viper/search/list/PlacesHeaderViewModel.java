package com.example.there.moviperfood.viper.search.list;

import android.databinding.ObservableList;

import com.example.there.moviperfood.data.place.CachedPlace;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlacesHeaderViewModel {
    private ObservableList<CachedPlace> places;
}
