package com.example.e_library.layout_activity.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.e_library.R;
import com.example.e_library.layout_activity.Home;
import com.example.e_library.layout_activity.auth.OnBoarding;
import com.example.e_library.layout_activity.history.HistoryActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private CircleImageView ic_profileImg;
    private TextView txt_name1,txt_studentID1,txt_editProfile, txt_status,
            txt_name2, txt_studentID2,txt_Uname,
            txt_email,txt_pwd;
    Button btn_back,btn_history,btn_logout;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile);

        ic_profileImg = findViewById(R.id.ic_profile);
        txt_name1 =findViewById(R.id.txt_Name1);
        txt_studentID1 =findViewById(R.id.txt_studentID1);
        txt_editProfile =findViewById(R.id.txt_editProfile);
        txt_status =findViewById(R.id.txt_studentStatus);
        txt_name2 =findViewById(R.id.txt_Name2);
        txt_studentID2 =findViewById(R.id.txt_studentID2);
        txt_Uname =findViewById(R.id.txt_Username);
        txt_email =findViewById(R.id.txt_Email);
        txt_pwd = findViewById(R.id.txt_Password);
        txt_pwd.setTransformationMethod(new PasswordTransformationMethod());
        btn_back = findViewById(R.id.btn_back);
        btn_history = findViewById(R.id.btn_history);
        btn_logout = findViewById(R.id.btn_logout);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        btn_back.setOnClickListener(this);
        txt_editProfile.setOnClickListener(this);
        btn_history.setOnClickListener(this);
        btn_logout.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
       if(view.getId()== R.id.txt_editProfile) {
           startActivity(new Intent(ProfileActivity.this, EditProfile.class));
       }
       else if (view.getId()==R.id.btn_back) {
           startActivity(new Intent(ProfileActivity.this, Home.class ));
       }
       else if (view.getId()==R.id.btn_history) {
           startActivity(new Intent(ProfileActivity.this, HistoryActivity.class));
       }
       else if (view.getId()==R.id.btn_logout){
           mAuth.signOut();
           Intent intent = new Intent(ProfileActivity.this, OnBoarding.class);
           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
           startActivity(intent);
           startActivity(new Intent(ProfileActivity.this, OnBoarding.class));
        }
    }
}