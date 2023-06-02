package com.example.e_library.layout_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e_library.R;

public class OnBoarding extends AppCompatActivity {
    private Button btn_login , btn_signup;
    private TextView txt_greeting , txt_welcome , txt_description;
    private ImageView ic_OnBoarding;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);
        txt_greeting = findViewById(R.id.txt_greeting);
        txt_welcome = findViewById(R.id.txt_welcome);
        txt_description = findViewById(R.id.txt_description);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent int_login = new Intent(OnBoarding.this,LoginPage.class);
                startActivity(int_login);
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent int_register = new Intent(OnBoarding.this, RegisterPage.class);
                startActivity(int_register);
            }
        });

    }
}