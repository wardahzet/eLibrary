package com.example.e_library.layout_activity.wishlist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_library.R;
import com.example.e_library.layout_activity.Home;
import com.example.e_library.layout_activity.profile.EditProfile;
import com.example.e_library.layout_activity.rent.CheckoutActivity;
import com.example.e_library.model.Book;
import com.example.e_library.model.Rent;
import com.example.e_library.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WishlistActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    WishlistAdapter _wishlistAdapter;
    List<Book> wishList;
    List<Book> rents;
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

        btn_back.setOnClickListener(v -> startActivity(new Intent(WishlistActivity.this, Home.class)));
        btn_checkout.setOnClickListener(v -> checkout());

        recyclerView = findViewById(R.id.rv_wishlist);
        RecyclerView.LayoutManager wishLayout = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(wishLayout);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("user").child(mAuth.getUid());

        getWishlist();
    }
    private void checkout() {
        validateUser();
        rents = _wishlistAdapter.getWishlistList();
        Intent intent = new Intent(WishlistActivity.this,CheckoutActivity.class);
        intent.putExtra("books", (ArrayList<Book>) rents);

        startActivity(intent);
        finish();
    }

    public void getWishlist(){
        wishList = new ArrayList<>();
        databaseReference.child("wishlist")
                .addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
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
                        Toast.makeText(WishlistActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    public void validateUser(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user.getStudentId().equals("") ||user.getPhoneNumber().equals("")) {
                    Toast.makeText(WishlistActivity.this, "Profile Belum Lengkap",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(WishlistActivity.this, EditProfile.class));
                    finish();
                } else {
                    storeRent();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WishlistActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void storeRent(){
        List<Book> rentBook = (_wishlistAdapter).getWishlistList();
        for (Book book : rentBook) {
            databaseReference.child("user").child(mAuth.getUid())
                    .child("rent")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    Rent rent = snapshot1.getValue(Rent.class);
                                    if ((rent.getStatus().equals("Dipesan") || rent.getStatus().equals("Dipinjam"))
                                            && Objects.equals(rent.getBook().getIsbn(), book.getIsbn())) {
                                        Toast.makeText(WishlistActivity.this
                                                ,"Anda sudah meminjam buku" + book.getKey(), Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(getIntent());
                                    }

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(WishlistActivity.this
                                    ,"error" + book.getKey(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}