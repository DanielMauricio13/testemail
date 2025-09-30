package com.example.cymarket;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfilesActivity extends AppCompatActivity {

    private Button homeButton;
    private Button messagesButton;
    private Button settingsButton;
    private TextView usernameText;
    private Button notificationsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);

        homeButton = findViewById(R.id.prfls_home_page_btn);
        messagesButton = findViewById(R.id.prfls_messages_btn);
        settingsButton = findViewById(R.id.prfls_setting_btn);
        usernameText = findViewById(R.id.username_text);
        notificationsButton = findViewById(R.id.prfls_notifs_btn);

        String username = getIntent().getStringExtra("username");

        // Display username
        usernameText.setText(username);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilesActivity.this, MainActivity.class);
            startActivity(intent);
        });

        messagesButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilesActivity.this, MessagesActivity.class);
            startActivity(intent);
        });

        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilesActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        notificationsButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilesActivity.this, NotificationsActivity.class);
            startActivity(intent);
        });
    }
}