package com.example.e_library.layout_activity.profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.e_library.R;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.UUID;

public class CropActivity extends AppCompatActivity {
    String sourceUrl, destinationUri;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        Intent intent = getIntent();
        if(intent.getExtras() != null) {
            sourceUrl = intent.getStringExtra("SendImageData");
            uri = Uri.parse(sourceUrl);
        }

        destinationUri = new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();
        UCrop.Options options = new UCrop.Options();
        UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationUri)))
                .withOptions(options)
                .withAspectRatio(1, 1)
                .start(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            final Uri resultUri = UCrop.getOutput(data);
            Intent intent = new Intent();
            intent.putExtra("CROP", resultUri+"");
            setResult(101,intent);
            finish();
            // Mengunggah gambar crop ke Firebase
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            Toast.makeText(CropActivity.this,"Error",Toast.LENGTH_SHORT).show();
            // Handle error saat melakukan crop
        }
    }

}