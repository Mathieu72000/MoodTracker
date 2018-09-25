package com.corroy.mathieu.moodtracker.Fragments;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.corroy.mathieu.moodtracker.R;


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

    // When creating, retrieve this instance's mood and background color from its arguments
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMood = getArguments() != null ? getArguments().getString(MOOD) : "happy";
        mBackground = getArguments() != null ? getArguments().getString(BACKGROUND) : "light_sage";
    }

    // The user interface show its instance mood and background color
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_mood, container, false);
        mView.setBackgroundColor(getResources().getColor(getResources().getIdentifier(mBackground, "color", getActivity().getPackageName())));

        mImageView = mView.findViewById(R.id.mood_img);
        mImageView.setImageResource(getResources().getIdentifier("smiley_" + mMood, "drawable", getActivity().getPackageName()));
        return mView;
    }
}