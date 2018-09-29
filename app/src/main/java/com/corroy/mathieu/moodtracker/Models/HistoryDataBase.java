package com.corroy.mathieu.moodtracker.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

// Class required for creation and access to the database
public class HistoryDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MoodTracker.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_MOODS = "MOODS";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_MOOD = "MOOD";
    private static final String COLUMN_DATE = "DATE";
    private static final String COLUMN_NOTE = "NOTE";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);

    public HistoryDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOOD_TABLE = "CREATE TABLE "
                + TABLE_MOODS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_MOOD + " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_NOTE + " TEXT" + ")";
        db.execSQL(CREATE_MOOD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOODS);
        onCreate(db);
    }

    // Add a new mood to the database
    public void addMood(MoodEntry mood) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MOOD, mood.getMood().name());
        values.put(COLUMN_DATE, dateFormat.format(mood.getDate()));
        values.put(COLUMN_NOTE, mood.getNote());
        db.insert(TABLE_MOODS, null, values);
        db.close();
    }

    // Retrieves the last 7 moods of the database
    public List<MoodEntry> getLastMoods() {
        List<MoodEntry> moodList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MOODS + " ORDER BY date DESC " + "LIMIT 7";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                MoodEntry mood = new MoodEntry();
                try {
                    mood.setDate(dateFormat.parse(cursor.getString(2)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mood.setMood(Mood.valueOf(Mood.class, cursor.getString(1)));
                mood.setNote(cursor.getString(3));
                moodList.add(mood);
            } while (cursor.moveToNext());
        }
        return moodList;
    }
}