package com.example.there.moviperfood.di;

import com.example.there.moviperfood.MoviperFoodApp;
import com.example.there.moviperfood.di.module.ApiModule;
import com.example.there.moviperfood.di.module.AppModule;
import com.example.there.moviperfood.di.module.CacheModule;
import com.example.there.moviperfood.di.module.DataModule;
import com.example.there.moviperfood.domain.BaseFoodRepository;
import com.example.there.moviperfood.domain.BasePlaceRepository;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        ApiModule.class,
        CacheModule.class,
        DataModule.class
})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(MoviperFoodApp application);

        AppComponent build();
    }

    BaseFoodRepository baseFoodRepository();
    BasePlaceRepository basePlaceRepository();

    void inject(MoviperFoodApp app);
}
