package com.corroy.mathieu.moodtracker.Controllers;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.corroy.mathieu.moodtracker.Models.MoodEntry;
import com.corroy.mathieu.moodtracker.R;

public class HistoryActivity extends AppCompatActivity {

    LinearLayout historyLinear;
    LinearLayout location1;
    LinearLayout location2;
    LinearLayout location3;
    LinearLayout location4;
    LinearLayout location5;
    LinearLayout location6;
    LinearLayout location7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyLinear = findViewById(R.id.history_Linear);
        location1 = findViewById(R.id.history_location1);
        location2 = findViewById(R.id.history_location2);
        location3 = findViewById(R.id.history_location3);
        location4 = findViewById(R.id.history_location4);
        location5 = findViewById(R.id.history_location5);
        location6 = findViewById(R.id.history_location6);
        location7 = findViewById(R.id.history_location7);
    }
}
