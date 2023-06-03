package com.example.e_library.layout_activity.wishlist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_library.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.MyHolder> {

    List <Wishlist> wishlistList;
    private Context context;

    public WishlistAdapter(Context context, List<Wishlist> wishlistList) {
        this.context = context;
        this.wishlistList = wishlistList;
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
        Wishlist wishlists = wishlistList.get(position);

        holder.txt_judul.setText(wishlists.getJudul());
        holder.txt_penulis.setText(wishlists.getPenulis());
        holder.txt_genre.setText(wishlists.getGenre());
        holder.ic_image.setImageResource(wishlists.getImage());
        holder.cb_checkBox.setChecked(wishlists.isSelected());
        holder.cb_checkBox.setTag(wishlists);

        holder.cb_checkBox.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                CheckBox cb_check = (CheckBox) view;
                Wishlist contact = (Wishlist) cb_check.getTag();

                contact.setSelected(cb_check.isChecked());
                wishlists.setSelected(cb_check.isChecked());


            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(holder.getLayoutPosition());
                Toast.makeText(context,"Delete",Toast.LENGTH_LONG).show();
            }
        });
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
//========cobaaaaaa
    public List<Wishlist> getWishlistList() {
        return wishlistList;
    }
    private void removeItem(int position) {
       wishlistList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, wishlistList.size());
    }
}
