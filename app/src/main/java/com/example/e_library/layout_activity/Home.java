package com.example.e_library.layout_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.e_library.R;
import com.example.e_library.layout_activity.profile.ProfileActivity;
import com.example.e_library.layout_activity.wishlist.WishlistActivity;
import com.example.e_library.model.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterHome adapterHome;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Book> bookList;
    SearchView searchView;
    ImageView wishlist;
    ImageView profile;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        recyclerView = findViewById(R.id.recyler_view_home);
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        profile = findViewById(R.id.ic_profile_user);
        profile.setOnClickListener(v -> {
                startActivity(new Intent(Home.this, ProfileActivity.class));
                finish();
        });

        wishlist = findViewById(R.id.ic_wishlist);
        wishlist.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, WishlistActivity.class);
            startActivity(intent);
            finish();
        });

        bookList = new ArrayList<>();
        databaseReference.child("books").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookList = new ArrayList<>();
                for(DataSnapshot item : snapshot.getChildren()){
                    Book book = item.getValue(Book.class);
                    bookList.add(book);
                }
                adapterHome = new AdapterHome(bookList,Home.this);
                recyclerView.setAdapter(adapterHome);
                searchView = findViewById(R.id.in_search);
                searchView.clearFocus();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                newText = newText.toLowerCase();
//                if(bookList.size() == 0) return false;
//                ArrayList<Book> itemFilter = new ArrayList<>();
//                try {
//                    for(Book model : bookList){
//                        String nama = model.getTitle().toLowerCase();
//                        if(nama.contains(newText)){
//                            itemFilter.add(model);
//                        }
//                    }
//                    adapterHome.setFilter(itemFilter);
//                    return true;
//                } catch (NullPointerException e) {
//                    Toast.makeText(Home.this,"Buku tidak ditemukan", Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//            }
//        });
    }
}
