package com.example.rodri.brightsky.ui.json;

import android.content.Context;

import com.example.rodri.brightsky.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rodri on 4/29/2016.
 */
public class RemoteFetch {


    private static final String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";

    public static JSONObject getJSON(Context context, String city) {
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_API, city));
            /** HttpURLConnection is used to make a remote request */
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            /** Set API Key for the OpenWeatherMapsAPI*/
            connection.addRequestProperty("x-api-key", context.getString(R.string.open_weather_maps_app_id));

            /** Use BufferedReader and StringBuffer to read the API's response */
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp = "";
            while((tmp = reader.readLine()) != null) {
                json.append(tmp).append("\n");
            }
            reader.close();

            /** Convert the response into a JSONObject */
            JSONObject data = new JSONObject(json.toString());

            /**
             * w200 - successful request
             * 404 - unsuccessful request
             */
            if (data.getInt("cod") != 200) {
                return null;
            }

            return data;
        } catch (Exception e) {
            return null;
        }
    }

}
