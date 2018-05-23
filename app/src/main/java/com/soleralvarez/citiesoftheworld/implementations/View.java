package com.soleralvarez.citiesoftheworld.implementations;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.soleralvarez.citiesoftheworld.R;
import com.soleralvarez.citiesoftheworld.fragments.HomeFragment;
import com.soleralvarez.citiesoftheworld.fragments.MapFragment;
import com.soleralvarez.citiesoftheworld.interfaces.Presenter;
import com.soleralvarez.citiesoftheworld.models.City;

import java.util.ArrayList;

public class View extends AppCompatActivity implements com.soleralvarez.citiesoftheworld.interfaces.View {
    private Presenter presenter;
    private static final String TAG = com.soleralvarez.citiesoftheworld.interfaces.View.class.getCanonicalName();
    private HomeFragment homeFragment;
    private MapFragment mapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mInstance = this;
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        homeFragment = new HomeFragment();
        mapFragment = new MapFragment();
        loadFragment(homeFragment);


        presenter = new PresenterImpl(this, this);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getData(false, false);
    }

    @Override
    public void getData(boolean isScrollDataRequest, boolean isFromMap) {
        presenter.getData(isScrollDataRequest, isFromMap);
    }

    @Override
    public void onGetCacheDataSuccess(ArrayList<City> arrayCity, Boolean isFromMap) {
        if (isFromMap) {
            mapFragment.addDataCache(arrayCity);
        } else {
            homeFragment.addDataCache(arrayCity);
        }

    }

    @Override
    public void onGetDataSuccess(ArrayList<City> arrayCity) {
        homeFragment.addData(arrayCity);
    }

    @Override
    public void onGetDataError() {
        Log.d(TAG, "VIEW ERROR");
    }

    @Override
    public void showProgressBar() {
        homeFragment.showProgress();
    }

    @Override
    public void hideProgressBar() {
        homeFragment.hideProgress();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (homeFragment == null) {
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                    } else {
                        loadFragment(homeFragment);
                    }
                    return true;
                case R.id.navigation_map:
                    if (mapFragment == null) {
                        fragment = new MapFragment();
                        loadFragment(fragment);
                    } else {
                        loadFragment(mapFragment);
                    }
                    return true;
            }
            return false;
        }

    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
