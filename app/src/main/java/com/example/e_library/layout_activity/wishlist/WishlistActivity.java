package com.example.e_library.layout_activity.wishlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.e_library.R;
import com.example.e_library.layout_activity.Home;
import com.example.e_library.layout_activity.profile.EditProfile;
import com.example.e_library.layout_activity.rent.CheckoutActivity;
import com.example.e_library.model.Book;
import com.example.e_library.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WishlistActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    WishlistAdapter _wishlistAdapter;
    List<Book> wishList;
    Button btn_back, btn_checkout;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_wishlist);

        btn_back = findViewById(R.id.btn_back);
        btn_checkout = findViewById(R.id.btn_checkout);

        btn_back.setOnClickListener(this);
        btn_checkout.setOnClickListener(this);

        recyclerView = findViewById(R.id.rv_wishlist);
        RecyclerView.LayoutManager wishLayout = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(wishLayout);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        wishList = new ArrayList<>();
        databaseReference.child("user").child(mAuth.getUid()).child("wishlist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item : snapshot.getChildren()){
                    Book book = item.getValue(Book.class);
                    wishList.add(book);
                    _wishlistAdapter = new WishlistAdapter(WishlistActivity.this, wishList);
                    recyclerView.setAdapter(_wishlistAdapter);
                    _wishlistAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_back) {
            startActivity(new Intent(WishlistActivity.this, Home.class));
        } else if (view.getId() == R.id.btn_checkout){
            databaseReference.child("user").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = null;
                    for(DataSnapshot item : snapshot.getChildren()){
                        user = item.getValue(User.class);
                    }

                    if (user == null || user.getStudentId() == null) {
                        Toast.makeText(WishlistActivity.this, "Profile Belum Lengkap",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(WishlistActivity.this, EditProfile.class));
                        finish();
                    } else {
                        List<Book> rentBook = ((WishlistAdapter) _wishlistAdapter).getWishlistList();
                        Intent intent = new Intent(WishlistActivity.this,CheckoutActivity.class);
                        intent.putExtra("listBook", (Serializable) rentBook);
                        startActivity(intent);
                        finish();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}