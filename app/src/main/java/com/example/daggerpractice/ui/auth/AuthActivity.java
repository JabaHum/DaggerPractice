package com.example.daggerpractice.ui.auth;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;
import com.example.daggerpractice.R;
import com.example.daggerpractice.models.User;
import com.example.daggerpractice.viewModels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AuthActivity";
    @Inject
    ViewModelProviderFactory providerFactory;

    AuthViewModel viewModel;


    EditText userId;
    ProgressBar progressBar;

    @Inject
    Drawable logo;
    @Inject
    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        userId = findViewById(R.id.user_id_input);
        progressBar = findViewById(R.id.progress_bar);

        findViewById(R.id.login_button).setOnClickListener(this);

        viewModel = ViewModelProviders.of(this, providerFactory).get(AuthViewModel.class);

        setLogo();
        subscribeObservers();
    }

    private void subscribeObservers(){
       viewModel.observeAuthState().observe(this, new Observer<AuthResource<User>>() {
           @Override
           public void onChanged(AuthResource<User> userResource) {
               if (userResource !=null){
                   switch (userResource.status){
                       case ERROR:
                           showProgressBar(false);
                           Toast.makeText(AuthActivity.this, userResource.message+"\nDid you enter a number between 1 and 10", Toast.LENGTH_SHORT).show();
                       case AUTHENTICATED:
                           showProgressBar(false);
                           if (userResource.data != null) {
                               Log.d(TAG, "onChanged: LOGIN SUCCESS :"+userResource.data.getEmail());
                           }
                           break;
                       case LOADING:
                           showProgressBar(true);
                           break;
                       case NOT_AUTHENTICATED:
                           showProgressBar(false);
                           break;

                   }
               }
           }
       });
    }

    private void showProgressBar(boolean isVisible){
        if (isVisible){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }

    }

    private void setLogo() {
        requestManager
                .load(logo)
                .into((ImageView) findViewById(R.id.login_logo));
    }

    @Override
    public void onClick(View view) {
        int v = view.getId();
        if (v == R.id.login_button) {
            attemptLogin();
        }

    }

    private void attemptLogin() {
        if (TextUtils.isEmpty(userId.getText().toString())) {
            return;
        } else {
            viewModel.authenticateWithId(Integer.parseInt(userId.getText().toString()));
        }
    }
}
