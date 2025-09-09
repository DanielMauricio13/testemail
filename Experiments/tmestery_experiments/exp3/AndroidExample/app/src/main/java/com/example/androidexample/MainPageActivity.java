package com.example.androidexample;

import android.os.Bundle;
import android.widget.TextClock;

import androidx.appcompat.app.AppCompatActivity;

public class MainPageActivity extends AppCompatActivity {

    private TextClock clockEdit;  // define username edittext variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Link this Activity to your layout
        setContentView(R.layout.main_page_after_login);

        // Initialize UI elements
        clockEdit = findViewById(R.id.textClock);
    }

}