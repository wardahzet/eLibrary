package com.example.e_library.layout_activity.auth;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_library.R;
import com.example.e_library.layout_activity.Home;
import com.example.e_library.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {
   private EditText in_username,in_password;
   private Button btn_back, btn_login, btn_withGoogle;
   private TextView txt_register;
   private FirebaseAuth mAuth;
   private GoogleSignInClient mGoogleSignInClient;
    private FirebaseDatabase firebaseDatabase;
   private DatabaseReference databaseReference;
   private static final int RC_SIGN_IN = 2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_page);

        mAuth = FirebaseAuth.getInstance();

        in_username = findViewById(R.id.in_phoneNumber);
        in_password = findViewById(R.id.in_password);
        btn_back = findViewById(R.id.btn_back);
        btn_login = findViewById(R.id.btn_login);
        btn_withGoogle = findViewById(R.id.btn_with_google);
        txt_register = findViewById(R.id.txt_register);

        btn_back.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_withGoogle.setOnClickListener(this);
        txt_register.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
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
        if(view.getId() == R.id.btn_back) {
            startActivity(new Intent(LoginPage.this, OnBoarding.class));
        } else if(view.getId() == R.id.btn_login) {
            login(in_username.getText().toString(),in_password.getText().toString());
        }else if(view.getId() == R.id.btn_with_google) {
            loginWithGoogle();
        } else {
           startActivity(new Intent(LoginPage.this, RegisterPage.class));
        }
    }

    public void login(String email,String password){
        if (validateForm()){
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        assert user != null;
                        Toast.makeText(LoginPage.this, user.toString(), Toast.LENGTH_SHORT).show();
                        updateUI(user);
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginPage.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
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
        return !result;
    }

    private void loginWithGoogle() {
        Intent i = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(i, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(this, authResult -> {
                    FirebaseUser user = mAuth.getCurrentUser();
                    databaseReference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.exists()) {
                                User userNew = new User(account.getDisplayName(), account.getEmail());
                                databaseReference.child(user.getUid()).setValue(userNew);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    updateUI(user);
                })
                .addOnFailureListener(this, e ->
                        Toast.makeText(LoginPage.this, "Authentication failed.", Toast.LENGTH_SHORT).show());
        updateUI(null);
    }

    public void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(LoginPage.this, Home.class);
            startActivity(intent);
        } else {
            Toast.makeText(LoginPage.this,"Log In First", Toast.LENGTH_SHORT).show();
        }
    }
}