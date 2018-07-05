package com.corroy.mathieu.moodtracker.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;


public class MoodFragment extends Fragment {

    private static final String MOOD = "mood";
    private static final String BACKGROUND = "background";

    private String mMood;
    private String mBackground;
    private View mView;
    private ImageView mImageView;

    // New instance of MoodFragment for the mood and the background color
    public static MoodFragment newInstance(String mood, String background) {
        MoodFragment moodFragment = new MoodFragment();

        // New Bundle for supply mood and background as arguments
        Bundle bdl = new Bundle();
        bdl.putString(MOOD, mood);
        bdl.putString(BACKGROUND, background);
        moodFragment.setArguments(bdl);
        return moodFragment;
    }
}