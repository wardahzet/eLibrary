package com.example.e_library.layout_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e_library.R;

public class StatusPeminjaman extends AppCompatActivity {
    private ImageButton btn_back;
    private TextView txt_rentStatustitle , txt_bookTitle, txt_author, txt_bookGenre, txt_bookStatus;
    private ImageView ic_book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_peminjaman);

        btn_back =findViewById(R.id.btn_back);
        txt_rentStatustitle =findViewById(R.id.txt_rentStatustitle);
        txt_bookTitle =findViewById(R.id.txt_bookTitle);
        txt_author =findViewById(R.id.txt_author);
        txt_bookGenre =findViewById(R.id.txt_author);
        txt_bookStatus =findViewById(R.id.txt_bookStatus);
        ic_book =findViewById(R.id.ic_book);

    }

}