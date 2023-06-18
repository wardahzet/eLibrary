package com.example.e_library.layout_activity.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_library.R;
import com.example.e_library.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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
    private ActivityResultLauncher<String> cropImage;
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
        in_email = findViewById(R.id.in_phoneNumber);
        btn_save = findViewById(R.id.btn_save);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("user").child(mAuth.getUid());
        storage = FirebaseStorage.getInstance();
        getUser();
        cropImage = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            Intent intent = new Intent(EditProfile.this, CropActivity.class);
            intent.putExtra("SendImageData", result.toString());
            startActivityForResult(intent, 100);
        });

        txt_changePhoto.setOnClickListener(v -> {
            cropImage.launch("image/*");
//            Intent intent = new Intent(Intent.ACTION_PICK);
////            intent.setType("img/*");
//            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            startActivityForResult(intent,100);
        });
        btn_save.setOnClickListener(v -> change());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 101) {
            String result = data.getStringExtra("CROP");
            imgUri = data.getData();
            if(result != null) {
                imgUri = Uri.parse(result);
            }
            Toast.makeText(EditProfile.this, String.valueOf(imgUri), Toast.LENGTH_SHORT).show();
            Log.d("TAG", String.valueOf(imgUri));
            image.setImageURI(imgUri);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 100 && data != null && data.getData() != null) {
//             imgUri = data.getData();
//             image.setImageURI(imgUri);
//        }
//    }
    private void change() {
        user.setName(in_name.getText().toString());
        user.setPhoneNumber(in_email.getText().toString());
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
        StorageReference reference = FirebaseStorage.getInstance().getReference().child("users_profile")
                .child(mAuth.getUid());
        reference.putFile(imgUri);

        return mAuth.getUid();
    }

    private void getUser() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                txt_name.setText(user.getName());
                in_name.setText(user.getName());

                in_studentID.setText(user.getStudentId());
                in_email.setText(user.getPhoneNumber());
                if(!user.getPhoto().isEmpty()) setImage(user.getPhoto());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setImage(String photo) {
        StorageReference reference = storage.getReference("users_profile/" + photo);

        try {
            File img = File.createTempFile("tempImage",photo.endsWith("jpg") ? "jpg" : "");
            reference.getFile(img).addOnSuccessListener(taskSnapshot -> {
                Bitmap bitmap = BitmapFactory.decodeFile(img.getAbsolutePath());
                image.setImageBitmap(bitmap);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}