package com.corroy.mathieu.moodtracker.Models;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.Date;

public class AlarmBroadCast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        HistoryDataBase db = new HistoryDataBase(context);
        Date date = new Date();

        // Recover the SharedPreferences of the mood and the comment and add it on the database when the alarm sound
        SharedPreferences notePref = context.getSharedPreferences("commentaire", Context.MODE_PRIVATE);
        SharedPreferences sharedPref = context.getSharedPreferences("humeur", Context.MODE_PRIVATE);
        db.addMood(new MoodEntry(date, Mood.valueOf(sharedPref.getString("value", "HAPPY")), notePref.getString("note", null)));
    }
}