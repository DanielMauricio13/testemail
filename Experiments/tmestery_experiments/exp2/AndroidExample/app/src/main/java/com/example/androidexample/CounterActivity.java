package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class CounterActivity extends AppCompatActivity {

    private TextView numberTxt; // define number textview variable
    private Button increaseBtn; // define increase button variable
    private Button decreaseBtn; // define decrease button variable
    private Button backBtn;     // define back button variable
    private Button randomNumButton;     // random number generator button between a range

    private int counter = 0;    // counter variable

    protected void colorChange() {
        if (counter >= 0) { // If it's an even number make it green
            numberTxt.setTextColor(Color.GREEN);
        } else { // If it's an odd number make it red
            numberTxt.setTextColor(Color.RED);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        /*
        initialize UI elements
        */
        numberTxt = findViewById(R.id.number);
        increaseBtn = findViewById(R.id.counter_increase_btn);
        decreaseBtn = findViewById(R.id.counter_decrease_btn);
        backBtn = findViewById(R.id.counter_back_btn);
        randomNumButton = findViewById(R.id.randomNumButton);  // Set up the button

        /*
        when increase btn is pressed, counter++, reset number textview
        */
        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberTxt.setText(String.valueOf(++counter));
                colorChange();
            }
        });

        /*
        when decrease btn is pressed, counter--, reset number textview
         */
        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberTxt.setText(String.valueOf(--counter));
                colorChange();
            }
        });

        /*
        when back btn is pressed, switch back to MainActivity
        */
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CounterActivity.this, MainActivity.class);
                intent.putExtra("NUM", String.valueOf(counter));  // key-value to pass to the MainActivity
                startActivity(intent);
            }
        });

        /*
        when button is pressed it generates a random number for the number text display
         */
        randomNumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int randomNumber = random.nextInt(500);
                numberTxt.setText(String.valueOf(randomNumber)); // Important because it accepts strings current not int
            }
        });

        numberTxt.setTextColor(Color.GREEN);
    }
}