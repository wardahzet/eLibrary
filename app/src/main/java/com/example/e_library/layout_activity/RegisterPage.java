package com.example.e_library.layout_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.e_library.R;

public class RegisterPage extends AppCompatActivity implements View.OnClickListener {

    private EditText in_username, in_email, in_password, in_confPass;
    private Button btn_back,btn_signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register_page);

        in_username = findViewById(R.id.in_username);
        in_email = findViewById(R.id.in_email);
        in_password = findViewById(R.id.in_password);
        in_confPass = findViewById(R.id.in_confPassword);
        btn_back = findViewById(R.id.btn_back);
        btn_signUp = findViewById(R.id.btn_daftar);

        btn_back.setOnClickListener(this);
        btn_signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
       if(view.getId() == R.id.btn_back){
           startActivity(new Intent(RegisterPage.this, OnBoarding.class));
       }else {
        //
       }
    }
}