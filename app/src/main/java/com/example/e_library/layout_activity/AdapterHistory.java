package com.example.e_library.layout_activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_library.R;

import java.util.ArrayList;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ViewHolder> {
    ArrayList<HistoryModel> dataHistory;
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView booktitle, bookauthor, bookgenre;
        ImageView bookcover ,bookstatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookauthor = itemView.findViewById(R.id.txt_bookauthor);
            booktitle = itemView.findViewById(R.id.txt_booktitle);
            bookgenre = itemView.findViewById(R.id.txt_bookgenre);
            bookcover = itemView.findViewById(R.id.ic_bookcover);
            bookstatus = itemView.findViewById(R.id.ic_bookstatus);
        }

    }
    AdapterHistory(ArrayList<HistoryModel> data){
        this.dataHistory=data;
    }
    @NonNull
    @Override
    public AdapterHistory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_status_rent,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistory.ViewHolder holder, int position) {
        TextView txt_booktitle = holder.booktitle;
        TextView txt_bookauthor = holder.bookauthor;
        TextView txt_bookgenre = holder.bookgenre;
        ImageView ic_bookcover = holder.bookcover;
        ImageView ic_bookstatus = holder.bookstatus;

        txt_booktitle.setText(dataHistory.get(position).getTitle());
        txt_bookauthor.setText(dataHistory.get(position).getAuthor());
        txt_bookgenre.setText(dataHistory.get(position).getGenre());
        ic_bookcover.setImageResource(dataHistory.get(position).getCover());
        ic_bookstatus.setImageResource(dataHistory.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return dataHistory.size();
    }


}
