package com.example.myapplication.controllers;

import android.content.Intent;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.SongActivity;
import com.example.myapplication.TracksActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

// this class is the listner for our bottom navigation bar
public class NavListner implements BottomNavigationView.OnNavigationItemSelectedListener {
    private AppCompatActivity owner;

    public NavListner(AppCompatActivity owner) {
        this.owner = owner;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_playlist:
                Intent intent = new Intent(owner, MainActivity.class);
                owner.startActivity(intent);
                break;
            case R.id.nav_tracks:
                intent = new Intent(owner, TracksActivity.class);
                owner.startActivity(intent);
                break;
            case R.id.nav_search:
                intent = new Intent(owner, SongActivity.class);
                owner.startActivity(intent);
                break;
        }
        return false;
    }
}
