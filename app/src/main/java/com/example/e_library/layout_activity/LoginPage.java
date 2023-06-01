package com.example.e_library.layout_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.e_library.R;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {
   private EditText in_username,in_password;
   private Button btn_back, btn_login, btn_withGoogle;
   private TextView txt_register;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_page);

        in_username = findViewById(R.id.in_username);
        in_password = findViewById(R.id.in_password);
        btn_back = findViewById(R.id.btn_back);
        btn_login = findViewById(R.id.btn_login);
        btn_withGoogle = findViewById(R.id.btn_with_google);
        txt_register = findViewById(R.id.txt_register);

        btn_back.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_withGoogle.setOnClickListener(this);
        txt_register.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_back) {
            startActivity(new Intent(LoginPage.this, OnBoarding.class));
        } else if(view.getId() == R.id.btn_login) {
            //
        }else if(view.getId() == R.id.btn_with_google) {
            //
        }else {
           startActivity(new Intent(LoginPage.this, RegisterPage.class));
        }
    }


}