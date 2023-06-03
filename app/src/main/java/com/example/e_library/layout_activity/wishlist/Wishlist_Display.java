package com.example.e_library.layout_activity.wishlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.e_library.R;
import com.example.e_library.layout_activity.Home;

import java.util.ArrayList;
import java.util.List;

public class Wishlist_Display extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    WishlistAdapter _wishlistAdapter;
    List<Wishlist> _wishlistList;
    Button btn_back, btn_checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_wishlist);

        btn_back = findViewById(R.id.btn_back);
        btn_checkout = findViewById(R.id.btn_checkout);

        btn_back.setOnClickListener(this);
        btn_checkout.setOnClickListener(this);

        recyclerView =findViewById(R.id.rv_wishlist);
        RecyclerView.LayoutManager wishLayout = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(wishLayout);

        _wishlistList = new ArrayList<>();
        _wishlistAdapter = new WishlistAdapter(Wishlist_Display.this, _wishlistList);
        recyclerView.setAdapter(_wishlistAdapter);
        _wishlistAdapter.notifyDataSetChanged();

        //===============  TESTTTTTT ==========
        Wishlist coba1 = new Wishlist("The wizard of Oz","L.Frank Baum","Fiction",R.drawable.ic_books1);
        _wishlistList.add(coba1);

        Wishlist coba2 = new Wishlist("1984 ","George Orwell","Fiction",R.drawable.ic_books2);
        _wishlistList.add(coba2);
      //=======================

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btn_back){
            startActivity(new Intent(Wishlist_Display.this, Home.class));
        }
        else if(view.getId() == R.id.btn_checkout){
            //nyobaaa
            String data="";
            List<Wishlist> wishlistList2 = ((WishlistAdapter) _wishlistAdapter).getWishlistList();
            for(int i =0; i<wishlistList2.size();i++){
                Wishlist wish = wishlistList2.get(i);

                if(wish.isSelected()==true){
                    int image = wish.getImage();
                    String judul = wish.getJudul().toString();
                    String penulis = wish.getPenulis().toString();
                    String genre = wish.getGenre().toString();

                    data = judul;
                }Toast.makeText(Wishlist_Display.this,"borrow:" +data ,Toast.LENGTH_LONG).show();
            }
          //  startActivity(new Intent(Wishlist_Display.this, ....class));
        }
    }
}