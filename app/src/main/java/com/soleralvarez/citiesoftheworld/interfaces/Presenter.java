package com.soleralvarez.citiesoftheworld.interfaces;

import com.soleralvarez.citiesoftheworld.models.City;
import com.soleralvarez.citiesoftheworld.models.Pagination;

import java.util.ArrayList;

/**
 * @author : Ruben Soler Álvarez on 19/05/2018.
 *
 * Interface that represents presenter methods.
 */

public interface Presenter {

    void getData(Boolean isScrollDataRequest, Boolean isFromMap);
    void responseSuccess(ArrayList<City> arrayCity, Pagination pagination);
    void responseError();
}
