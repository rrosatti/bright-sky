package com.example.rodri.brightsky.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rodri.brightsky.R;
import com.example.rodri.brightsky.city.City;
import com.example.rodri.brightsky.database.CityDataSource;
import com.example.rodri.brightsky.ui.CityPreference;
import com.example.rodri.brightsky.fragment.WeatherFragment;
import com.example.rodri.brightsky.ui.widget.CustomAutoCompleteView;
import com.example.rodri.brightsky.ui.widget.CustomAutoCompleteViewChangedListener;
import com.example.rodri.brightsky.util.Util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView imgChangeCity;
    TextView txtToolBarTittle;
    Typeface typeface;

    public CustomAutoCompleteView input;
    CityDataSource dataSource;
    public ArrayAdapter<String> adapter;
    public String[] cities = new String[] { "type a city..." };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setCustomToolBar();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new WeatherFragment()).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather_menu, menu);
        RelativeLayout menuLayout = (RelativeLayout) menu.findItem(R.id.chooseCity).getActionView();

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.chooseCity) {
            showInputDialog();
        }
        return false;
    }

    /**
     *
     * Create an AlertDialog where user can change the city name.
     * Call the method changeCity to continue this task.
     *
     */
    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a city");
        input = new CustomAutoCompleteView(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.addTextChangedListener(new CustomAutoCompleteViewChangedListener(this));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, cities);
        input.setAdapter(adapter);
        builder.setView(input);
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeCity(input.getText().toString());
            }
        });
        builder.show();
    }

    /**
     * Create a new WeatherFragment to get the fragment layout and then save this new city
     * by using the CityPreference (SharedPreferences)
     *
     * @param city
     */
    public void changeCity(String city) {
        WeatherFragment weatherFragment = (WeatherFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        weatherFragment.changeCity(city);
        new CityPreference(this).setCity(city);
    }

    public String[] getCitiesFromDB(String searchCity) {
        dataSource = new CityDataSource(MainActivity.this);
        try {
            dataSource.open();

            List<City> citiesFound = dataSource.searchCities(searchCity);
            int count = citiesFound.size();

            String[] cities = new String[count];
            int i = 0;

            for (City city : citiesFound) {
                cities[i] = city.getName();
                i++;
            }

            return cities;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        dataSource.close();

        return null;

    }

}
