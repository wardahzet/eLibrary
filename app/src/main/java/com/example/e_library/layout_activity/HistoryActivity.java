package com.example.e_library.layout_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.e_library.R;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterHistory adapterHistory;
    ArrayList<HistoryModel> data;

    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_rent);

        recyclerView = findViewById(R.id.recyler_view_history);
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        data = new ArrayList<>();
        for (int i = 0 ; i<HistoryItem.book_title.length;i++){
            data.add(new HistoryModel(
                    HistoryItem.book_title[i],
                    HistoryItem.book_author[i],
                    HistoryItem.book_genre[i],
                    HistoryItem.book_cover[i],
                    HistoryItem.book_status[i]
            ));
        }
        adapterHistory = new AdapterHistory(data);
        recyclerView.setAdapter(adapterHistory);
    }
}