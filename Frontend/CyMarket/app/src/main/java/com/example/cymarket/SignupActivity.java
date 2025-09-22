package com.example.cymarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignupActivity extends AppCompatActivity {
    // text variables
    private EditText firstNameText;   // define email edit
    private EditText lastNameText;   // define email edit
    private EditText emailEditText;   // define email edit
    private EditText usernameEditText;  // define username edittext variable
    private EditText passwordEditText;  // define password edittext variable
    private EditText confirmEditText;   // define confirm edittext variable
    private TextView loginText;         // define login button variable
    private Button signupButton;        // define signup button variable

    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        /* initialize UI elements */
        firstNameText = findViewById(R.id.signup_first_name_edt);  // link to first name edtext in the Signup activity XML
        lastNameText = findViewById(R.id.signup_last_name_edt);  // link to last name edtext in the Signup activity XML
        usernameEditText = findViewById(R.id.signup_username_edt);  // link to username edtext in the Signup activity XML
        passwordEditText = findViewById(R.id.signup_password_edt);  // link to password edtext in the Signup activity XML
        confirmEditText = findViewById(R.id.signup_confirm_edt);    // link to confirm edtext in the Signup activity XML
        loginText = findViewById(R.id.signup_login_text);  // link to login button in the Signup activity XML
        signupButton = findViewById(R.id.signup_signup_btn);  // link to signup button in the Signup activity XML
        emailEditText = findViewById(R.id.signup_email_edt); // link to email text in the Signup activity XML

        /* click listener on login button pressed */
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* when login button is pressed, use intent to switch to Login Activity */
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        /* click listener on signup button pressed */
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* grab strings from user inputs */
                String firstName = firstNameText.getText().toString();
                String lastName = lastNameText.getText().toString();
                String email = emailEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmEditText.getText().toString();

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Passwords Don't Match", Toast.LENGTH_LONG).show();
                } else if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Fill Out All Fields", Toast.LENGTH_LONG).show();
                } else { // Now it will sign up the user if everything passes
                    Toast.makeText(getApplicationContext(), "Signing Up", Toast.LENGTH_LONG).show();
                    signUpUser(firstName, lastName, email, username, password);

                    // Set the screen to the login screen when the account is created:
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void signUpUser(String firstName, String lastName, String email, String username, String password) {
        JSONObject json = new JSONObject();
        try {
        json.put("firstName", username);   // using username as firstName (adjust as needed)
        json.put("lastName", "");          // leave blank if not collected
        json.put("email", email);
        json.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}