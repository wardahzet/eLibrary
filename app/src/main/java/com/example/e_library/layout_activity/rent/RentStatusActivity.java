package com.example.e_library.layout_activity.rent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.e_library.R;
import com.example.e_library.layout_activity.Home;
import com.example.e_library.layout_activity.history.HistoryActivity;
import com.example.e_library.model.Book;
import com.example.e_library.model.Rent;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RentStatusActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn_back, btn_finish;
    TextView txt_rent_date_fill, txt_return_date_fill;
    List<Rent> rents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_status);
        btn_back = findViewById(R.id.btn_back);
        btn_finish = findViewById(R.id.btn_finish);

        LocalDate rentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String formattedRentDate = rentDate.format(formatter);
        txt_rent_date_fill.setText(formattedRentDate);
    }

    @Override
    public void onClick(View view){
        if(view.getId() == R.id.btn_back){
            startActivity(new Intent(RentStatusActivity.this, CheckoutActivity.class));
        } else if (view.getId() == R.id.btn_finish) {
            startActivity(new Intent(RentStatusActivity.this, HistoryActivity.class));
        }
    }
}