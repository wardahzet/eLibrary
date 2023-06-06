package com.example.e_library.layout_activity.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e_library.R;
import com.example.e_library.layout_activity.Home;
import com.google.firebase.auth.FirebaseAuth;
public class OnBoarding extends AppCompatActivity {
    private Button btn_login , btn_signup;
    private FirebaseAuth mAuth;
    private TextView txt_greeting , txt_welcome , txt_description;
    private ImageView ic_OnBoarding;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        mAuth = FirebaseAuth.getInstance();

        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);
        txt_greeting = findViewById(R.id.txt_greeting);
        txt_welcome = findViewById(R.id.txt_welcome);
        txt_description = findViewById(R.id.txt_description);

        btn_login.setOnClickListener(view -> {
            Intent int_login = new Intent(OnBoarding.this, LoginPage.class);
            startActivity(int_login);
        });

        btn_signup.setOnClickListener(view -> {
            Intent int_register = new Intent(OnBoarding.this, RegisterPage.class);
            startActivity(int_register);
        });
    }
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(OnBoarding.this, Home.class);
            startActivity(intent);
        }
    }
}