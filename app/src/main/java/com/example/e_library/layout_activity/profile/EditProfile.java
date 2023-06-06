package com.example.e_library.layout_activity.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_library.R;
import com.example.e_library.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {
    private CircleImageView image;
    private TextView txt_name,txt_changePhoto;
    private EditText in_name,in_studentID,in_uname,in_email,in_pwd;
    private Button btn_save;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mAuthUser;
    private User user;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_profile);

        image = findViewById(R.id.ic_chngPhoto);
        txt_name = findViewById(R.id.txt_Name1);
        txt_changePhoto = findViewById(R.id.txt_editImg);
        in_name = findViewById(R.id.in_Name2);
        in_uname = findViewById(R.id.in_username);
        in_studentID = findViewById(R.id.in_studentID);
        in_email = findViewById(R.id.in_email);
        in_pwd = findViewById(R.id.in_password);
        btn_save = findViewById(R.id.btn_save);

        mAuth = FirebaseAuth.getInstance();
        mAuthUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.child("user").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    user = item.getValue(User.class);
                }
                if (user != null) {
                    String nama = user.getName() == null ? mAuthUser.getDisplayName() : user.getName();
                    txt_name.setText(nama);
                    in_name.setText(nama);

                    in_studentID.setText(user.getStudentId());
                    in_email.setText(user.getPhoneNumber());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        txt_changePhoto.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.txt_editImg){
            Toast.makeText(EditProfile.this,"Change success",Toast.LENGTH_SHORT).show();
        } else {
            databaseReference.child("user").child(mAuth.getUid()).setValue(user)
                .addOnSuccessListener(this, unused -> {
                    Toast.makeText(EditProfile.this, "Berhasil update Profile",
                        Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(this, e ->
                    Toast.makeText(EditProfile.this, "Error",
                        Toast.LENGTH_SHORT).show());
        }
    }

}