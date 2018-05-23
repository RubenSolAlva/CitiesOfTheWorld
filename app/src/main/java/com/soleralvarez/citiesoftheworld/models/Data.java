package com.soleralvarez.citiesoftheworld.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author : Ruben Soler Álvarez on 19/05/2018.
 *
 * Data contains arrayList with all city items and Pagination object.
 */

public class Data {
    @SerializedName("items")
    @Expose
    private ArrayList<City> items;

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public ArrayList<City> getItems() {
        return items;
    }

    public void setItems(ArrayList<City> items) {
        this.items = items;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
