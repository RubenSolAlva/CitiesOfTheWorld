package com.soleralvarez.citiesoftheworld.apiService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author : Ruben Soler Álvarez on 19/05/2018.
 */

public class RetroClient {
    private static final String BASE_URL = "http://connect-demo.mobile1.io/square1/connect/v1/";

    public static Service getApiService() {
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(Service.class);
    }
}
