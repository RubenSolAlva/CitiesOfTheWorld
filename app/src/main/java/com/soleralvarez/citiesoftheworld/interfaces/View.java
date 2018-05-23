package com.soleralvarez.citiesoftheworld.interfaces;

import com.soleralvarez.citiesoftheworld.models.City;

import java.util.ArrayList;

/**
 * @author : Ruben Soler Álvarez on 19/05/2018.
 *
 * Interface that represents view methods.
 */
public interface View {
    void getData(boolean isScrollDataRequest, boolean isFromMap);

    void onGetCacheDataSuccess(ArrayList<City> arrayCity, Boolean isFromMap);

    void onGetDataSuccess(ArrayList<City> arrayCity);

    void onGetDataError();

    void showProgressBar();

    void hideProgressBar();
}
