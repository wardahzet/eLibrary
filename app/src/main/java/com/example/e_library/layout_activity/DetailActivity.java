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

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_back, btn_borrow, btn_wishlist;
    ImageView ic_cover;
    TextView txt_bookname, txt_author, txt_synopsis, txt_bookparagraph;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        btn_back = findViewById(R.id.btn_back);
        btn_borrow = findViewById(R.id.btn_borrow);
        btn_wishlist = findViewById(R.id.btn_wishlist);
        ic_cover = findViewById(R.id.ic_cover);
        txt_bookname = findViewById(R.id.txt_bookname);
        txt_author = findViewById(R.id.txt_author);
        txt_synopsis = findViewById(R.id.txt_synopsis);
        txt_bookparagraph = findViewById(R.id.txt_bookparagraph);
    }

    @Override
    public void onClick(View view){
        if (view.getId() == R.id.btn_back){
            startActivity(new Intent(DetailActivity.this, Home.class));
        } else if (view.getId() == R.id.btn_borrow) {
            startActivity(new Intent(DetailActivity.this, CheckoutActivity.class));
        } else if (view.getId() == R.id.btn_wishlist) {
            //
        }
    }
}