package com.example.myapplication.tools;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication.R;
import com.example.myapplication.beans.Playlist;
import com.example.myapplication.beans.Song;

import java.util.ArrayList;
import java.util.List;

//this is our song adapter that manage to present all of our songs
public class TrackAdapter extends BaseAdapter {
    private Activity activity;

    private List<Song> allss=new ArrayList<Song>();

    public TrackAdapter(List<Song> songs,Activity activity) {
        this.activity = activity;
        this.allss=songs;

    }
    @Override
    public int getCount() {
        return allss.size();
    }

    @Override
    public Object getItem(int position) {
        return allss.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout rela;
        if (convertView == null) {
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            rela = (RelativeLayout) layoutInflater.inflate(R.layout.one_song, null);
        } else {
            rela = (RelativeLayout) convertView;
        }
        TextView Title = rela.findViewById(R.id.tv_music_name);
        TextView Artist = rela.findViewById(R.id.tv_music_subtitle);
        Title.setText(allss.get(position).getName());
        Artist.setText(allss.get(position).getArtist());

        return rela;
    }

    public List<Song> getAllss() {
        return allss;
    }

    public void setAllss(List<Song> allss) {
        this.allss = allss;
    }
}
