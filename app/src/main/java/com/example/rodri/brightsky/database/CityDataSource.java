package com.example.rodri.brightsky.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rodri.brightsky.city.City;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 5/1/2016.
 */
public class CityDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper helper;
    private String[] cityColumns = { helper.COLUMN_ID, helper.COLUMN_NAME };

    public CityDataSource(Context context) {
        helper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public City createCity(String name) {
        ContentValues values = new ContentValues();
        values.put(helper.COLUMN_NAME, name);
        long insertId = database.insert(helper.TABLE_CITY, null, values);
        Cursor cursor = database.query(helper.TABLE_CITY, cityColumns, helper.COLUMN_ID + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        City city = cursorToCity(cursor);
        cursor.close();

        return city;
    }

    public City cursorToCity(Cursor cursor) {
        City city = new City();
        city.setId(cursor.getLong(0));
        city.setName(cursor.getString(1));
        return city;
    }

    public boolean findCity(String name) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + helper.TABLE_CITY + " WHERE "
                + helper.COLUMN_NAME + " = '" + name +"'", null);

        if (cursor != null) {
            if (cursor.getCount() > 0)
                return true;
        }

        return false;
    }

    public List<City> searchCities(String cityTerm) {
        List<City> cities = new ArrayList<>();

        String sql = "SELECT * FROM " + helper.TABLE_CITY + " WHERE "
                + helper.COLUMN_NAME + " LIKE '%" + cityTerm + "%'"
                + " ORDER BY " + helper.COLUMN_ID + " DESC LIMIT 0,3";

        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                City city = cursorToCity(cursor);
                cities.add(city);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return cities;
    }

}
