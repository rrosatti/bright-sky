package com.example.rodri.brightsky.ui.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rodri.brightsky.R;
import com.example.rodri.brightsky.ui.CityPreference;
import com.example.rodri.brightsky.ui.json.RemoteFetch;

import android.os.Handler;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import org.json.JSONObject;

import java.util.Locale;

/**
 * Created by rodri on 4/29/2016.
 */
public class WeatherFragment extends Fragment {

    TextView txtCurrentWeather;
    TextView txtCityAndCountry;
    /** handler will be used to use a separate Thread to fetch data from the OpenWeatherMap API (asynchronously) */
    Handler handler;

    public WeatherFragment() {
        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        txtCurrentWeather = (TextView) rootView.findViewById(R.id.txtCurrentWeather);
        txtCityAndCountry = (TextView) rootView.findViewById(R.id.txtCityAndCountry);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateWeatherData(new CityPreference(getActivity()).getCity());
    }

    private void updateWeatherData(final String city) {
        new Thread() {
            public void run() {
                final JSONObject json = RemoteFetch.getJSON(getActivity(), city);
                /** if json = null we show an error message, else we start renderWeather() */
                if (json == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), R.string.city_not_found, Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    private void renderWeather(JSONObject json) {
        try {
            txtCityAndCountry.setText(json.getString("name").toUpperCase(Locale.US) +
            ", " + json.getJSONObject("sys").getString("country"));

            JSONObject main = json.getJSONObject("main");

            txtCurrentWeather.setText(String.format("%.2f", main.getDouble("temp")) + " ºC");
        } catch (Exception e) {
            Log.e("BrightSky", "One or more fields not found in the JSON data.");
        }
    }

    public void changeCity(String city) {
        updateWeatherData(city);
    }
}
