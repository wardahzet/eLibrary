package com.example.e_library.layout_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.e_library.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_back, btn_submit_form;
    TextView txt_co, txt_name, txt_email, txt_id, txt_phone, txt_bookname, txt_bookauthor, txt_bookid, txt_bookgenre, txt_rent_id, txt_rent_date, txt_return_date, txt_rent_id_num, txt_rent_date_fill, txt_return_date_fill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        btn_back = findViewById(R.id.btn_back);
        btn_submit_form = findViewById(R.id.btn_submit_form);
        txt_co = findViewById(R.id.txt_co);
        txt_name = findViewById(R.id.txt_name);
        txt_email = findViewById(R.id.txt_email);
        txt_id = findViewById(R.id.txt_id);
        txt_phone = findViewById(R.id.txt_phone);
        txt_bookname = findViewById(R.id.txt_bookname);
        txt_bookauthor = findViewById(R.id.txt_bookauthor);
        txt_bookid = findViewById(R.id.txt_bookid);
        txt_bookgenre = findViewById(R.id.txt_bookgenre);
        txt_rent_id = findViewById(R.id.txt_rent_id);
        txt_rent_date = findViewById(R.id.txt_rent_date);
        txt_return_date = findViewById(R.id.txt_return_date);
        txt_rent_id_num = findViewById(R.id.txt_rent_id_num);
        txt_rent_date_fill = findViewById(R.id.txt_rent_date_fill);
        txt_return_date_fill = findViewById(R.id.txt_return_date_fill);

        LocalDate rentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String formattedRentDate = rentDate.format(formatter);
        txt_rent_date_fill.setText(formattedRentDate);

        LocalDate returnDate = rentDate.plusDays(3);
        String formattedReturnDate = returnDate.format(formatter);
        txt_return_date_fill.setText(formattedReturnDate);

    }

    @Override
    public void onClick (View view){
        if(view.getId() == R.id.btn_back){
            startActivity(new Intent(CheckoutActivity.this, DetailActivity.class));
        } else if (view.getId() == R.id.btn_submit_form) {
            startActivity(new Intent(CheckoutActivity.this, RentStatusActivity.class));
        }
    }


}