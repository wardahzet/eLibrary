package com.example.e_library.model;

import java.io.Serializable;

public class Rent implements Serializable {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String userId;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    private Book book;
    private String rentDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public Rent(String userId, Book book, String rentDate) {
        this.userId = userId;
        this.book = book;
        this.rentDate = rentDate;
    }

    private String bookingDate;

    public Rent() {
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
