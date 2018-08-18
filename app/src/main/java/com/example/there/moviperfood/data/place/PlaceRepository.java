package com.example.there.moviperfood.data.place;

import com.example.there.moviperfood.domain.BasePlaceRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class PlaceRepository implements BasePlaceRepository {

    private PlaceDao placeDao;

    @Inject
    public PlaceRepository(PlacesDb db) {
        placeDao = db.placeDao();
    }

    @Override
    public Flowable<List<CachedPlace>> getPlaces() {
        return placeDao.getPlaces();
    }

    @Override
    public void insertPlace(CachedPlace place) {
        placeDao.insert(place);
    }
}
