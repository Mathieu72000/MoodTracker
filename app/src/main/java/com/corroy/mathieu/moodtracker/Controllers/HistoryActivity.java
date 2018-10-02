package com.corroy.mathieu.moodtracker.Controllers;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.corroy.mathieu.moodtracker.Models.HistoryDataBase;
import com.corroy.mathieu.moodtracker.Models.MoodEntry;
import com.corroy.mathieu.moodtracker.R;
import java.util.Date;
import java.util.List;
import static java.lang.Math.toIntExact;

public class HistoryActivity extends AppCompatActivity {

    Date date = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        HistoryDataBase db = new HistoryDataBase(this);
        List<MoodEntry> moodList = db.getLastMoods();

        // Recover 7 Layouts for the 7 locations
        LinearLayout linearLayout1 = findViewById(R.id.linearLayout1);
        LinearLayout linearLayout2 = findViewById(R.id.linearLayout2);
        LinearLayout linearLayout3 = findViewById(R.id.linearLayout3);
        LinearLayout linearLayout4 = findViewById(R.id.linearLayout4);
        LinearLayout linearLayout5 = findViewById(R.id.linearLayout5);
        LinearLayout linearLayout6 = findViewById(R.id.linearLayout6);
        LinearLayout linearLayout7 = findViewById(R.id.linearLayout7);

        // Recover 7 TextView to show the date
        TextView historyTextView1 = findViewById(R.id.historyTextView1);
        TextView historyTextView2 = findViewById(R.id.historyTextView2);
        TextView historyTextView3 = findViewById(R.id.historyTextView3);
        TextView historyTextView4 = findViewById(R.id.historyTextView4);
        TextView historyTextView5 = findViewById(R.id.historyTextView5);
        TextView historyTextView6 = findViewById(R.id.historyTextView6);
        TextView historyTextView7 = findViewById(R.id.historyTextView7);

        // recover 7 ImageButton to display the user comment
        ImageButton historyImageBtn1 = findViewById(R.id.imageBtn1);
        ImageButton historyImageBtn2 = findViewById(R.id.imageBtn2);
        ImageButton historyImageBtn3 = findViewById(R.id.imageBtn3);
        ImageButton historyImageBtn4 = findViewById(R.id.imageBtn4);
        ImageButton historyImageBtn5 = findViewById(R.id.imageBtn5);
        ImageButton historyImageBtn6 = findViewById(R.id.imageBtn6);
        ImageButton historyImageBtn7 = findViewById(R.id.imageBtn7);

        // Call the method to configure the Layout, TextView, and ButtonImage and recover the moods on the List
        int size = moodList.size();
        if (0 < size) {
            configureLayout(linearLayout1, historyTextView1, historyImageBtn1, moodList.get(0));
        }
        if (1 < size) {
            configureLayout(linearLayout2, historyTextView2, historyImageBtn2, moodList.get(1));
        }
        if (2 < size) {
            configureLayout(linearLayout3, historyTextView3, historyImageBtn3, moodList.get(2));
        }
        if (3 < size) {
            configureLayout(linearLayout4, historyTextView4, historyImageBtn4, moodList.get(3));
        }
        if (4 < size) {
            configureLayout(linearLayout5, historyTextView5, historyImageBtn5, moodList.get(4));
        }
        if (5 < size) {
            configureLayout(linearLayout6, historyTextView6, historyImageBtn6, moodList.get(5));
        }
        if (6 < size) {
            configureLayout(linearLayout7, historyTextView7, historyImageBtn7, moodList.get(6));
        }
        if (moodList.size() == 0) {
            Toast.makeText(getApplicationContext(), "Pas encore d'historique ? Revenez demain !", Toast.LENGTH_LONG).show();
        }
    }

    // Method who configure Layouts, TextViews, ImageButtons and the object MoodEntry as parameters
    private void configureLayout(LinearLayout ll, TextView htv, ImageButton imgBtn, final MoodEntry mood) {

        // Set the visibility of the button to show it if a comment is available
        if (!TextUtils.isEmpty(mood.getNote())) {
            imgBtn.setVisibility(View.VISIBLE);
            imgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), mood.getNote(), Toast.LENGTH_LONG).show();
                }
            });
        }

        // Recover the screen size of the phone
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        // Switch with different mood who configure color and width of different locations
        switch (mood.getMood()) {
            case SAD:
                ll.setBackground(getResources().getDrawable(R.color.faded_red));
                ViewGroup.LayoutParams params = ll.getLayoutParams();
                params.width = width / 5;
                break;
            case DISAPPOINTED:
                ll.setBackground(getResources().getDrawable(R.color.warm_grey));
                ViewGroup.LayoutParams params1 = ll.getLayoutParams();
                params1.width = 2 * width / 5;
                break;
            case NORMAL:
                ll.setBackground(getResources().getDrawable(R.color.cornflower_blue_65));
                ViewGroup.LayoutParams params2 = ll.getLayoutParams();
                params2.width = 3 * width / 5;
                break;
            case HAPPY:
                ll.setBackground(getResources().getDrawable(R.color.light_sage));
                ViewGroup.LayoutParams params3 = ll.getLayoutParams();
                params3.width = 4 * width / 5;
                break;
            case SUPER_HAPPY:
                ll.setBackground(getResources().getDrawable(R.color.banana_yellow));
                break;
        }

        long daysDiff = mood.daysDifference(date);
        // To display the day of the selected mood on the history
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            switch (toIntExact(daysDiff)) {
                case 1:
                    htv.setText(R.string.today);
                    break;
                case 2:
                    htv.setText(R.string.yesterday);
                    break;
                case 3:
                    htv.setText(R.string.before_yesterday);
                    break;
                case 7:
                    htv.setText(R.string.a_week_ago);
                    break;
                default:
                    String str = getString(R.string.days_ago_1) + toIntExact(daysDiff) + getString(R.string.spc_days);
                    htv.setText(str);
                    break;
            }
        }
    }
}