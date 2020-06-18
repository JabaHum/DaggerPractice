package com.example.daggerpractice.di;

import com.example.daggerpractice.di.auth.AuthViewModelsModule;
import com.example.daggerpractice.network.auth.AuthModule;
import com.example.daggerpractice.ui.auth.AuthActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(modules = {
            AuthViewModelsModule.class,
            AuthModule.class,
    })
    abstract AuthActivity contributeAuthActivity();

}
