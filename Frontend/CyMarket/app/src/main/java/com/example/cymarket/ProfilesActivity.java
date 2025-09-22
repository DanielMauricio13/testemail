package com.example.cymarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ProfilesActivity extends AppCompatActivity {

    private Button homeButton;  // define profile button variable
    private Button messagesButton;  // define messages button variable
    private Button settingsButton;  // define settings button variable

    private Button notificationsButton; // define notfications button variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);

        // Link all the buttons:
        homeButton = findViewById(R.id.prfls_home_page_btn);
        messagesButton = findViewById(R.id.prfls_messages_btn);
        settingsButton = findViewById(R.id.prfls_setting_btn);
        notificationsButton = findViewById(R.id.prfls_notifs_btn);

        // Click listener on home button pressed:
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Click listener on messages button pressed:
        messagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilesActivity.this, MessagesActivity.class);
                startActivity(intent);
            }
        });

        // Click listener on settings button pressed:
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilesActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // Click listener on notifications button pressed:
        notificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilesActivity.this, NotificationsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}