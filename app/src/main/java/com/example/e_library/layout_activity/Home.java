package com.example.e_library.layout_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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

    SearchView searchView;

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

        searchView=findViewById(R.id.in_search);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<BooksModel> itemFilter = new ArrayList<>();
                for(BooksModel model : data){
                    String nama = model.getTitle().toLowerCase();
                    if(nama.contains(newText)){
                        itemFilter.add(model);
                    }
                }
                adapterHome.setFilter(itemFilter);
                return true;
            }
        });


    }


    }
