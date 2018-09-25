package com.corroy.mathieu.moodtracker.Models;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Date;

public class AlarmBroadCast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        HistoryDataBase db = new HistoryDataBase(context);
        Date date = new Date();
        SharedPreferences notePref = context.getSharedPreferences("commentaire", Context.MODE_PRIVATE);
        SharedPreferences sharedPref = context.getSharedPreferences("humeur", Context.MODE_PRIVATE);
        Log.i("comment", notePref.getString("note", null));
        db.addMood(new MoodEntry(date, Mood.valueOf(sharedPref.getString("value", "HAPPY")), notePref.getString("note", null)));

    }
}
