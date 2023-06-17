package com.example.e_library.layout_activity.book_detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.e_library.R;
import com.example.e_library.layout_activity.Home;
import com.example.e_library.layout_activity.profile.EditProfile;
import com.example.e_library.layout_activity.rent.CheckoutActivity;
import com.example.e_library.layout_activity.wishlist.WishlistActivity;
import com.example.e_library.model.Book;
import com.example.e_library.model.Rent;
import com.example.e_library.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btn_back;
    AppCompatButton btn_borrow, btn_wishlist;
    ImageView ic_cover;
    TextView txt_bookname, txt_author, txt_synopsis, txt_bookparagraph;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private Book book;
    private FirebaseStorage storage;

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
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("user").child(mAuth.getUid());
        storage = FirebaseStorage.getInstance();

        book = (Book) getIntent().getSerializableExtra("book");
        txt_bookname.setText(book.getTitle());
        txt_bookparagraph.setText(book.getAuthor());
        txt_author.setText(book.getAuthor());
        txt_bookparagraph.setText(book.getSynopsis());
        setImage();

        btn_borrow.setOnClickListener(this);
        btn_wishlist.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

    private void setImage() {
        StorageReference reference = storage.getReference("books_cover/" + book.getCover());
        try {
            File img = File.createTempFile("tempImage",book.getCover().endsWith("jpg") ? "jpg" : "png");
            reference.getFile(img).addOnSuccessListener(taskSnapshot -> {
                Bitmap bitmap = BitmapFactory.decodeFile(img.getAbsolutePath());
                ic_cover.setImageBitmap(bitmap);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view){
        if (view.getId() == R.id.btn_back){
            startActivity(new Intent(DetailActivity.this, Home.class));
            finish();
        } else if (view.getId() == R.id.btn_borrow) {
            checkUser();
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
                                        Toast.makeText(DetailActivity.this
                                                ,"Anda sudah meminjam buku" + book.getKey(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(DetailActivity.this
                                    ,"error" + book.getKey(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else if (view.getId() == R.id.btn_wishlist) {
            storeWishlist();
        }
    }

    private void storeWishlist() {
        databaseReference.child("wishlist").child(book.getIsbn()).setValue(book)
                .addOnSuccessListener(this, unused ->
                        Toast.makeText(DetailActivity.this, "Berhasil menambahkan ke Wishlist",
                        Toast.LENGTH_SHORT).show())
                .addOnFailureListener(this, e ->
                        Toast.makeText(DetailActivity.this, "Error",
                                Toast.LENGTH_SHORT).show());
    }

    public void checkUser(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user == null || user.getStudentId() == null) {
                    Toast.makeText(DetailActivity.this, "Profile Belum Lengkap",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DetailActivity.this, EditProfile.class));
                    finish();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}