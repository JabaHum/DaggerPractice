package com.example.daggerpractice.di.main;

import com.example.daggerpractice.network.main.MainApi;
import com.example.daggerpractice.ui.main.posts.PostsRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @Provides
    static PostsRecyclerAdapter providesAdapter(){
        return  new PostsRecyclerAdapter();
    }

    @Provides
    static MainApi provideMainApi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }
}
