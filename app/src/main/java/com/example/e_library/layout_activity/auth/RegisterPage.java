package com.example.e_library.layout_activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_library.R;
import com.example.e_library.layout_activity.Home;
import com.example.e_library.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterPage extends AppCompatActivity implements View.OnClickListener {

    private EditText in_username, in_email, in_password, in_confPass;
    private Button btn_back,btn_signUp;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register_page);

        in_username = findViewById(R.id.in_phoneNumber);
        in_email = findViewById(R.id.in_email);
        in_password = findViewById(R.id.in_password);
        in_confPass = findViewById(R.id.in_confPassword);
        btn_back = findViewById(R.id.btn_back);
        btn_signUp = findViewById(R.id.btn_daftar);

        btn_back.setOnClickListener(this);
        btn_signUp.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("user");
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onClick(View view) {
       if (view.getId() == R.id.btn_back) {
           finish();
       } else {
           signUp(in_email.getText().toString(), in_password.getText().toString());
       }
    }

    public void signUp(String email,String password){
        if (validateForm()){
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    User userNew = new User(in_username.getText().toString(), in_email.getText().toString());
                    databaseReference.child(user.getUid()).setValue(userNew);
                    updateUI(user);
                } else {
                    Toast.makeText(RegisterPage.this,
                            Objects.requireNonNull(task.getException()).toString(),
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            });
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(in_username.getText().toString())) {
            in_username.setError("Required");
            result = false;
        } else {
            in_username.setError(null);
        }
        if (TextUtils.isEmpty(in_password.getText().toString())) {
            in_password.setError("Required");
            result = false;
        } else {
            in_password.setError(null);
        }
        if (!TextUtils.equals(in_password.getText().toString(), in_confPass.getText().toString())) {
            in_password.setError("password isn't same");
            in_confPass.setError("password isn't same");
            result = false;
        } else {
            in_password.setError(null);
            in_confPass.setError(null);
        }
        return !result;
    }

    public void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(RegisterPage.this, Home.class);
            startActivity(intent);
        }
    }
}