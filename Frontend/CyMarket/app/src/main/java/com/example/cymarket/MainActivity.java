package com.example.cymarket;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Button buyButton;     // define login button variable
    private Button sellButton;  // define profile button variable
    private Button messagesButton;  // define messages button variable
    private Button listingsButton;  // define settings button variable
    private TextView profileText; // define profile text button
    private TextView settingsText; // define profile text button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // link to Main activity XML

        // Initialize UI elements:
        buyButton = findViewById(R.id.main_buy_btn);
        sellButton = findViewById(R.id.main_sell_btn);
        messagesButton = findViewById(R.id.main_messages_btn);
//        listingsButton = findViewById(R.id.main_listings_btn);
        profileText = findViewById(R.id.main_profile_txt);
        settingsText = findViewById(R.id.main_settings_txt);

        // Click listener on login button pressed:
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when login button is pressed, use intent to switch to Login Activity */
                Intent intent = new Intent(MainActivity.this, BuyActivity.class);
                startActivity(intent);
            }
        });

        // Click listener on profiles button pressed:
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when signup button is pressed, use intent to switch to Signup Activity */
                Intent intent = new Intent(MainActivity.this, SellActivity.class);
                startActivity(intent);
            }
        });

        // Click listener on messages button pressed:
        messagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when signup button is pressed, use intent to switch to Signup Activity */
                Intent intent = new Intent(MainActivity.this, MessagesActivity.class);
                startActivity(intent);
            }
        });

//        // Click listener on settings button pressed:
//        listingsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                /* when signup button is pressed, use intent to switch to Signup Activity */
//                Intent intent = new Intent(MainActivity.this, ListingsActivity.class);
//                startActivity(intent);
//            }
//        });

        // Click listener on profile text button pressed:
        profileText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when signup button is pressed, use intent to switch to Signup Activity */
                Intent intent = new Intent(MainActivity.this, ProfilesActivity.class);
                startActivity(intent);
            }
        });

        // Click listener on settings text button pressed:
        settingsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when signup button is pressed, use intent to switch to Signup Activity */
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}