package com.example.e_library.layout_activity.history;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_library.R;
import com.example.e_library.model.Rent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterHistory adapterHistory;
    List<Rent> rents;
    RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_rent);

        recyclerView = findViewById(R.id.recyler_view_history);
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.child("user").child(mAuth.getUid());

        rents = new ArrayList<>();
        getRent();

    }

    public void getRent(){
        databaseReference.child("user").child(mAuth.getUid()).child("rent")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("sdjgggjv", String.valueOf(snapshot.getChildrenCount()));
                        for(DataSnapshot item : snapshot.getChildren()){
                            Rent rent = item.getValue(Rent.class);
                            rents.add(rent);
                        }
                        adapterHistory = new AdapterHistory(rents);
                        recyclerView.setAdapter(adapterHistory);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(HistoryActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}