package com.example.there.moviperfood.di;

import com.example.there.moviperfood.MoviperFoodApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        BuildersModule.class
})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(MoviperFoodApp application);

        Builder appModule(AppModule appModule);

        AppComponent build();
    }

    void inject(MoviperFoodApp application);
}
