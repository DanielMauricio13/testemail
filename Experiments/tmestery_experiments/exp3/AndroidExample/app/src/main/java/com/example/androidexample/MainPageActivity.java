package com.example.androidexample;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextClock;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainPageActivity extends AppCompatActivity {

    private TextClock clockEdit;  // define username edittext variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Link this Activity to your layout
        setContentView(R.layout.main_page_after_login);

        // Initialize UI elements
        clockEdit = findViewById(R.id.textClock);

        // Change the colors
        dayOrNightDisplay(clockEdit);
    }

    /*
    Changes the screen colors depending on the time of day
     */
    protected void dayOrNightDisplay(TextClock clockEdit) {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        // Access rootView so background can be changed
        View rootView = findViewById(android.R.id.content);

        // Checking the time of day AM or PM
        if (hour >= 7 && hour < 19) {
            rootView.setBackgroundColor(Color.parseColor("#99ccff")); // Set background color
            clockEdit.setBackgroundColor(Color.parseColor("#0099ff")); // Set clock background
            clockEdit.setTextColor(Color.parseColor("#99ccff")); // Set clock text color
        } else { // Time of day is PM
            rootView.setBackgroundColor(Color.parseColor("#1C1C1C")); // Set background color
            clockEdit.setBackgroundColor(Color.parseColor("#2A2A2A")); // Set clock background color
            clockEdit.setTextColor(Color.parseColor("#E0E0E0")); // Set clock text color
        }
    }
}