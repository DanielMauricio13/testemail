package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CounterActivity extends AppCompatActivity {

    private TextView numberTxt; // define number textview variable
    private Button increaseBtn; // define increase button variable
    private Button decreaseBtn; // define decrease button variable
    private Button backBtn;     // define back button variable
    private Button increase5Btn;
    private Button decrease5Btn;

    private int counter = 0;    // counter variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        /* initialize UI elements */
        numberTxt = findViewById(R.id.number);
        increaseBtn = findViewById(R.id.counter_increase_btn);
        decreaseBtn = findViewById(R.id.counter_decrease_btn);
        backBtn = findViewById(R.id.counter_back_btn);
        increase5Btn = findViewById(R.id.counter_increase5_btn);
        decrease5Btn = findViewById(R.id.counter_decrease5_btn);

        /* when increase btn is pressed, counter++, reset number textview */
        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            updateCounterDisplay();
            }
        });

        /* when decrease btn is pressed, counter--, reset number textview */
        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter--;
                numberTxt.setText(String.valueOf(--counter));
                updateCounterDisplay();
            }
        });

        increase5Btn.setOnClickListener(v -> {
            counter += 4;
            updateCounterDisplay();
        });

        decrease5Btn.setOnClickListener(V ->{
            counter -=6;
            updateCounterDisplay();
        });

        /* when back btn is pressed, switch back to MainActivity */
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CounterActivity.this, MainActivity.class);
                intent.putExtra("NUM", String.valueOf(counter));  // key-value to pass to the MainActivity
                startActivity(intent);
            }
        });

    }
    private void updateCounterDisplay() { // for text change when increase and decrease
        numberTxt.setText(String.valueOf(++counter));

        int color;
        if (counter < 0) {
            color = android.graphics.Color.rgb(255, 0, 0); // red if negative
        } else {
            int shade = (counter >= 100) ? 100 : Math.max(255 - (counter * 2), 100); // 100 is the max shade
            color = android.graphics.Color.rgb(0, shade, 0);
        }

        numberTxt.setTextColor(color);
    }
}