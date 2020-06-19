package com.example.daggerpractice.ui.main.posts;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

public class PostsViewModel extends ViewModel {
    private static final String TAG = "PostsViewModel";

    @Inject

    public PostsViewModel() {
        Log.d(TAG, "PostsViewModel: Posts View Model is ready");
    }
}
