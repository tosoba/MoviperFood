package com.example.there.moviperfood.data.place;

import com.example.there.moviperfood.domain.BasePlaceRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class PlaceRepository implements BasePlaceRepository {

    private PlaceDao placeDao;

    @Inject
    public PlaceRepository(PlacesDb db) {
        placeDao = db.placeDao();
    }

    @Override
    public Flowable<List<CachedPlace>> getSavedPlaces() {
        return placeDao.getPlaces();
    }

    @Override
    public Completable insertPlace(CachedPlace place) {
        return Completable.fromAction(() -> placeDao.insert(place));
    }

    @Override
    public Completable deleteMostRecentlyAddedPlace() {
        return Completable.fromAction(() -> placeDao.deleteMostRecentlyAddedPlace());
    }
}
