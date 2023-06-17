package com.example.e_library.layout_activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_library.R;
import com.example.e_library.layout_activity.book_detail.DetailActivity;
import com.example.e_library.model.Book;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ViewHolder> {
    ArrayList<Book> bookList;
    Activity activity;
    FirebaseStorage storage;
    AdapterHome(ArrayList<Book> data, Activity activity){
        this.bookList = data;
        this.activity = activity;
        storage = FirebaseStorage.getInstance();
    }
    @NonNull
    @Override

    public AdapterHome.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_home,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHome.ViewHolder holder, int position) {
        final Book book = bookList.get(position);
        holder.booktitle.setText(bookList.get(position).getTitle());
        holder.bookauthor.setText(bookList.get(position).getAuthor());

        StorageReference reference = storage.getReference("books_cover/" + book.getCover());

        try {
            File img = File.createTempFile("tempImage",book.getCover().endsWith("jpg") ? "jpg" : "png");
            reference.getFile(img).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(img.getAbsolutePath());
                    holder.bookcover.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.cardBook.setOnClickListener(v ->{
            Intent intent = new Intent(activity, DetailActivity.class);
            intent.putExtra("book", book);
            activity.startActivity(intent);
        });

    }
    @Override
    public int getItemCount() {
        return bookList.size();
    }
    @SuppressLint("NotifyDataSetChanged")
    void setFilter(ArrayList<Book> filterModel){
        bookList = new ArrayList<>();
        bookList.addAll(filterModel);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView booktitle , bookauthor;
        ImageView bookcover;
        CardView cardBook;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            booktitle = itemView.findViewById(R.id.txt_booktitle);
            bookauthor = itemView.findViewById(R.id.txt_bookautor);
            bookcover = itemView.findViewById(R.id.ic_bookcover);
            cardBook = itemView.findViewById(R.id.card_book);
        }
    }
}
