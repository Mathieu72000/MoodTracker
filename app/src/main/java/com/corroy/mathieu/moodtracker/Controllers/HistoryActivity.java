package com.corroy.mathieu.moodtracker.Controllers;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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

            for (int i = 0; i < moodList.size(); i++) {
                if (moodList.get(i).daysDifference(date) != 0){
                    LinearLayout linearLayout = findViewById(R.id.linearLayout1);
                    configureLayout((MoodEntry) moodList, linearLayout);
                }
            }
        }

        private void configureLayout (final MoodEntry mood, LinearLayout historyPlaceHolder) {

        LinearLayout linearLayout1 = findViewById(R.id.linearLayout1);
        LinearLayout linearLayout2 = findViewById(R.id.linearLayout2);
        LinearLayout linearLayout3 = findViewById(R.id.linearLayout3);
        LinearLayout linearLayout4 = findViewById(R.id.linearLayout4);
        LinearLayout linearLayout5 = findViewById(R.id.linearLayout5);
        LinearLayout linearLayout6 = findViewById(R.id.linearLayout6);
        LinearLayout linearLayout7 = findViewById(R.id.linearLayout7);
        TextView historyTextView = findViewById(R.id.historyTextView);
        ImageButton historyImageBtn = findViewById(R.id.imageBtn);


            if (TextUtils.isEmpty(mood.getNote())) {
                historyImageBtn.setVisibility(View.GONE);
            } else {
                historyImageBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), mood.getNote(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;

            switch (mood.getMood()) {
                case SAD:
                    Log.d("Message", "largeur:" + historyPlaceHolder.getLayoutParams());
                    historyPlaceHolder.setBackground(getResources().getDrawable(R.color.faded_red));
                    ViewGroup.LayoutParams params = linearLayout1.getLayoutParams();
                    params.width = width / 5;
                    break;
                case DISAPPOINTED:
                    Log.d("Message", "largeur2:" + historyPlaceHolder.getLayoutParams());
                    historyPlaceHolder.setBackground(getResources().getDrawable(R.color.warm_grey));
                    ViewGroup.LayoutParams params1 = linearLayout1.getLayoutParams();
                    params1.width = width / 4;
                    break;
                case NORMAL:
                    Log.d("Message", "largeur3:" + historyPlaceHolder.getLayoutParams());
                    historyPlaceHolder.setBackground(getResources().getDrawable(R.color.cornflower_blue_65));
                    ViewGroup.LayoutParams params2 = linearLayout1.getLayoutParams();
                    params2.width = width / 3;
                    break;
                case HAPPY:
                    Log.d("Message", "largeur4:" + historyPlaceHolder.getLayoutParams());
                    historyPlaceHolder.setBackground(getResources().getDrawable(R.color.light_sage));
                    ViewGroup.LayoutParams params3 = linearLayout1.getLayoutParams();
                    params3.width = width / 2;
                    break;
                case SUPER_HAPPY:
                    Log.d("Message", "largeur5:" + historyPlaceHolder.getLayoutParams());
                    historyPlaceHolder.setBackground(getResources().getDrawable(R.color.banana_yellow));
                    ViewGroup.LayoutParams params4 = linearLayout1.getLayoutParams();
                    params4.width = width / 1;
                    break;
            }

            long daysDiff = mood.daysDifference(date);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                switch (toIntExact(daysDiff)){
                    case 1:
                        historyTextView.setText(R.string.yesterday);
                        break;
                    case 2:
                        historyTextView.setText(R.string.before_yesterday);
                        break;
                    case 7:
                        historyTextView.setText(R.string.a_week_ago);
                        break;
                    default:
                        String str = getString(R.string.days_ago_1) + toIntExact(daysDiff) + getString(R.string.spc_days);
                        historyTextView.setText(str);
                        break;
                }
            }
        }
}
