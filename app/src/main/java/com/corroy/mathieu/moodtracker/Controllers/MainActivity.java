package com.corroy.mathieu.moodtracker.Controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.corroy.mathieu.moodtracker.Adapters.PageAdapter;
import com.corroy.mathieu.moodtracker.Models.HistoryDataBase;
import com.corroy.mathieu.moodtracker.Models.MoodEntry;
import com.corroy.mathieu.moodtracker.R;
import java.util.Date;

public class MainActivity extends FragmentActivity {

    private VerticalViewPager mVerticalViewPager;
    private ImageButton mCommentBtn;
    private ImageButton mHistoryBtn;
    private String m_Text = "";
    private MoodEntry mood = new MoodEntry();
    private HistoryDataBase mHistoryDatabase = new HistoryDataBase(this);
    private int moodState = 3 ;
    Date date = new Date();

    // Application launching
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVerticalViewPager = findViewById(R.id.activity_main_view_pager);
        mCommentBtn = findViewById(R.id.activity_comment_btn);
        mHistoryBtn = findViewById(R.id.activity_history_btn);
        mCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("What happened today ?");

                final EditText input = new EditText(MainActivity.this);
                input.setText(mood.getNote());
                builder.setView(input);

                builder.setPositiveButton("VALIDER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        mood.setNote(m_Text);
                        Toast.makeText(getApplicationContext(), R.string.comment_saved, Toast.LENGTH_LONG).show();
                        mVerticalViewPager.getCurrentItem();
                        mHistoryDatabase.addMood(mood);
                        mHistoryDatabase.updateMood(mood);
                        mHistoryDatabase.close();
                    }
                });
                builder.setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        mHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent history = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(history);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("MainActivity - onStart");
    }

    // Application is running
    @Override
    protected void onResume() {
        super.onResume();
        mHistoryDatabase = new HistoryDataBase(this);
        MoodEntry currMood = mHistoryDatabase.getMood(date);
        if(currMood != null) {
            m_Text = currMood.getNote();
        } else {
            moodState = 3;
            m_Text = "";
        }
        mHistoryDatabase.close();
        // Set the PagerAdapter to show fragments
        mVerticalViewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        mVerticalViewPager.setCurrentItem(moodState);
        System.out.println("MainActivity - onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("MainActivity - onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("MainActivity - onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        System.out.println("MainActivity - onDestroy");
    }
}
