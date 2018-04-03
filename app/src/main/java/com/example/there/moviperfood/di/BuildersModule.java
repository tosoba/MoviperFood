package com.example.there.moviperfood.di;

import com.example.there.moviperfood.viper.search.SearchActivity;
import com.example.there.moviperfood.viper.search.SearchModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModule {
    @ContributesAndroidInjector(modules = {SearchModule.class})
    public abstract SearchActivity bindSearchActivity();
}
