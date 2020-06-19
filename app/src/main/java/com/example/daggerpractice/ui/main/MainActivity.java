package com.example.daggerpractice.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.daggerpractice.BaseActivity;
import com.example.daggerpractice.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerlayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerlayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);


        init();

    }

    private void init() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mDrawerlayout);
        NavigationUI.setupWithNavController(mNavigationView, navController);
        mNavigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.menu_logout:
                sessionManager.logOut();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_profile:
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.profileScreen);
                break;
            case R.id.nav_posts:
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.postsScreen);
                break;
        }

        menuItem.setChecked(true);
        mDrawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
