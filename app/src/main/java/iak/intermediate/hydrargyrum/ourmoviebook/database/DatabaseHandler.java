package iak.intermediate.hydrargyrum.ourmoviebook.database;

/**
 * Created by hydrargyrum on 2/21/2018.
 * This class for handling SQLite DB events
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import iak.intermediate.hydrargyrum.ourmoviebook.model.Movies;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHandler";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "moviesManager";
    private static final String TABLE_MOVIES = "tbmastermovies";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ORIGINAL_TITLE = "original_title";
    private static final String KEY_ORIGINAL_LANGUAGE = "original_language";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_POPULARITY = "popularity";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_VOTE_COUNT = "vote_count";
    private static final String KEY_FAVORITE = "favorite";
    private static final String KEY_RELEASE_DATE = "release_date";

    public DatabaseHandler(Context _context) {
        super(_context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase _db) {
        String CREATE_MOVIE_TABLE = "CREATE TABLE " + TABLE_MOVIES + " " +
                "(" + KEY_ID + " INTEGER PRIMARY KEY " +
                ", " + KEY_TITLE + " TEXT" +
                ", " + KEY_ORIGINAL_TITLE + " TEXT" +
                ", " + KEY_ORIGINAL_LANGUAGE + " TEXT" +
                ", " + KEY_OVERVIEW + " TEXT" +
                ", " + KEY_POPULARITY + " TEXT" +
                ", " + KEY_VOTE_AVERAGE + " TEXT" +
                ", " + KEY_VOTE_COUNT + " TEXT" +
                ", " + KEY_RELEASE_DATE + " TEXT" +
                ", " + KEY_POSTER_PATH + " TEXT" +
                ", " + KEY_FAVORITE + " TEXT )";
        Log.d(TAG, "onCreate : Table Movie Create Query : " + CREATE_MOVIE_TABLE);
        _db.execSQL(CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
        _db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(_db);
    }

    public void close() {
        this.close();
    }

    public void addMovie(Movies _movies) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, _movies.getId());
        values.put(KEY_TITLE, _movies.getTitle());
        values.put(KEY_POSTER_PATH, _movies.getPoster_path());
        values.put(KEY_FAVORITE, "FALSE");
        values.put(KEY_OVERVIEW, _movies.getOverview());
        values.put(KEY_POPULARITY, _movies.getPopularity());
        values.put(KEY_RELEASE_DATE, _movies.getRelease_date());
        values.put(KEY_VOTE_AVERAGE, _movies.getVote_average());
        values.put(KEY_VOTE_COUNT, _movies.getVote_count());
        db.insert(TABLE_MOVIES, null, values);

        Log.d(TAG, "onCreate: ADDED movie create Query :" + db);
        db.close();
    }

    public void clearMovie() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_MOVIES);
        db.close();
    }

    public boolean isMovieExist(int _id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MOVIES, new String[]{KEY_ID}, KEY_ID + "=?",
                new String[]{ String.valueOf(_id) }, null, null, null, null);
        if (cursor.getCount() <= 0){
            cursor.close();
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    public Movies getMovie(int _id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MOVIES, new String[]{KEY_ID,
                        KEY_TITLE
                        , KEY_ORIGINAL_TITLE
                        , KEY_ORIGINAL_LANGUAGE
                        , KEY_OVERVIEW
                        , KEY_POPULARITY
                        , KEY_VOTE_AVERAGE
                        , KEY_VOTE_COUNT
                        , KEY_RELEASE_DATE
                        , KEY_POSTER_PATH,
                        KEY_FAVORITE}, KEY_ID + "=?",
                new String[]{ String.valueOf(_id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Movies movies = new Movies(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getDouble(5), cursor.getString(6), cursor.getDouble(7), cursor.getInt(8));
        db.close();
        return movies;
    }

    public ArrayList<Movies> getAllListRated(int _page) {
        ArrayList<Movies> movieList = new ArrayList<Movies>();
        String selectQuery = "SELECT " +
                KEY_ID + ", " +
                KEY_TITLE + ", " +
                KEY_POSTER_PATH + ", " +
                KEY_FAVORITE + ", " +
                KEY_OVERVIEW + ", " +
                KEY_POPULARITY + ", " +
                KEY_RELEASE_DATE + ", " +
                KEY_VOTE_AVERAGE + ", " +
                KEY_VOTE_COUNT +
                " FROM " + TABLE_MOVIES +
                " WHERE " + KEY_FAVORITE + "= 'TRUE'" +
                " ORDER BY " + KEY_RELEASE_DATE + " DESC" +
                " LIMIT 20 OFFSET " + _page;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Movies movies = new Movies(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getDouble(5), cursor.getString(6), cursor.getDouble(7), cursor.getInt(8));
                movieList.add(movies);
            } while (cursor.moveToNext());
        }
        db.close();
        return movieList;
    }

    public ArrayList<Movies> getAllListMovies(int _page) {
        ArrayList<Movies> movieList = new ArrayList<Movies>();
        String selectQuery = "SELECT " +
                KEY_ID + ", " +
                KEY_TITLE + ", " +
                KEY_POSTER_PATH + ", " +
                KEY_FAVORITE + ", " +
                KEY_OVERVIEW + ", " +
                KEY_POPULARITY + ", " +
                KEY_RELEASE_DATE + ", " +
                KEY_VOTE_AVERAGE + ", " +
                KEY_VOTE_COUNT +
                " FROM " + TABLE_MOVIES +
                " ORDER BY " + KEY_RELEASE_DATE + " DESC" +
                " LIMIT 20 OFFSET " + _page;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Movies movies = new Movies(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getDouble(5), cursor.getString(6), cursor.getDouble(7), cursor.getInt(8));
                movieList.add(movies);
            } while (cursor.moveToNext());
        }
        db.close();
        return movieList;
    }

    public int updateMovie(Movies _movies) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, _movies.getId());
        values.put(KEY_TITLE, _movies.getTitle());
        values.put(KEY_POSTER_PATH, _movies.getPoster_path());
        values.put(KEY_FAVORITE, "TRUE");
        int result = 0;
        result = db.update(TABLE_MOVIES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(_movies.getId()) });
        db.close();
        return result;
    }

    public int unFavoriteMovie(Movies _movies){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FAVORITE, "FALSE");
        int result = 0;
        result = db.update(TABLE_MOVIES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(_movies.getId()) });
        db.close();
        return result;
    }
    public void deleteMovie(Movies movies) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIES, KEY_ID + " = ?",
                new String[] { String.valueOf(movies.getId()) });
        db.close();
    }
}
