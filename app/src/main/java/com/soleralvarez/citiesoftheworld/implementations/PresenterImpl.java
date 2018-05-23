package com.soleralvarez.citiesoftheworld.implementations;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.soleralvarez.citiesoftheworld.bbdd.SQLiteHelper;
import com.soleralvarez.citiesoftheworld.interfaces.Presenter;
import com.soleralvarez.citiesoftheworld.interfaces.Repository;
import com.soleralvarez.citiesoftheworld.interfaces.View;
import com.soleralvarez.citiesoftheworld.models.City;
import com.soleralvarez.citiesoftheworld.models.Country;
import com.soleralvarez.citiesoftheworld.models.Pagination;

import java.util.ArrayList;

public class PresenterImpl implements Presenter {
    private Context ctx;
    private View view;
    private Repository repository;
    private static final String TAG = PresenterImpl.class.getCanonicalName();
    private int page = 1;
    private SharedPreferences prefs;
    private ArrayList<City> arrayListCityDB = new ArrayList<>();

    PresenterImpl(View view, Context ctx) {
        this.ctx = ctx;
        this.view = view;
        this.repository = new RepositoryImpl(this);
    }

    @Override
    public void getData(Boolean isScrollDataRequest, Boolean isFromMap) {
        //Get page saved
        prefs = ctx.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        page = prefs.getInt("page", 1);

        if (page == 1) {
            repository.getData(page);
            Log.d(TAG, "Repository get FIRST PAGE: " + page);
        } else {
            if (isScrollDataRequest) {
                view.showProgressBar();
                repository.getData(page);
                Log.d(TAG, "Repository get PAGE: " + page);
            } else {
                Log.d(TAG, "GET CACHE");
                view.onGetCacheDataSuccess(obtainCacheData(), isFromMap);
            }
        }
    }

    @Override
    public void responseSuccess(ArrayList<City> arrayCity, Pagination pagination) {
        insertDataToDB(arrayCity);
        view.onGetDataSuccess(arrayCity);
        if (pagination.getCurrent_page() != pagination.getLast_page())
            page = pagination.getCurrent_page() + 1;

        view.hideProgressBar();
        Log.d(TAG, "SINCHRONIZED");

        //Save page
        prefs = ctx.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("page", page);
        editor.apply();
    }

    @Override
    public void responseError() {
        Log.d(TAG, "PRESENTER ERROR");
        view.hideProgressBar();
        view.onGetDataError();
    }

    private void insertDataToDB(ArrayList<City> arrayCity) {
        //Open DB writable mode
        SQLiteHelper sqLiteHelper = new SQLiteHelper(ctx, "DBWorld", null, 1);
        SQLiteDatabase sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        for (int i = 0; i < arrayCity.size(); i++) {
            //Insert content into DB
            ContentValues contentValues = new ContentValues();
            contentValues.put("city_Id", arrayCity.get(i).getId());
            contentValues.put("city_Name", arrayCity.get(i).getName());
            contentValues.put("city_LocalName", arrayCity.get(i).getLocal_name());
            contentValues.put("city_Latitude", arrayCity.get(i).getLat());
            contentValues.put("city_Longitude", arrayCity.get(i).getLng());
            contentValues.put("city_CreatedAt", arrayCity.get(i).getCreated_at());
            contentValues.put("city_UpdatedAt", arrayCity.get(i).getUpdated_at());
            contentValues.put("city_CountryId", arrayCity.get(i).getCountry_id());
            contentValues.put("country_CountryId", arrayCity.get(i).getCountry().getId());
            contentValues.put("country_Name", arrayCity.get(i).getCountry().getName());
            contentValues.put("country_Code", arrayCity.get(i).getCountry().getCode());
            contentValues.put("country_CreatedAt", arrayCity.get(i).getCountry().getCreated_at());
            contentValues.put("country_UpdatedAt", arrayCity.get(i).getCountry().getUpdated_at());
            contentValues.put("country_ContinentId", arrayCity.get(i).getCountry().getContinent_id());
            sqLiteDatabase.insert("World", null, contentValues);
            Log.d(TAG, "Inserted id: " + arrayCity.get(i).getId());
        }
        sqLiteDatabase.close();
    }

    private ArrayList<City> obtainCacheData() {
        //Open DB writable mode
        SQLiteHelper sqLiteHelper = new SQLiteHelper(ctx, "DBWorld", null, 1);
        SQLiteDatabase sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        //Checking table registers
        String count = "SELECT count(*) FROM World";
        @SuppressLint("Recycle") Cursor mcursor = sqLiteDatabase.rawQuery(count, null);
        mcursor.moveToFirst();
        int registers = mcursor.getInt(0);
        Log.d(TAG, "registers: " + registers);

        @SuppressLint("Recycle") Cursor c = sqLiteDatabase.rawQuery("SELECT " +
                "city_Id, " +
                "city_Name, " +
                "city_Latitude, " +
                "city_Longitude, " +
                "country_Name, " +
                "country_ContinentId " +
                "FROM World", null);
        if (c.moveToFirst()) {
            for (int i = 0; i < registers; i++) {
                City city = new City();
                Country country = new Country();
                city.setId(c.getInt(0));
                city.setName(c.getString(1));
                city.setLat(c.getString(2));
                city.setLng(c.getString(3));
                country.setName(c.getString(4));
                country.setContinent_id(c.getInt(5));
                city.setCountry(country);
                arrayListCityDB.add(city);
                Log.d(TAG, "DBid: " + c.getInt(0));
                c.moveToNext();
            }
        }
        sqLiteDatabase.close();
        return arrayListCityDB;
    }
}
