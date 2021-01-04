package com.example.myapplication.business;

import android.content.Context;

import com.example.myapplication.beans.Playlist;
import com.example.myapplication.beans.Song;
import com.example.myapplication.dao.DatabaseHandler;

import java.sql.DataTruncation;
import java.util.List;

//this is a service class that allow us to communicate with database we don t communicare directly to database
public class PlaylistService {
    private DatabaseHandler playlistDao;
    public PlaylistService(Context context) {
        playlistDao=new DatabaseHandler(context);

    }
    public void clearsongs(){
        playlistDao.clear();
    }
    public List<Playlist> getplaylists() {

        return playlistDao.getAllRows();
    }
    public int getPLId(String plname){
        return playlistDao.getplid(plname);
    }
    public List<Song> getplsongs(Playlist p) {

        return playlistDao.getplsongs(p);
    }
    public List<Song> getsongs() {

        return playlistDao.getallsongs();
    }
    public void addplaylist(Playlist pl) {
        playlistDao.addRow(pl);
    }

    public void addsong(Song s) {
        playlistDao.addSong(s);
    }

    public void addsongtopl(Song s,Playlist pl) {
        playlistDao.addSongtopl(s,pl);
    }


    public void removeplaylist(String name) {
       // playlistDao.de(name);
    }

    public int getsid(String sname) {
        return playlistDao.getsid(sname);
    }
}
