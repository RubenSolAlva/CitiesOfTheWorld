package com.soleralvarez.citiesoftheworld.apiService;

import com.soleralvarez.citiesoftheworld.models.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author : Ruben Soler Álvarez on 19/05/2018.
 */

public interface Service {
    @GET("city?")
    Call<ApiResponse> getApiData(@Query("page") int page, @Query("include") String include);
}
