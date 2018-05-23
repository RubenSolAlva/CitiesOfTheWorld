package com.soleralvarez.citiesoftheworld.implementations;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.soleralvarez.citiesoftheworld.apiService.RetroClient;
import com.soleralvarez.citiesoftheworld.apiService.Service;
import com.soleralvarez.citiesoftheworld.interfaces.Presenter;
import com.soleralvarez.citiesoftheworld.interfaces.Repository;
import com.soleralvarez.citiesoftheworld.models.ApiResponse;
import com.soleralvarez.citiesoftheworld.models.City;
import com.soleralvarez.citiesoftheworld.models.Pagination;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RepositoryImpl implements Repository {
    private Presenter presenter;
    private static final String TAG = RepositoryImpl.class.getCanonicalName();

    RepositoryImpl(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getData(int page) {
        Service api = RetroClient.getApiService();
        Call<ApiResponse> call = api.getApiData(page, "country");

        call.enqueue(new Callback<ApiResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<City> cityList = Objects.requireNonNull(response.body()).getData().getItems();
                    Pagination pagination = Objects.requireNonNull(response.body()).getData().getPagination();
                    presenter.responseSuccess(cityList, pagination);
                } else {
                    presenter.responseError();
                    Log.d(TAG, "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                presenter.responseError();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
