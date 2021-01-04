package com.example.myapplication.dao;

import android.os.Environment;

import com.example.myapplication.beans.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

//we created this classe so we can manage all the songs available on the phone storage
public class SongsDAO {
    private List<Song> allsongs;
    private static SongsDAO instance=null;

    public static SongsDAO getInstance() {
        if(instance==null){
            instance=new SongsDAO();
        }
        return instance;
    }

    private SongsDAO(){

        final ArrayList<File> mysong = findSong(Environment.getExternalStorageDirectory()); // read songs by findSong method.
        allsongs=new ArrayList<Song>();

        for(int i=0;i<mysong.size();i++){
            String name=mysong.get(i).getName().replace(".mp3","");
            Song s= new Song(name,"uknown");
            s.setPath(mysong.get(i).getPath());
            allsongs.add(s);

        }

    }
    private ArrayList<File> findSong(File file) {

        ArrayList<File> arrayList = new ArrayList<>();

        File[] files = file.listFiles(); // create array object of File

        for(File singleFile : files){
            if(singleFile.isDirectory() && !singleFile.isHidden()){
                arrayList.addAll(findSong(singleFile));
            }
            else{
                if(singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav")){
                    arrayList.add(singleFile);
                }
            }
        }
        return arrayList;
    }

    public List<Song> getEventByKeyWord(String key) {
        List<Song>  songs = new Vector<>();
        key = key.toLowerCase();

        for (Song c : allsongs) {
            if (c.getName().toLowerCase().contains(key)) {
                songs.add(c);
            }
        }
        return songs;
    }

    public List<Song> getAllsongs() {
        return allsongs;
    }

    public void setAllsongs(List<Song> allsongs) {
        this.allsongs = allsongs;
    }
}
