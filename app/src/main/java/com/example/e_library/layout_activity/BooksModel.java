package com.example.e_library.layout_activity;

public class BooksModel {
    String title, author;
    int cover;

    public BooksModel(String title, String author, int cover) {
        this.title = title;
        this.author = author;
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getCover() {
        return cover;
    }
}
