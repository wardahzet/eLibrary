package com.example.e_library.model;

import java.io.Serializable;

public class Book implements Serializable {
    private String key;
    private String isbn;
    private String title;
    private String author;
    private String synopsis;
//    private String cover;

    public Book(String isbn, String title, String author, String synopsis){
        this.setIsbn(isbn);
        this.setTitle(title);
        this.setAuthor(author);
        this.setSynopsis(synopsis);
    }

    public Book(){

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

//    public int getCover() {
//        return cover;
//    }
//
//    public void setCover(int cover) {
//        this.cover = cover;
//    }
}
