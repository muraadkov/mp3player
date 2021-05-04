package com.example.mp3playermt;

import java.io.Serializable;

public class Music implements Serializable {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getSong() {
        return song;
    }

    public void setSong(int song) {
        this.song = song;
    }

    private String name;



    private String author;
    private int song;
    public Music(String name, String author, int song) {
        this.name = name;
        this.author = author;
        this.song = song;
    }
}
