package com.example.myapplication.tools;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication.R;
import com.example.myapplication.beans.Playlist;

import java.util.ArrayList;
import java.util.List;

//this is our playlist adapter that manage to present our playlists
public class CustomAdapter extends BaseAdapter {
    private Activity activity;
    private List<Playlist> allpl=new ArrayList<Playlist>();


    public CustomAdapter(List<Playlist> playlists,Activity activity) {
        this.activity = activity;
        this.allpl=playlists;

    }

    @Override
    public int getCount() {
        return allpl.size();
    }

    @Override
    public Object getItem(int position) {
        return allpl.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("tesstt","passed me");
        ConstraintLayout constraintLayout;
        if (convertView == null) {
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            constraintLayout = (ConstraintLayout) layoutInflater.inflate(R.layout.one_playlist, null);
        } else {
            constraintLayout = (ConstraintLayout) convertView;
        }
        ((TextView) constraintLayout.findViewById(R.id.playlist_name))
                .setText(allpl.get(position).getName());
        ((ImageView) constraintLayout.findViewById(R.id.imageView2)).setImageResource(R.drawable.playlist);
        ((TextView) constraintLayout.findViewById(R.id.nbr_songs))
                .setText(""+allpl.get(position).getNbrSongs()+"songs");



        return constraintLayout;
    }

    public List<Playlist> getAllpl() {
        return allpl;
    }

    public void setAllpl(List<Playlist> allpl) {
        this.allpl = allpl;
    }
}
