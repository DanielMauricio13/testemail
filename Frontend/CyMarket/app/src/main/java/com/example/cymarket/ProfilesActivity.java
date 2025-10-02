package com.example.cymarket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfilesActivity extends AppCompatActivity {
    private Button homeButton;
    private Button messagesButton;
    private Button settingsButton;
    private TextView usernameText;
    private static final int PICK_IMAGE = 1;
    private ImageView profileImage;

    // private Button notificationsButton;

    // @PostMapping("/users/{username}/profile-image")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);

        homeButton = findViewById(R.id.prfls_home_page_btn);
        messagesButton = findViewById(R.id.prfls_messages_btn);
        settingsButton = findViewById(R.id.prfls_setting_btn);
        usernameText = findViewById(R.id.username_text);
//      notificationsButton = findViewById(R.id.prfls_notifs_btn);
        profileImage = findViewById(R.id.profile_image_view);

        // Set up the PFP
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String savedUri = prefs.getString("profile_image_uri", null);

        if (savedUri != null) {
            profileImage.setImageURI(Uri.parse(savedUri));
        }

        // Display username
        String username = getIntent().getStringExtra("username");
        usernameText.setText(username);

        // Button click listeners
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

//        notificationsButton.setOnClickListener(v -> {
//            Intent intent = new Intent(ProfilesActivity.this, NotificationsActivity.class);
//            startActivity(intent);
//        });

        // Open image picker on profile image click
        profileImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            profileImage.setImageURI(imageUri);
            saveUserPFP(imageUri);
        }
    }

    private void saveUserPFP(Uri imageUri) {
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("profile_image_uri", imageUri.toString());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}