package com.example.there.moviperfood.viper.search;

import android.support.annotation.NonNull;

import com.mateuszkoslacz.moviper.base.presenter.BaseRxPresenter;

public class SearchPresenter
        extends BaseRxPresenter<SearchContract.View, SearchContract.Interactor, SearchContract.Routing>
        implements SearchContract.Presenter {

    @NonNull
    @Override
    public SearchContract.Routing createRouting() {
        return new SearchRouting();
    }

    @NonNull
    @Override
    public SearchContract.Interactor createInteractor() {
        return new SearchInteractor();
    }
}
