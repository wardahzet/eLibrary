package com.example.e_library.layout_activity.history;

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

import java.util.List;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ViewHolder> {
    List<Rent> rents;
    public AdapterHistory(List<Rent> data){
        this.rents = data;
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
