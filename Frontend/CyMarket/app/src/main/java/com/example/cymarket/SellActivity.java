package com.example.cymarket;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;

/**
 * TODO - Add image support
 */
public class SellActivity extends AppCompatActivity {

    private TextView messageText;   // define messagetext variable

    private Button backButton;     // define backbutton variable

    private EditText itemNameEditText;    // define itemnameedittext variable

    private EditText priceEditText;     // define priceedittext variable
    private EditText descriptionEditText;    // define descriptionedittext variable
    private EditText quantityEditText;      // define quantityedittext variable
    private Button submitButton;           // define submitbutton variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);     // sell activity xml

        // link to xml
        messageText = findViewById(R.id.sell_msg_txt);
        backButton = findViewById(R.id.sell_back_btn);
        itemNameEditText = findViewById(R.id.sell_item_name_edt);
        priceEditText = findViewById(R.id.sell_price_edt);
        descriptionEditText = findViewById(R.id.sell_description_edt);
        quantityEditText = findViewById(R.id.sell_quantity_edt);
        submitButton = findViewById(R.id.sell_submit_btn);

        // submit button, sends to post
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = itemNameEditText.getText().toString().trim();
                String price = priceEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();
                String quantity = quantityEditText.getText().toString().trim();

                if (itemName.isEmpty() || price.isEmpty() || description.isEmpty() || quantity.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Field is empty", Toast.LENGTH_SHORT).show();
                } else {
                    postItemForSale(itemName, price, description, quantity);
                }
            }
        });

        // establish listener for back button functionality
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // Volley request
    private void postItemForSale(String itemName, String price, String description, String quantity) {
        String url = "http://coms-3090-056.class.las.iastate.edu:8080/items";

        // build the json payload using information entered by user
        JSONObject json = new JSONObject();
        try {
            json.put("name", itemName);
            json.put("price", Double.parseDouble(price));
            json.put("description", description);
            json.put("quantity", Integer.parseInt(quantity));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "Error creating request: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // send the request
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                json,
                response -> {  // if successful, move refresh prompts
                    Toast.makeText(getApplicationContext(), "Item listed!", Toast.LENGTH_SHORT).show();
                    itemNameEditText.setText("");
                    priceEditText.setText("");
                    descriptionEditText.setText("");
                    quantityEditText.setText("");
                },
                error -> Toast.makeText(getApplicationContext(),
                        "Listing failed: " + error.toString(), Toast.LENGTH_SHORT).show()
        );

        // add request to queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
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

