package com.example.e_library.layout_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.e_library.R;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterHome adapterHome;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<BooksModel> data;

    Button btn_search;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyler_view_home);
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        data= new ArrayList<>();
        for(int i = 0; i<BooksItem.books_title.length;i++){
            data.add(new BooksModel(
                    BooksItem.books_title[i],
                    BooksItem.books_author[i],
                    BooksItem.books_cover[i]
            ));
        }
        adapterHome = new AdapterHome(data);
        recyclerView.setAdapter(adapterHome);

        btn_search = findViewById(R.id.btn_search);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent int_search=new Intent(Home.this, SearchActivity.class);
                startActivity(int_search);
            }
        });

    }
}