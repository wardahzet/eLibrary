package com.example.e_library.layout_activity.book_detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_library.R;
import com.example.e_library.layout_activity.Home;
import com.example.e_library.layout_activity.profile.EditProfile;
import com.example.e_library.layout_activity.rent.CheckoutActivity;
import com.example.e_library.layout_activity.wishlist.WishlistActivity;
import com.example.e_library.layout_activity.wishlist.WishlistAdapter;
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

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btn_back;
    AppCompatButton btn_borrow, btn_wishlist;
    ImageView ic_cover;
    TextView txt_bookname, txt_author, txt_synopsis, txt_bookparagraph;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private Book book;

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
        databaseReference = firebaseDatabase.getReference();

        book = (Book) getIntent().getSerializableExtra("book");
        txt_bookname.setText(book.getTitle());
        txt_bookparagraph.setText(book.getAuthor());
        txt_author.setText(book.getAuthor());
        txt_bookparagraph.setText(book.getSynopsis());

        btn_borrow.setOnClickListener(this);
        btn_wishlist.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if (view.getId() == R.id.btn_back){
            startActivity(new Intent(DetailActivity.this, Home.class));
            finish();
        } else if (view.getId() == R.id.btn_borrow) {
            databaseReference.child("user").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = null;
                    for(DataSnapshot item : snapshot.getChildren()){
                        user = item.getValue(User.class);
                    }
                    if (user == null || user.getStudentId() == null) {
                        Toast.makeText(DetailActivity.this, "Profile Belum Lengkap",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DetailActivity.this, EditProfile.class));
                        finish();
                    } else {
                        List<Book> books = new ArrayList<>();
                        books.add(book);
                        Intent intent = new Intent(DetailActivity.this, CheckoutActivity.class);
                        intent.putExtra("book", (Serializable) books);
                        startActivity(intent);
                        finish();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else if (view.getId() == R.id.btn_wishlist) {
            databaseReference.child("user").child(mAuth.getUid()).child("wishlist").child(book.getIsbn()).setValue(book)
                    .addOnSuccessListener(this, unused -> {
                            Toast.makeText(DetailActivity.this, "Berhasil menambahkan ke Wishlist",
                                    Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(this, e ->
                            Toast.makeText(DetailActivity.this, "Error",
                                    Toast.LENGTH_SHORT).show());
        }
    }
}