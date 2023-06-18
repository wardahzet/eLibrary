package com.example.e_library.layout_activity.history;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_library.R;
import com.example.e_library.model.Rent;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ViewHolder> {
    List<Rent> rents;
    FirebaseStorage storage;
    public AdapterHistory(List<Rent> data){
        this.rents = data;

        storage = FirebaseStorage.getInstance();
    }
    @NonNull
    @Override
    public AdapterHistory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_status_rent,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistory.ViewHolder holder, int position) {
        Rent rent = rents.get(position);
        Log.e("fgdghjm", rent.getBook().getTitle());

        holder.booktitle.setText(rent.getBook().getTitle());
        holder.bookauthor.setText(rent.getBook().getAuthor());
        StorageReference reference = storage.getReference("books_cover/" + rent.getBook().getCover());
        try {
            File img = File.createTempFile("tempImage",rent.getBook().getCover().endsWith("jpg") ? "jpg" : "png");
            reference.getFile(img).addOnSuccessListener(taskSnapshot -> {
                Bitmap bitmap = BitmapFactory.decodeFile(img.getAbsolutePath());
                holder.bookcover.setImageBitmap(bitmap);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return rents.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView booktitle, bookauthor;
        ImageView bookcover ,bookstatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookauthor = itemView.findViewById(R.id.txt_bookauthor);
            booktitle = itemView.findViewById(R.id.txt_booktitle);
            bookcover = itemView.findViewById(R.id.ic_bookcover);
            bookstatus = itemView.findViewById(R.id.ic_bookstatus);
        }

    }


}
