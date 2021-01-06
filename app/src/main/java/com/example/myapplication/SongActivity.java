package com.example.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.PorterDuff;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.myapplication.tools.Myservice;

import java.io.File;
import java.util.ArrayList;
/*this activity launch the selected music and give you the ability to go to the next music or the preceding one
* normally we should launch our service in this activity but i had some problems to manage using the bound service
* so we just created a mediaplayer and implements all the necessary method and by default the mediaplayer is launched in
* the background
*
* also at this activity we created a sensors that implements the device accelerometer and by this sensors we control the music
* in the event of shaking the phone we launch the next music in list
*  */
public class SongActivity extends AppCompatActivity implements SensorEventListener {

   // private Accelerometre accelerometre;


//-------------------sensor variables
    private SensorManager sensorManager;
    private Sensor sensor;
    float curentx,curenty,curentz;
    float lastx,lasty,lastz;
    boolean notfirsttime=false;
    float shake=5f;
    //------------------bound service
    private Myservice musicservice;
    boolean serviceBound = false;
    Intent playerIntent;

    //-----------------------------------------
    Button btnNext, btnPrevious, btnPause;
    TextView songLabel;
    SeekBar songSeekbar;



    static MediaPlayer myMediaPlayer;
    int position;
    String sname;
    ArrayList<File> mySongs;
    Thread updateSeekBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_activity);
        btnNext = (Button)findViewById(R.id.next);
        btnPrevious = (Button)findViewById(R.id.previous);
        btnPause = (Button)findViewById(R.id.pause);
        songLabel = (TextView)findViewById(R.id.songLabel);


        //get all the songs passed
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        mySongs = (ArrayList) bundle.getParcelableArrayList("songs");

        //getting the selected song position
        position = bundle.getInt("pos", 0);
        sname = mySongs.get(position).getName().toString();

        String songName = intent.getStringExtra("songname");

        songLabel.setText(songName); // display song title
        songLabel.setSelected(true);

        if(myMediaPlayer != null){
            myMediaPlayer.stop();
            myMediaPlayer.release();
        }

        Uri u = Uri.parse(mySongs.get(position).toString());




        myMediaPlayer = MediaPlayer.create(getApplicationContext(), u);
        myMediaPlayer.start();

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(myMediaPlayer.isPlaying()){
                    btnPause.setBackgroundResource(R.drawable.icon_play);
                    myMediaPlayer.pause();
                }else{
                    btnPause.setBackgroundResource(R.drawable.icon_pause);
                    myMediaPlayer.start();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myMediaPlayer.stop(); // first stop the music
                myMediaPlayer.release();
                // then, increase position of song
                position = (position+1) % mySongs.size(); // also consider boundary cases.

                Uri u = Uri.parse(mySongs.get(position).toString());
                myMediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                sname = mySongs.get(position).getName().toString();
                songLabel.setText(sname);

                myMediaPlayer.start(); // then, start song
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myMediaPlayer.stop(); // first stop the music
                myMediaPlayer.release();
                // then, decrease position of song
                position = ((position-1) < 0) ? (mySongs.size()-1):position-1; // also consider boundary case less than 0.

                Uri u = Uri.parse(mySongs.get(position).toString());
                myMediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                sname = mySongs.get(position).getName().toString();
                songLabel.setText(sname);

                myMediaPlayer.start(); // then, start song
            }
        });

        //--------------------sensors
        sensorManager= (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!=null){
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        }else{
            Toast.makeText(getApplicationContext(),"No sensor ", Toast.LENGTH_SHORT).show();
        }

    }
    //Binding this Client to the AudioPlayer Service
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            Myservice.LocalBinder binder = (Myservice.LocalBinder) service;
            musicservice = binder.getService();
            //serviceBound = true;

            Toast.makeText(SongActivity.this, "Service Bound", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
       // accelerometre.unregister();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        curentx=event.values[0];
        curenty=event.values[1];
        curentz=event.values[2];

        if(notfirsttime){
            float xdiff=Math.abs(lastx-curentx);
            float ydiff=Math.abs(lasty-curenty);
            float zdiff=Math.abs(lastz-curentz);

            if((xdiff>shake && ydiff > shake)||(xdiff >shake && zdiff>shake)||(ydiff>shake && zdiff >shake)){
                btnNext.callOnClick();
            }
        }

        lastx =curentx;
        lasty =curenty;
        lastz =curentz;

        notfirsttime=true;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
