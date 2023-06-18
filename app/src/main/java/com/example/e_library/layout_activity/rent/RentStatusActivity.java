package com.example.e_library.layout_activity.rent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.e_library.R;
import com.example.e_library.layout_activity.history.HistoryActivity;

public class RentStatusActivity extends AppCompatActivity{
    ImageButton btn_back;
    AppCompatButton btn_finish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_status);
        btn_back = findViewById(R.id.btn_back);
        btn_finish = findViewById(R.id.btn_finish);

        btn_back.setOnClickListener(v -> startActivity(new Intent(RentStatusActivity.this, CheckoutActivity.class)));
        btn_finish.setOnClickListener(v -> startActivity(new Intent(RentStatusActivity.this, HistoryActivity.class)));
    }


}