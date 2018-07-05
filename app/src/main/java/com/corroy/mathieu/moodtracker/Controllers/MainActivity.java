package com.corroy.mathieu.moodtracker.Controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import com.corroy.mathieu.moodtracker.Adapters.PageAdapter;
import com.corroy.mathieu.moodtracker.R;


public class MainActivity extends FragmentActivity {

    private VerticalViewPager mVerticalViewPager;
    private ImageButton mCommentBtn;
    private String m_Text = "";

    // Application launching
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVerticalViewPager = findViewById(R.id.activity_main_view_pager);
        mCommentBtn = findViewById(R.id.activity_comment_btn);


        mCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("What happened today ?");

                final EditText input = new EditText(MainActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }
    // Application is running
    @Override
    protected void onResume() {
        super.onResume();
        // Set the PagerAdapter to show fragments
        mVerticalViewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        mVerticalViewPager.setCurrentItem(3, true);
        System.out.println("MainActivity - onResume");
    }

    @Override
    protected void onStart(){
        super.onStart();
        System.out.println("MainActivity - onStart");
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
