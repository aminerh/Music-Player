package com.example.myapplication;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.beans.Song;
import com.example.myapplication.business.PlaylistService;
import com.example.myapplication.controllers.NavListner;
import com.example.myapplication.dao.SongsDAO;
import com.example.myapplication.tools.TrackAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//this activty present all the available songs and with possibilty to search for any specified song
public class TracksActivity extends AppCompatActivity {
    private ListView mylistview;
    private List<Song> allsongs;
    private PlaylistService service;
    private SongsDAO daoS;
    private TrackAdapter adapter;
    private String[] items;
    private EditText search;
    private boolean calledforresult=false;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracks);
        mylistview=findViewById(R.id.tracklists);
        search=findViewById(R.id.search_song);
        service=new PlaylistService(getApplicationContext());
        daoS=SongsDAO.getInstance();

        adapter=new TrackAdapter(service.getsongs(),this);
        displayInListView();

        Intent intent = getIntent();
        //calledforresult=intent.getBooleanExtra("ress",false);
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            if(intent.getBooleanExtra("fromplaylist",false)){
                calledforresult=intent.getBooleanExtra("fromplaylist",false);
            }
        }



        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                adapter.setAllss(daoS.getEventByKeyWord(search.getText().toString()));
                adapter.notifyDataSetChanged();

                return false;
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        NavListner nav=new NavListner(this);
        bottomNav.setOnNavigationItemSelectedListener(nav);
    }
    //this function is used to get all the songs and update the adapter
    public void refresh(View viw){
        service.clearsongs();

        for (Song c : daoS.getAllsongs()) {
            service.addsong(c);
        }
        adapter.setAllss(service.getsongs());
        adapter.notifyDataSetChanged();
    }

    private void displayInListView() {

        final ArrayList<File> mysong = findSong(Environment.getExternalStorageDirectory()); // read songs by findSong method.
        List<Song> allsongs=new ArrayList<Song>();

        for(int i=0;i<mysong.size();i++){
            String name=mysong.get(i).getName().replace(".mp3","");
            Song s= new Song(name,"uknown");
            s.setPath(mysong.get(i).getPath());
            allsongs.add(new Song(name,"uknown"));
          //  service.addsong(s);
        }
        //List<Song> test=service.getsongs();

        mylistview.setAdapter(adapter); // plugin adapter with list view.


        // Perform action on item click
        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(calledforresult){
                    Intent result = new Intent();
                    Song s = (Song) mylistview.getItemAtPosition(position);
                    result.putExtra("song", s.getName());

                    setResult(RESULT_OK,result);
                    finish();
                }else {
                    Song s = (Song) mylistview.getItemAtPosition(position);
                    String songName = s.getName();

                    startActivity(new Intent(TracksActivity.this, SongActivity.class)
                            .putExtra("songs", mysong).putExtra("songname", songName)
                            .putExtra("pos", position));
                }
                // Pass selected (song) item to another activity and also position of song in list.
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    // this function read all the files from the device
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



}