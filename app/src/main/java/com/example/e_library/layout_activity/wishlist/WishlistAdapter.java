package com.example.e_library.layout_activity.wishlist;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_library.R;
import com.example.e_library.model.Book;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.MyHolder> {

    List<Book> wishlistList;
    List<Book> rentList = new ArrayList<>();
    private final Activity context;
    private final FirebaseDatabase firebaseDatabase;
    private final DatabaseReference databaseReference;
    private final FirebaseStorage storage;
    private final FirebaseAuth mAuth;
    public WishlistAdapter(Activity activity, List<Book> wishlistList) {
        this.context = activity;
        this.wishlistList = wishlistList;

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storage = FirebaseStorage.getInstance();
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_wishlist, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.MyHolder holder, int position) {
        Book book = wishlistList.get(position);

        holder.txt_judul.setText(book.getTitle());
        holder.txt_penulis.setText(book.getAuthor());
        StorageReference reference = storage.getReference("books_cover/" + book.getCover());
        try {
            File img = File.createTempFile("tempImage",book.getCover().endsWith("jpg") ? "jpg" : "png");
            reference.getFile(img).addOnSuccessListener(taskSnapshot -> {
                Bitmap bitmap = BitmapFactory.decodeFile(img.getAbsolutePath());
                holder.ic_image.setImageBitmap(bitmap);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.cb_checkBox.setChecked(false);
        holder.cb_checkBox.setTag(book);
        holder.cb_checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                rentList.add(book);
            } else {
                rentList.remove(book);
            }

        });


        holder.btn_delete.setOnClickListener(v ->
                databaseReference.child("user").child(mAuth.getUid())
                    .child("wishlist").child(book.getIsbn()).removeValue()
                    .addOnSuccessListener(context, unused -> {
                            Toast.makeText(context, "Berhasil Mengapus ke Wishlist",
                                    Toast.LENGTH_SHORT).show();
                            context.startActivity(context.getIntent());
                    })
                    .addOnFailureListener(context, e ->
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    )
        );
    }

    @Override
    public int getItemCount() {
        return wishlistList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        RoundedImageView ic_image;
        TextView txt_judul,txt_penulis,txt_genre;
        CheckBox cb_checkBox;
        Button btn_delete;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            ic_image = itemView.findViewById(R.id.ic_image);
            txt_judul = itemView.findViewById(R.id.txt_BookTitle);
            txt_penulis = itemView.findViewById(R.id.txt_author);
            txt_genre = itemView.findViewById(R.id.txt_genre);
            cb_checkBox = itemView.findViewById(R.id.cb_wishlist);
            btn_delete = itemView.findViewById(R.id.btn_delete);

        }
    }
    public List<Book> getWishlistList() {
        return rentList;
    }
}
