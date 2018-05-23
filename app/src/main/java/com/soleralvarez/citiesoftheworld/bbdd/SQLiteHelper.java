package com.soleralvarez.citiesoftheworld.bbdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author : Ruben Soler Álvarez on 20/05/2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private String SQL_CREATE = "CREATE TABLE World " +
            "(city_Id INTEGER, " +
            "city_Name TEXT, " +
            "city_LocalName TEXT, " +
            "city_Latitude TEXT, " +
            "city_Longitude TEXT, " +
            "city_CreatedAt TEXT, " +
            "city_UpdatedAt TEXT, " +
            "city_CountryId INTEGER, " +
            "country_CountryId INTEGER, " +
            "country_Name TEXT, " +
            "country_Code TEXT, " +
            "country_CreatedAt TEXT, " +
            "country_UpdatedAt TEXT, " +
            "country_ContinentId INTEGER)";

    public SQLiteHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS World");
        db.execSQL(SQL_CREATE);
    }
}
