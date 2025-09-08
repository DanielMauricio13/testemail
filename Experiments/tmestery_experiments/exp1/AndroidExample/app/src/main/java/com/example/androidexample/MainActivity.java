package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;

import android.app.ActivityManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.Toast;


import org.w3c.dom.Text;

/*

1. To run this project, open the directory "Android Example", otherwise it may not recognize the file structure properly

2. Ensure you are using a compatible version of gradle, to do so you need to check 2 files.

    AndroidExample/Gradle Scripts/build.gradle
    Here, you will have this block of code. Ensure it is set to a compatible version,
    in this case 8.12.2 should be sufficient:
        plugins {
            id 'com.android.application' version '8.12.2' apply false
        }

    Gradle Scripts/gradle-wrapper.properties

3. This file is what actually determines the Gradle version used, 8.13 should be sufficient.
    "distributionUrl=https\://services.gradle.org/distributions/gradle-8.13-bin.zip" ---Edit the version if needed

4. You might be instructed by the plugin manager to upgrade plugins, accept it and you may execute the default selected options.

5. Press "Sync project with gradle files" located at the top right of Android Studio,
   once this is complete you will be able to run the app

   This version is compatible with both JDK 17 and 21. The Java version you want to use can be
   altered in Android Studio->Settings->Build, Execution, Deployment->Build Tools->Gradle

 */

public class MainActivity extends AppCompatActivity {

    private TextView messageText;   // define message textview variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);             // link to Main activity XML
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#BBDEFB")); // Set background color of entire window

        /* initialize UI elements */
        messageText = findViewById(R.id.main_msg_txt);      // link to message textview in the Main activity XML
        messageText.setTypeface(null, Typeface.BOLD);   // Changes the text to be bold
        messageText.setTextColor(Color.parseColor("#009688"));  // Changes text color
        messageText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);         // Centers the text
        messageText.setShadowLayer(9f, 7f, 7f, Color.GRAY);  // Adds a shadow to the text

        // Auto re-sizing feature for the text
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
                messageText,
                50,  // minimum size
                80,  // maximum size
                3,   // step size
                TypedValue.COMPLEX_UNIT_SP
        );

        // Listener that displays "CyMarket clicked!" when pressed
        messageText.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "CyMarket clicked!", Toast.LENGTH_SHORT).show()
        );

        messageText.setText("CyMarket");
    }
}