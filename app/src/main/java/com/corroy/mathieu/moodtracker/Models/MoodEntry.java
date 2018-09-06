package com.corroy.mathieu.moodtracker.Models;

import java.util.Date;


public class MoodEntry {
    private Date date;
    private Mood mood;
    private String note;

    public MoodEntry() {
    }

    public MoodEntry(Date date, Mood mood, String note) {
        this.date = date;
        this.mood = mood;
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Mood getMood(){
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long daysDifference(Date endDate) {
        long different = endDate.getTime() - this.date.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        return different / daysInMilli;
    }
}