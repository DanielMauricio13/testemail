package com.example.cymarket;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.cymarket.R;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;  // define username edittext variable
    private EditText passwordEditText;  // define password edittext variable
    private Button loginButton;         // define login button variable
    private Button signupButton;        // define signup button variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);            // link to Login activity XML

        /* initialize UI elements */
        usernameEditText = findViewById(R.id.login_username_edt);
        passwordEditText = findViewById(R.id.login_password_edt);
        loginButton = findViewById(R.id.login_login_btn);    // link to login button in the Login activity XML
        signupButton = findViewById(R.id.login_signup_btn);  // link to signup button in the Login activity XML

        /* click listener on login button pressed */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* grab strings from user inputs */
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                checkUserExists(username, new SignupActivity.VolleyCallback() {
                    @Override
                    public void onSuccess() {
                        // Expand on this part when backend uncomments the get mapping for username/pass
                        checkUserCredentials(username, password);







                        // Username does exist → proceed with login identification
                        Toast.makeText(getApplicationContext(), "Logging In", Toast.LENGTH_LONG).show();

                        // Go to main screen after login
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure() {
                        // Username isn't in the system/credentials wrong, cannot login
                        Toast.makeText(getApplicationContext(), "Username Not Recognized", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        /* click listener on signup button pressed */
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* when signup button is pressed, use intent to switch to Signup Activity */
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);  // go to SignupActivity
            }
        });
    }

    private void checkUserCredentials(String username, String password) {
        // "/loginEmail/{email}/{password}/"
        String url = "http://coms-3090-056.class.las.iastate.edu:8080/users/us/" + username + "/" + password;






    }

    private void checkUserExists(String username, SignupActivity.VolleyCallback callback) {
        String url = "http://coms-3090-056.class.las.iastate.edu:8080/users/u/" + username;

        // Had to update to a string request, since currently the backend code sends NULL if user isn't found instead of a 404 error code...
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    // response might be "null" (literally the string "null")
                    if (response == null || response.equals("null") || response.isEmpty()) {
                        // user NOT found → safe to signup
                        callback.onFailure();
                    } else {
                        // user exists
                        Toast.makeText(getApplicationContext(),
                                "Account Already Created Under This Username!",
                                Toast.LENGTH_SHORT).show();
                        callback.onSuccess();
                    }
                },
                error -> {
                    Toast.makeText(getApplicationContext(),
                            "Failed to check username: " + error.toString(),
                            Toast.LENGTH_SHORT).show();
                    callback.onFailure();
                }
        );

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}