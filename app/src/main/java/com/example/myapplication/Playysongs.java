package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.beans.Playlist;
import com.example.myapplication.beans.Song;
import com.example.myapplication.business.PlaylistService;
import com.example.myapplication.tools.TrackAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
//this activity present all the song available on the selected playlist
public class Playysongs extends AppCompatActivity {
    private ListView mylist;
    private PlaylistService service;
    private TextView namep;
    private List<Song> songs;
    private TrackAdapter adap;
    private Playlist pl=new Playlist();
    Playlist temp;
    private  ArrayList<File> fsongss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlistsongs);
        service=new PlaylistService(getApplicationContext());
        namep=findViewById(R.id.plname);

        mylist = findViewById(R.id.plsongs);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        pl.setName(intent.getStringExtra("playlist"));
        namep.setText(pl.getName());
        temp=new Playlist();
        temp.setName(pl.getName());
        temp.setId(service.getPLId(pl.getName()));
        songs= service.getplsongs(temp);

        adap= new TrackAdapter(songs,this);

        mylist.setAdapter(adap);
        fsongss= new ArrayList<>();
        for(Song c : songs){
            fsongss.add(new File(c.getPath()));
        }


        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song s = (Song) mylist.getItemAtPosition(position);
                String songName = s.getName();

                startActivity(new Intent(Playysongs.this, SongActivity.class)
                        .putExtra("songs", fsongss).putExtra("songname", songName)
                        .putExtra("pos", position));
            }
        });
    }
    public void adds(View v){
        Intent  intent = new Intent(this, TracksActivity.class);
        boolean getpl=true;
        intent.putExtra("fromplaylist",getpl);
        startActivityForResult(intent,5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==5){
            if(resultCode==RESULT_OK){
                Song s= new Song();
                s.setName(data.getStringExtra("song"));
                s.setIds(service.getsid(s.getName()));
                service.addsongtopl(s,temp);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adap.setAllss(service.getplsongs(temp));
        adap.notifyDataSetChanged();
        for(Song c : songs){
            fsongss.add(new File(c.getPath()));
        }
    }
}