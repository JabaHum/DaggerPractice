package com.example.daggerpractice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.example.daggerpractice.models.User;
import com.example.daggerpractice.ui.auth.AuthActivity;
import com.example.daggerpractice.ui.auth.AuthResource;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {
    private static final String TAG = "BaseActivity";

    @Inject
    public SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribeObeservers();
    }

    private void subscribeObeservers(){
        sessionManager.getAuthUser().observe(BaseActivity.this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource !=null){
                    switch (userAuthResource.status){
                        case ERROR:
                            Log.e(TAG, "onChanged: Error" );
                        case AUTHENTICATED:
                            if (userAuthResource.data != null) {
                                Log.d(TAG, "onChanged: LOGIN SUCCESS :"+userAuthResource.data.getEmail());
                            }
                            break;
                        case LOADING:
                            Log.e(TAG, "onChanged: Loading" );
                            break;
                        case NOT_AUTHENTICATED:
                            Log.e(TAG, "onChanged: Not Authenticated" );
                            navLoginScreen();
                            break;

                    }
                }
            }
        });
    }

    private void navLoginScreen(){
        Intent intent = new Intent(BaseActivity.this,AuthActivity.class);
        startActivity(intent);
        finish();
    }
}
