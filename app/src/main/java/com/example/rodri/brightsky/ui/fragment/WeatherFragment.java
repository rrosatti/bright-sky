package com.example.rodri.brightsky.ui.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rodri.brightsky.R;
import com.example.rodri.brightsky.ui.CityPreference;
import com.example.rodri.brightsky.ui.json.RemoteFetch;

import android.os.Handler;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rodri on 4/29/2016.
 */
public class WeatherFragment extends Fragment {

    TextView txtCurrentWeather;
    TextView txtCityAndCountry;
    TextView txtLastUpdate;
    ImageView imgWeatherIcon;
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
        txtLastUpdate = (TextView) rootView.findViewById(R.id.txtLastUpdate);
        imgWeatherIcon = (ImageView) rootView.findViewById(R.id.imgWeatherIcon);

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
            JSONObject main = json.getJSONObject("main");
            JSONObject details = json.getJSONArray("weather").getJSONObject(0);

            txtCurrentWeather.setText(String.format("%.2f", main.getDouble("temp")) + " ºC");
            setWeatherIcon(details.getInt("id"));

            txtCityAndCountry.setText(json.getString("name").toUpperCase(Locale.US) +
                    ", " + json.getJSONObject("sys").getString("country"));

            DateFormat dateFormat = DateFormat.getDateTimeInstance();
            String lastTimeUpdated = dateFormat.format(new Date(json.getLong("dt")*1000));
            txtLastUpdate.setText("Last update: " + lastTimeUpdated);

        } catch (Exception e) {
            Log.e("BrightSky", "One or more fields not found in the JSON data.");
        }
    }

    private void setWeatherIcon(int id) {
        if (id > 800 && id < 900)
            id = 1000;
        else if (id >= 900)
            id = 900;

        switch (id/100) {
            case 8:
                imgWeatherIcon.setImageResource(R.drawable.w800);
                break;
            case 2:
                imgWeatherIcon.setImageResource(R.drawable.w200);
                break;
            case 3:
                imgWeatherIcon.setImageResource(R.drawable.w300);
                break;
            case 5:
                imgWeatherIcon.setImageResource(R.drawable.w500);
                break;
            case 6:
                imgWeatherIcon.setImageResource(R.drawable.w600);
                break;
            case 7:
                imgWeatherIcon.setImageResource(R.drawable.w700);
                break;
            case 9:
                imgWeatherIcon.setImageResource(R.drawable.w900_plus);
                break;
            case 10:
                imgWeatherIcon.setImageResource(R.drawable.w800_plus);
                break;
        }
    }

    public void changeCity(String city) {
        updateWeatherData(city);
    }
}
