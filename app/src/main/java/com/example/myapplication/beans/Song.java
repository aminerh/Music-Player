package com.example.myapplication.beans;
//the song class
public class Song {
    private String name;
    private int id;
    private String artist;
    private String path;


    public Song(String name, String artist) {
        this.name = name;

        this.artist = artist;
    }

    public Song() {

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getid() {
        return id;
    }

    public void setIds(int ids) {
        this.id = ids;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
