package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.example.myapplication.beans.Playlist;
import com.example.myapplication.beans.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Templates;

//this class is for our handling our sqllite database
public class DatabaseHandler extends SQLiteOpenHelper {

    // Database version
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "playlistManager";

    // Table name
    private static final String TABLE_PLAYLIST = "playlists";
    private static final String TABLE_SONG = "songs";
    private static final String TABLE_HAVE = "have";

    // Table columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    private static final String KEY_IDS = "id_S";
    private static final String KEY_NAMES = "name_s";
    private static final String KEY_PATH = "path";
    //private static final String KEY_NBR = "nbrsongs";
   // private static final String KEY_TYPE = "idsongs";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    // Create the table
    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_PLAYLISTE_TABLE = "CREATE TABLE " + TABLE_PLAYLIST + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + KEY_NAME + " TEXT" + ")";
        db.execSQL(CREATE_PLAYLISTE_TABLE);

        String CREATE_SONGS_TABLE = "CREATE TABLE " + TABLE_SONG + "("
                + KEY_IDS + " INTEGER PRIMARY KEY autoincrement," + KEY_NAMES + " TEXT," +
                KEY_PATH+" TEXT"+")";

        String CREATE_HAVE_TABLE = "CREATE TABLE " + TABLE_HAVE + "("
                + KEY_IDS + " INTEGER NOT NULL," + KEY_ID + " INTEGER NOT NULL,"
                +"PRIMARY KEY ("+KEY_IDS+","+KEY_ID+"),"
                + "FOREIGN KEY ("+KEY_IDS+") REFERENCES "+TABLE_SONG+"("+KEY_IDS+"),"
                + "FOREIGN KEY ("+KEY_ID+") REFERENCES "+TABLE_PLAYLIST+"("+KEY_ID+")"
                +")";
       // db.execSQL(CREATE_PLAYLISTE_TABLE);
        db.execSQL(CREATE_SONGS_TABLE);
        db.execSQL(CREATE_HAVE_TABLE);



       // addRow(new Playlist("test"));

    }

    // Upgrade the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HAVE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG);


        // Create tables again
        onCreate(db);
    }

    // Add a new row
    public void addRow(Playlist e) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, e.getName());

        //values.put(KEY_NBR, e.getNbrSongs());

        // Insert row
        db.insert(TABLE_PLAYLIST, null, values);
        db.close();

    }

    public int getplid(String pln){



        // Select all query
        String selectQuery = "SELECT * FROM " + TABLE_PLAYLIST
                            +" WHERE "+KEY_NAME+" = '"+pln+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all the rows and addi the to the list
        if (cursor.moveToFirst()) {
            do {
                return Integer.parseInt(cursor.getString(0));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // Return the list
        return 0;
    }

    public void addSongtopl(Song e,Playlist pl){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IDS, e.getid());
        values.put(KEY_ID,pl.getId());
        // Insert row
        db.insert(TABLE_HAVE, null, values);
        db.close();
    }

    public void addSong(Song e) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAMES, e.getName());
        values.put(KEY_PATH, e.getPath());


        //values.put(KEY_NBR, e.getNbrSongs());

        // Insert row
        db.insert(TABLE_SONG, null, values);
        db.close();

    }

    public void addsongtopl(int id_song ,int id_pl){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IDS, id_song);
        values.put(KEY_ID, id_pl);


        //values.put(KEY_NBR, e.getNbrSongs());

        // Insert row
        db.insert(TABLE_HAVE, null, values);
        db.close();
    }


    // Get all PLAYLIST
    public List<Playlist> getAllRows() {
        List<Playlist> l = new ArrayList<>();


        // Select all query
        String selectQuery = "SELECT * FROM " + TABLE_PLAYLIST;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all the rows and addi the to the list
        if (cursor.moveToFirst()) {
            do {
                Playlist e = new Playlist();
                e.setId(Integer.parseInt(cursor.getString(0)));
                e.setName(cursor.getString(1));
               // e.setNbrSongs(0);

                // Add row to list
                l.add(e);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // Return the list
        return l;
    }

    public List<Song> getplsongs(Playlist p){
        List<Song> l = new ArrayList<>();


        // Select all query
        String selectQuery = "SELECT "+TABLE_SONG+"."+KEY_IDS+","+TABLE_SONG+"."+KEY_NAMES+","+TABLE_SONG+"."+KEY_PATH+" FROM "+TABLE_HAVE+" , "+TABLE_SONG+
                        " WHERE "+TABLE_HAVE+"."+KEY_ID+"="+p.getId()+" AND "
                            +TABLE_SONG+"."+KEY_IDS+"="+TABLE_HAVE+"."+KEY_IDS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all the rows and addi the to the list
        if (cursor.moveToFirst()) {
            do {
                Song e = new Song();
                e.setIds(Integer.parseInt(cursor.getString(0)));
                e.setName(cursor.getString(1));
                e.setArtist("uknown");
                e.setPath(cursor.getString(2));

                // Add row to list
                l.add(e);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // Return the list
        return l;

    }


    // Get all songs NAMES FOR NOWV
    public List<Song> getallsongs() {
        List<Song> l = new ArrayList<>();


        // Select all query
        String selectQuery = "SELECT * FROM " + TABLE_SONG;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all the rows and addi the to the list
        if (cursor.moveToFirst()) {
            do {
                Song e = new Song();
                e.setIds(Integer.parseInt(cursor.getString(0)));
                e.setName(cursor.getString(1));
                e.setPath(cursor.getString(2));
                // e.setNbrSongs(0);

                // Add row to list
                l.add(e);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // Return the list
        return l;
    }




    // Clear the table
    public void clear() {
        SQLiteDatabase db = this.getWritableDatabase();
      //  db.execSQL("DELETE FROM " + TABLE_HAVE);
        db.execSQL("DELETE FROM " + TABLE_SONG);
       // db.execSQL("DELETE FROM " + TABLE_PLAYLIST);
        db.close();
    }

    public int getCurrentId() {
        SQLiteDatabase db = this.getWritableDatabase();
        String q = "SELECT MAX(" + KEY_ID + ") FROM " + TABLE_PLAYLIST;
        Cursor c = db.rawQuery(q, null);

        if (c.moveToFirst()) {
            int id = c.getInt(0);
            c.close();
            db.close();
            return id;
        } else {
            return 0;
        }
    }

    public int getsid(String sname) {


        // Select all query
        String selectQuery = "SELECT * FROM " + TABLE_SONG
                +" WHERE "+KEY_NAMES+" = '"+sname+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all the rows and addi the to the list
        if (cursor.moveToFirst()) {
            do {
                return Integer.parseInt(cursor.getString(0));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // Return the list
        return 0;
    }
}