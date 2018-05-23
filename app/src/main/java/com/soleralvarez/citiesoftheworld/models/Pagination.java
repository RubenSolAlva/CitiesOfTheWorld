package com.soleralvarez.citiesoftheworld.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author : Ruben Soler Álvarez on 19/05/2018.
 *
 * Pagination contains pagination params.
 */

public class Pagination {
    @SerializedName("current_page")
    @Expose
    private int current_page;

    @SerializedName("last_page")
    @Expose
    private int last_page;

    @SerializedName("per_page")
    @Expose
    private int per_page;

    @SerializedName("total")
    @Expose
    private int total;

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
