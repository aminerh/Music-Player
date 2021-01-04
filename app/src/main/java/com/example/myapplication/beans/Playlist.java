package com.example.myapplication.beans;

import java.util.List;
// the playlist class
public class Playlist {
    private int id;
    private String name;
    private List<Song> mysongs;
    private int nbrSongs;

    public Playlist() {
    }

    public Playlist(int id,String name) {
        this.id=id;
        this.name = name;
        nbrSongs=0;
        this.mysongs = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Playlist(String name, List<Song> mysongs) {
        this.name = name;
        this.mysongs = mysongs;
        this.nbrSongs=mysongs.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getMysongs() {
        return mysongs;
    }

    public void setMysongs(List<Song> mysongs) {
        this.mysongs = mysongs;
    }

    public int getNbrSongs() {
        return nbrSongs;
    }

    public void setNbrSongs(int nbrSongs) {
        this.nbrSongs = nbrSongs;
    }
}
