package com.example.androidexample;

import android.os.Bundle;
import android.icu.util.Calendar;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WelcomeActivity extends AppCompatActivity {

    private ConstraintLayout welcomeLayout;
    private TextView welcomeMsg;
    private TextView quoteMsg;
    private TextView dayMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcomeLayout = findViewById(R.id.welcome_layout);
        welcomeMsg = findViewById(R.id.welcome_msg);
        quoteMsg = findViewById(R.id.quote_msg);
        dayMsg = findViewById(R.id.day_msg);

        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        if (hour >= 6 && hour < 18) {
            welcomeLayout.setBackgroundResource(R.drawable.morning_gradient);
        } else {
            welcomeLayout.setBackgroundResource(R.drawable.night_gradient);
        }

        // Optional: personalize message
        String username = getIntent().getStringExtra("USERNAME");
        if (username != null) {
            welcomeMsg.setText("Welcome " + username + "!");
        }

        // quote list
        String[] quotes = {
                "“I am your father.” - Darth Vader",
                "“The greatest victory is that which requires no battle.” - Sun Tzu",
                "“You miss 100% of the shots you don't take.” - Wayne Gretzky",
                "“The unexamined life is not worth living.” - Socrates",
                "“An investment in knowledge pays the best interest.” - Benjamin Franklin",
        };

        // select random quote
        Random rand = new Random();
        String selectedQuote = quotes[rand.nextInt(quotes.length)];
        quoteMsg.setText(selectedQuote);


        // get date
        String day = new SimpleDateFormat("EEEE").format(new Date());
        dayMsg.setText("It is " + day);
    }
}
