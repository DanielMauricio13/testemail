package com.example.cymarket;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;     // define login button variable
    private Button signupButton;    // define signup button variable
    private Button profileButton;  // define profile button variable
    private Button messagesButton;  // define messages button variable
    private Button settingsButton;  // define settings button variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);             // link to Main activity XML

        // Initialize UI elements:

        // Link all the buttons:
        loginButton = findViewById(R.id.main_login_btn);
        signupButton = findViewById(R.id.main_signup_btn);
        profileButton = findViewById(R.id.main_profiles_btn);
        messagesButton = findViewById(R.id.main_messages_btn);
        settingsButton = findViewById(R.id.main_settings_btn);

        // Click listener on login button pressed:
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when login button is pressed, use intent to switch to Login Activity */
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Click listener on signup button pressed:
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when signup button is pressed, use intent to switch to Signup Activity */
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        // Click listener on profiles button pressed:
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when signup button is pressed, use intent to switch to Signup Activity */
                Intent intent = new Intent(MainActivity.this, ProfilesActivity.class);
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

        // Click listener on settings button pressed:
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when signup button is pressed, use intent to switch to Signup Activity */
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}