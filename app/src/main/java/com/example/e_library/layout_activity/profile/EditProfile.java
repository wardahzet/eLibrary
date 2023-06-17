package com.example.e_library.layout_activity.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_library.R;
import com.example.e_library.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity{
    private CircleImageView image;
    private TextView txt_name,txt_changePhoto;
    private EditText in_name,in_studentID,in_email;
    private Button btn_save;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private User user;
    private Uri imgUri;
    private FirebaseStorage storage;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_profile);

        image = findViewById(R.id.ic_chngPhoto);
        txt_name = findViewById(R.id.txt_Name1);
        txt_changePhoto = findViewById(R.id.txt_editImg);
        in_name = findViewById(R.id.in_Name2);
        in_studentID = findViewById(R.id.in_studentID);
        in_email = findViewById(R.id.in_email);
        btn_save = findViewById(R.id.btn_save);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("user").child(mAuth.getUid());
        storage = FirebaseStorage.getInstance();

        getUser();

        txt_changePhoto.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("img/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,100);
        });
        btn_save.setOnClickListener(v -> change());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null) {
             imgUri = data.getData();
             image.setImageURI(imgUri);
        }
    }
    private void change() {
        user.setName(in_name.getText().toString());
        user.setEmail(in_email.getText().toString());
        user.setStudentId(in_studentID.getText().toString());
        user.setPhoto(uploadImage());
        databaseReference.setValue(user)
                .addOnSuccessListener(this, unused -> {
                    Toast.makeText(EditProfile.this, "Berhasil update Profile",
                            Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(this, e ->
                        Toast.makeText(EditProfile.this, "Error",
                                Toast.LENGTH_SHORT).show());
    }

    private String uploadImage() {
        if (imgUri == null) return user.getPhoto();
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(String.valueOf(imgUri));
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        StorageReference reference = FirebaseStorage.getInstance().getReference().child("users")
                .child(mAuth + "." + type);
        Task<UploadTask.TaskSnapshot> task = reference.putFile(imgUri);
        return mAuth + type;
    }

    private void getUser() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                txt_name.setText(user.getName());
                in_name.setText(user.getName());

                in_studentID.setText(user.getStudentId());
                in_email.setText(user.getEmail());
                if(!user.getPhoto().isEmpty()) setImage(user.getPhoto());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setImage(String photo) {
        StorageReference reference = storage.getReference("users_profile/" + user.getPhoto());

        try {
            File img = File.createTempFile("tempImage",user.getPhoto().endsWith("jpg") ? "jpg" : "png");
            reference.getFile(img).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(img.getAbsolutePath());
                    image.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}