package sg.edu.rp.c346.id19048236.ndpsongs_l09;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ndpsongs.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_SONG = "song";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_SONG_TITLE = "song_title";
    private static final String COLUMN_SONG_SINGERS = "song_singers";
    private static final String COLUMN_SONG_YEAR = "song_year";
    private static final String COLUMN_SONG_STARS = "song_star";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String createSongTableSql = "CREATE TABLE " + TABLE_SONG + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SONG_TITLE + " TEXT,"
                + COLUMN_SONG_SINGERS + " TEXT,"
                + COLUMN_SONG_YEAR + " INTEGER,"
                + COLUMN_SONG_STARS + " INTEGER ) ";

        db.execSQL(createSongTableSql);
        Log.i("info", "created tables");

        //Dummy records, to be inserted when the database is created
        for (int i = 0; i< 4; i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_SONG_TITLE, "Data number " + i);
            db.insert(TABLE_SONG, null, values);
        }
        Log.i("info", "dummy records inserted");

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE " + TABLE_SONG + " ADD COLUMN  module_name TEXT ");
    }

    public long insertSong(String songTitle, String songSinger, int songYear, int songStar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SONG_TITLE, songTitle);
        values.put(COLUMN_SONG_SINGERS, songSinger);
        values.put(COLUMN_SONG_YEAR, songYear);
        values.put(COLUMN_SONG_STARS,songStar);
        long result = db.insert(TABLE_SONG, null, values);
        db.close();
        Log.d("SQL Insert","ID:"+ result); //id returned, shouldn’t be -1
        return result;
    }

    public ArrayList<Songs> getAllSongs() {
        ArrayList<Songs> songs = new ArrayList<Songs>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_SONG_TITLE + ","
                + COLUMN_SONG_SINGERS + ","
                + COLUMN_SONG_YEAR +  ","
                + COLUMN_SONG_STARS
                + " FROM " + TABLE_SONG;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String songTitle = cursor.getString(1);
                String songSinger= cursor.getString(2);
                int songYear= cursor.getInt(3);
                int songStar= cursor.getInt(4);
                Songs song = new Songs(id,songTitle, songSinger, songYear, songStar);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    public int updateSong(Songs data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SONG_TITLE, data.getTitle());
        values.put(COLUMN_SONG_SINGERS, data.getSingers());
        values.put(COLUMN_SONG_YEAR, data.getYear());
        values.put(COLUMN_SONG_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.get_id())};
        int result = db.update(TABLE_SONG, values, condition, args);
        db.close();
        return result;
    }
    public ArrayList<Songs> getAllSongByStar(int starFilter) {
        ArrayList<Songs> songs = new ArrayList<Songs>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_SONG_TITLE, COLUMN_SONG_SINGERS, COLUMN_SONG_YEAR, COLUMN_SONG_STARS};
        String condition = COLUMN_SONG_STARS + " >= ?";
        String[] args = { String.valueOf(starFilter)};
        Cursor cursor = db.query(TABLE_SONG, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String songTitle = cursor.getString(1);
                String songSinger= cursor.getString(2);
                int songYear= cursor.getInt(3);
                int songStar= cursor.getInt(4);
                Songs song = new Songs(id,songTitle, songSinger, songYear, songStar);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    public int deleteSong(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_SONG, condition, args);
        db.close();
        return result;
    }

}
