package com.corroy.mathieu.moodtracker.Controllers;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.corroy.mathieu.moodtracker.Adapters.PageAdapter;
import com.corroy.mathieu.moodtracker.Models.AlarmBroadCast;
import com.corroy.mathieu.moodtracker.Models.HistoryDataBase;
import com.corroy.mathieu.moodtracker.Models.Mood;
import com.corroy.mathieu.moodtracker.Models.MoodEntry;
import com.corroy.mathieu.moodtracker.R;
import com.facebook.stetho.Stetho;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends FragmentActivity {

    private VerticalViewPager mVerticalViewPager;
    private ImageButton mCommentBtn;
    private ImageButton mHistoryBtn;
    private String m_Text = "";
    private MoodEntry mood;
    private HistoryDataBase mHistoryDataBase;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private Context context;

    // Application launching
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);

        mHistoryDataBase = new HistoryDataBase(this);
        Date date = new Date();
        mood = new MoodEntry(date, Mood.DISAPPOINTED, "");
        context = this;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 30);

        alarmMgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmBroadCast.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);

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
                        SharedPreferences notePref = context.getSharedPreferences("commentaire", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = notePref.edit();
                        mood.setNote(m_Text);
                        Toast.makeText(getApplicationContext(), R.string.comment_saved, Toast.LENGTH_LONG).show();
                        editor.putString("note", m_Text);
                        editor.commit();
                        Log.i("comment", notePref.getString("note", null));
                        mVerticalViewPager.getCurrentItem();
                        mHistoryDataBase.close();
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
        Date date = new Date();
        mHistoryDataBase = new HistoryDataBase(this);
        MoodEntry currMood = mHistoryDataBase.getMood(date);
        if(currMood != null) {
            m_Text = currMood.getNote();
        } else {
            m_Text = "";
        }
        mHistoryDataBase.close();
        // Set the PagerAdapter to show fragments
        mVerticalViewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        mVerticalViewPager.setCurrentItem(3);
        System.out.println("MainActivity - onResume");
        mVerticalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position){

                MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.woosh);
                mediaPlayer.start();

                String humeur = "";

                SharedPreferences sharedPref = context.getSharedPreferences("humeur", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                switch (position){
                    case 0:
                       humeur = "SAD";
                       break;
                    case 1:
                        humeur = "DISAPPOINTED";
                        break;
                    case 2:
                        humeur = "NORMAL";
                        break;
                    case 3:
                        humeur = "HAPPY";
                        break;
                    case 4:
                        humeur = "SUPER_HAPPY";
                        break;
                }
                editor.putString("value", humeur);
                editor.commit();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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