package com.example.e_library.layout_activity.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.e_library.R;
import com.example.e_library.layout_activity.Home;
import com.example.e_library.layout_activity.auth.OnBoarding;
import com.example.e_library.layout_activity.history.HistoryActivity;
import com.example.e_library.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private CircleImageView ic_profileImg;
    private TextView txt_name1,txt_studentID1,txt_editProfile,
            txt_name2, txt_studentID2,
            txt_email,txt_username;
    Button btn_back,btn_history,btn_logout;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private User user;
    private FirebaseStorage storageReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile);

        ic_profileImg = findViewById(R.id.ic_profile);
        txt_name1 =findViewById(R.id.txt_Name1);
        txt_studentID1 =findViewById(R.id.txt_studentID1);
        txt_editProfile =findViewById(R.id.txt_editProfile);

        txt_name2 =findViewById(R.id.txt_Name2);
        txt_studentID2 =findViewById(R.id.txt_studentID2);
        txt_email =findViewById(R.id.txt_Email);
        btn_back = findViewById(R.id.btn_back);
        txt_username = findViewById(R.id.txt_Username);
        btn_history = findViewById(R.id.btn_history);
        btn_logout = findViewById(R.id.btn_logout);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance();

        databaseReference.child("user").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    user = snapshot.getValue(User.class);
                    txt_name1.setText(user.getName());
                    txt_name2.setText(user.getName());
                    txt_studentID1.setText(user.getStudentId());
                    txt_studentID2.setText(user.getStudentId());
                    txt_email.setText(user.getEmail());
                    txt_username.setText(user.getPhoneNumber());
                    if(!user.getPhoto().isEmpty()) setImage(user.getPhoto());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_back.setOnClickListener(this);
        txt_editProfile.setOnClickListener(this);
        btn_history.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
    }

    private void setImage(String photo) {
        StorageReference reference = storageReference.getReference("users_profile/" + photo);

        try {
            File img = File.createTempFile("tempImage",photo.endsWith("jpg") ? "jpg" : "");
            reference.getFile(img).addOnSuccessListener(taskSnapshot -> {
                Bitmap bitmap = BitmapFactory.decodeFile(img.getAbsolutePath());
                ic_profileImg.setImageBitmap(bitmap);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
       if(view.getId() == R.id.txt_editProfile) {
           startActivity(new Intent(ProfileActivity.this, EditProfile.class));
       }
       else if (view.getId() == R.id.btn_back) {
           finish();
       }
       else if (view.getId() == R.id.btn_history) {
           startActivity(new Intent(ProfileActivity.this, HistoryActivity.class));
       }
       else if (view.getId() == R.id.btn_logout){
           mAuth.signOut();
           Intent intent = new Intent(ProfileActivity.this, OnBoarding.class);
           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
           startActivity(intent);
        }
    }
}