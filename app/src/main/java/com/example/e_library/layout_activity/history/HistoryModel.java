package com.example.e_library.layout_activity.history;

public class HistoryModel {
    String title, author, genre;
    int cover, status;

    public HistoryModel(String title, String author, String genre, int cover, int status) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.cover = cover;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getCover() {
        return cover;
    }

    public int getStatus() {
        return status;
    }
}
