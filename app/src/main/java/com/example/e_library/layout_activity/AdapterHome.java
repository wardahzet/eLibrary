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

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ViewHolder> {
    ArrayList<BooksModel> dataBooks;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView booktitle , bookauthor;
        ImageView bookcover;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            booktitle=itemView.findViewById(R.id.txt_booktitle);
            bookauthor=itemView.findViewById(R.id.txt_bookautor);
            bookcover=itemView.findViewById(R.id.ic_bookcover);
        }
    }
    AdapterHome(ArrayList<BooksModel> data){
        this.dataBooks = data;

    }
    @NonNull
    @Override

    public AdapterHome.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_home,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHome.ViewHolder holder, int position) {
        TextView txt_booktitle = holder.booktitle;
        TextView txt_bookauthor = holder.bookauthor;
        ImageView ic_bookcover = holder.bookcover;

        txt_booktitle.setText(dataBooks.get(position).getTitle());
        txt_bookauthor.setText(dataBooks.get(position).getAuthor());
        ic_bookcover.setImageResource(dataBooks.get(position).getCover());

    }

    @Override
    public int getItemCount() {
        return dataBooks.size();
    }

    void setFilter(ArrayList<BooksModel> filterModel){
        dataBooks = new ArrayList<>();
        dataBooks.addAll(filterModel);
        notifyDataSetChanged();
    }


}
