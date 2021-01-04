package com.example.myapplication;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication.beans.Playlist;
import com.example.myapplication.business.PlaylistService;
import com.example.myapplication.dao.DatabaseHandler;

//this activity give to the user to create new playlists
public class AddPlaylist extends AppCompatActivity {
    private PlaylistService service;
    private EditText name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_playlist);
        name=(EditText)findViewById(R.id.playlistname);
        service=new PlaylistService(this);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;
     

        getWindow().setLayout((int)(width),500);
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-20;

        getWindow().setAttributes(params);
    }

    public void addPlay(View view){
        if(name.getText().toString().isEmpty())
            return;
        Playlist pl=new Playlist();
        pl.setName(name.getText().toString());
      //  DatabaseHandler db = new DatabaseHandler(this);
        service.addplaylist(pl);

        finish();
    }
    public void Back(View view){
        finish();
    }


}
