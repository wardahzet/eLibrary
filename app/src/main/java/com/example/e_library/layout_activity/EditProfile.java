package com.example.e_library.layout_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_library.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {
    private CircleImageView image;
    private TextView txt_name,txt_changePhoto;
    private EditText in_name,in_studentID,in_uname,in_email,in_pwd;
    private Button btn_save;

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

        txt_changePhoto.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.txt_editImg){
            Toast.makeText(EditProfile.this,"Change success",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(EditProfile.this,"Add data",Toast.LENGTH_SHORT).show();

            startActivity(new Intent(EditProfile.this,Profile.class));
        }
    }

}