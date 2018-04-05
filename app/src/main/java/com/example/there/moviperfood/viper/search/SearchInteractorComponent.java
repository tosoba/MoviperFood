package com.example.there.moviperfood.viper.search;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {SearchInteractorModule.class})
public interface SearchInteractorComponent {
    @Component.Builder
    interface Builder {
        SearchInteractorComponent.Builder searchInteractorModule(SearchInteractorModule searchInteractorModule);

        SearchInteractorComponent build();
    }

    void inject(SearchInteractor interactor);
}
