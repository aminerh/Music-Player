package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myapplication.beans.Playlist;
import com.example.myapplication.beans.Song;
import com.example.myapplication.business.PlaylistService;
import com.example.myapplication.controllers.NavListner;
import com.example.myapplication.dao.DatabaseHandler;
import com.example.myapplication.dao.SongsDAO;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.tools.CustomAdapter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
/*
*this project is realised by
* AMINE RHOUNI BELLOUTI
* JAD BENHRA
*
*
*
*
*
 */
public class MainActivity extends AppCompatActivity {

    private GridView myListView;
    private CustomAdapter adapter;
    private PlaylistService service;
    private SongsDAO daoS;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // DatabaseHandler db =new DatabaseHandler(this);
        service=new PlaylistService(this);
        adapter=new CustomAdapter(service.getplaylists(),this);
        myListView=findViewById(R.id.gridview);

        myListView.setAdapter(adapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Playlist plame = (Playlist) myListView.getItemAtPosition(position);
                String plName=plame.getName();

                    startActivity(new Intent(MainActivity.this, Playysongs.class)
                        .putExtra("playlist",  plName)
                        .putExtra("pos",position));
                // Pass selected (song) item to another activity and also position of song in list.
            }
        });

        runtimePermission();



        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        NavListner nav=new NavListner(this);
        bottomNav.setOnNavigationItemSelectedListener(nav);


    }

    @Override
    protected void onResume() {
        super.onResume();
        //to update our list adapter
        adapter.setAllpl(service.getplaylists());
        adapter.notifyDataSetChanged();
    }
    public void add(View view){
        //launch the activity so we can add new playlists
        Intent Popwindow = new Intent(MainActivity.this,AddPlaylist.class);
        startActivity(Popwindow);

    }
    // Get read permission from user.
    private void runtimePermission(){

        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {

                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                       Toast.makeText(getApplicationContext(),"Permission granted",Toast. LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(MainActivity.this, "Please grant permission", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest(); // if user denied permission.

                    }
                }).check();
    }





}