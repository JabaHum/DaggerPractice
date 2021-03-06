package com.example.daggerpractice.di;

import com.example.daggerpractice.di.auth.AuthModule;
import com.example.daggerpractice.di.auth.AuthViewModelsModule;
import com.example.daggerpractice.di.main.MainBuildersModule;
import com.example.daggerpractice.di.main.MainModule;
import com.example.daggerpractice.di.main.MainViewModelsModule;
import com.example.daggerpractice.ui.auth.AuthActivity;
import com.example.daggerpractice.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(modules = {
            AuthViewModelsModule.class,
            AuthModule.class,
    })
    abstract AuthActivity contributeAuthActivity();

    @ContributesAndroidInjector(modules = {
            MainBuildersModule.class,
            MainViewModelsModule.class,
            MainModule.class
    })
    abstract MainActivity contributeMainActivity();

}
