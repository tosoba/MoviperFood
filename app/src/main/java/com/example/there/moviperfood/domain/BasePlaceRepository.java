package com.example.there.moviperfood.domain;

import com.example.there.moviperfood.data.place.CachedPlace;

import java.util.List;

import io.reactivex.Flowable;

public interface BasePlaceRepository {
    Flowable<List<CachedPlace>> getPlaces();
    void insertPlace(CachedPlace place);
}
