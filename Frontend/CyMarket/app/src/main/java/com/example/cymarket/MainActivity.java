package com.example.cymarket;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    // private Button buyButton;     // define buy button variable
    // private Button sellButton;    // define sell button variable
    // private Button messagesButton;  // define messages button variable
    private Button profileText; // define profile text button
    private Button settingsText; // define settings text button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // link to Main activity XML

        // --- OLD Buttons (no longer used, replaced with BottomNavigationView) ---
        /*
        buyButton = findViewById(R.id.main_buy_btn);
        sellButton = findViewById(R.id.main_sell_btn);
        messagesButton = findViewById(R.id.main_messages_btn);
        */

        profileText = findViewById(R.id.main_profile_btn);
        settingsText = findViewById(R.id.main_settings_btn);

        String username = getIntent().getStringExtra("username");

        // --- OLD button click listeners (commented out and replaced) ---
        /*
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BuyActivity.class);
                startActivity(intent);
            }
        });

        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SellActivity.class);
                startActivity(intent);
            }
        });

        messagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MessagesActivity.class);
                startActivity(intent);
            }
        });
        */

        // --- Keep Top Buttons (Profile & Settings) ---
        profileText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfilesActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        settingsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // --- NEW Bottom Navigation (Buy / Sell / Chat) ---
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_buy) {
                startActivity(new Intent(MainActivity.this, BuyActivity.class));
                return true;
            } else if (id == R.id.nav_sell) {
                startActivity(new Intent(MainActivity.this, SellActivity.class));
                return true;
            } else if (id == R.id.nav_chat) {
                startActivity(new Intent(MainActivity.this, MessagesActivity.class));
                return true;
            }
            return false;
        });
    }
}