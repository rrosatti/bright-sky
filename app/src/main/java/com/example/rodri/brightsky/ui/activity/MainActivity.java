package com.example.rodri.brightsky.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodri.brightsky.R;
import com.example.rodri.brightsky.ui.CityPreference;
import com.example.rodri.brightsky.fragment.WeatherFragment;
import com.example.rodri.brightsky.util.Util;

public class MainActivity extends AppCompatActivity {

    ImageView imgChangeCity;
    TextView txtToolBarTittle;
    Typeface typeface;

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
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
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

    public void setCustomToolBar() {
        /**LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.fragment_weather, null);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.customToolBar);
        setSupportActionBar(toolbar);
        imgChangeCity = (ImageView) toolbar.findViewById(R.id.imgChangeCity);
        txtToolBarTittle = (TextView) toolbar.findViewById(R.id.txtToolBarTitle);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Bold.otf");
        txtToolBarTittle.setTypeface(typeface);*/

    }
}
