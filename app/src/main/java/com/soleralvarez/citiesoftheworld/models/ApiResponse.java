package com.soleralvarez.citiesoftheworld.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author : Ruben Soler Álvarez on 19/05/2018.
 *
 * ApiResponse is used to get API data.
 */

public class ApiResponse {
    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
