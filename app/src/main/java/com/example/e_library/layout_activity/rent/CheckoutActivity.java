package com.example.e_library.layout_activity.rent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_library.R;
import com.example.e_library.model.Book;
import com.example.e_library.model.Rent;
import com.example.e_library.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
    ImageButton btn_back;
    AppCompatButton btn_submit_form;
    List<Book> books;
    TextView txt_name;
    TextView txt_email;
    TextView txt_id;
    TextView txt_phone;
    RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        btn_back = findViewById(R.id.btn_back);
        btn_submit_form = findViewById(R.id.btn_submit_form);
        txt_name = findViewById(R.id.in_name);
        txt_email = findViewById(R.id.in_email);
        txt_id = findViewById(R.id.in_id);
        txt_phone = findViewById(R.id.in_phone);
        recyclerView = findViewById(R.id.rv_checkout);
        RecyclerView.LayoutManager wishLayout = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(wishLayout);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("user").child(mAuth.getUid());
        getUser();
        books = (ArrayList<Book>) getIntent().getSerializableExtra("books");

        CheckoutAdapter checkoutAdapter = new CheckoutAdapter(books);
        recyclerView.setAdapter(checkoutAdapter);
        checkoutAdapter.notifyDataSetChanged();
        btn_back.setOnClickListener(v -> finish());
        btn_submit_form.setOnClickListener(v -> {
            checkout();
            Intent intent = new Intent(CheckoutActivity.this, RentStatusActivity.class);
            finish();
            startActivity(intent);
        });
    }
    public void getUser(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                txt_name.setText(user.getName());
                txt_email.setText(user.getEmail());
                txt_id.setText(user.getStudentId());
                txt_phone.setText(user.getPhoneNumber());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CheckoutActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void checkout() {
        LocalDate rentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String formattedRentDate = rentDate.format(formatter);
        for (Book book : books) {
            Rent rent = new Rent(mAuth.getUid(), book, formattedRentDate);
            rent.setStatus("dipesan");
            databaseReference.child("rent").push().setValue(rent)
                    .addOnSuccessListener(this, unused ->
                            databaseReference.child("wishlist")
                            .child(book.getIsbn()).removeValue()).addOnFailureListener(this, e -> {
                        Toast.makeText(CheckoutActivity.this, "Error",
                                Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
                    });
        }
    }


}