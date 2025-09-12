package com.example.androidexample;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextClock;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainPageActivity extends AppCompatActivity {

    private TextClock clockEdit; // define the clock var
    private TextView welcomeText; // define the welcome message var

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_after_login);

        // Initialize UI elements
        clockEdit = findViewById(R.id.textClock);
        welcomeText = findViewById(R.id.textView); // <-- ADD THIS

        // Change the colors
        dayOrNightDisplay(clockEdit);
        displayWelcomeText();
    }

    /*
     Changes based on the time of day
     */
    private String getTimeGreeting() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        if (hour >= 5 && hour < 8) {
            return "🌅 Bright and early morning";
        } else if (hour >= 8 && hour < 12) {
            return "☀️ Good morning";
        } else if (hour >= 12 && hour < 14) {
            return "🍽 Good mid-day";
        } else if (hour >= 14 && hour < 17) {
            return "🌤 Good afternoon";
        } else if (hour >= 17 && hour < 19) {
            return "🌇 Good early evening";
        } else if (hour >= 19 && hour < 22) {
            return "🌙 Good evening";
        } else if (hour >= 22 && hour < 24) {
            return "🌌 Good night";
        } else { // 0–5 AM
            return "🌃 Late night studying";
        }
    }

    /*
    Displays a stylish welcome message to the user,
    adapting to time of day and username input.
*/
    private void displayWelcomeText() {
        String username = getIntent().getStringExtra("USERNAME");
        if (username == null || username.trim().isEmpty()) {
            username = "Guest";
        }

        String message = getTimeGreeting() + ", " + username + "!";   // Writing out the message with all pieces
        welcomeText.setTextColor(Color.parseColor("#FFFFFF")); // Setting color of text to white
        welcomeText.setText(message); // Setting message text
        welcomeText.setGravity(Gravity.CENTER); // Centering the textview item
        welcomeText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 34); // Setting font size
        welcomeText.setSingleLine(false); // Creating multiple lines of text
        welcomeText.setEllipsize(TextUtils.TruncateAt.END);
        welcomeText.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
        welcomeText.setShadowLayer(6, 3, 3, Color.parseColor("#99000000")); // creating a shadow
        welcomeText.setAlpha(0f);
        welcomeText.animate() // Animating the text when it comes on the screen at the start
                .alpha(1f)
                .setDuration(1200)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
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