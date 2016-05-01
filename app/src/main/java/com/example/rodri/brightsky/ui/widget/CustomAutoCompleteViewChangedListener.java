package com.example.rodri.brightsky.ui.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.rodri.brightsky.ui.activity.MainActivity;

/**
 * Created by rodri on 5/1/2016.
 */
public class CustomAutoCompleteViewChangedListener implements TextWatcher {

    public static final String TAG = "CustomAutoCompleteViewChangedListener.java";
    Context context;

    public CustomAutoCompleteViewChangedListener(Context context) {
        this.context = context;
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence userInput, int start, int before, int count) {
        // just to see the user input
        Log.w(this.toString(), "User input: " + userInput);

        MainActivity mainActivity = ((MainActivity) context);

        // Try to get data from the user input
        mainActivity.cities = mainActivity.getCitiesFromDB(userInput.toString());

        // update adapter with new data found
        mainActivity.adapter.notifyDataSetChanged();
        mainActivity.adapter = new ArrayAdapter<String>(mainActivity,
                android.R.layout.simple_dropdown_item_1line, mainActivity.cities);
        mainActivity.input.setAdapter(mainActivity.adapter);

    }


}
