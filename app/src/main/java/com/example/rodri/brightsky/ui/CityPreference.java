package com.example.rodri.brightsky.ui;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by rodri on 4/29/2016.
 *
 *  This class will be used to save the user's chosen city,
 *  so he/she won't need to set the city every time when the app starts
 *
 */
public class CityPreference {

    SharedPreferences sharedPreferences;

    public CityPreference(Activity activity) {
        sharedPreferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    /** Set São Paulo as default city if the user hasn't chosen a city */
    public String getCity() {
        return sharedPreferences.getString("city", "São Paulo, BR");
    }

    public void setCity(String city) {
        sharedPreferences.edit().putString("city", city).commit();
    }
}
