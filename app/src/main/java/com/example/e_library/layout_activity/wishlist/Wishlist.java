package com.example.e_library.layout_activity.wishlist;

public class Wishlist {
    private String judul, penulis, genre;
    private int image;
    private boolean isSelected = false;
    public Wishlist(){
    }
    public Wishlist(String judul, String penulis, String genre, int image) {
        this.judul = judul;
        this.penulis = penulis;
        this.genre = genre;
        this.image = image;
    }
    public String getJudul() {
        return judul;
    }
    public void setJudul(String judul) {
        this.judul = judul;
    }
    public String getPenulis() {
        return penulis;
    }
    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }
    public boolean isSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
