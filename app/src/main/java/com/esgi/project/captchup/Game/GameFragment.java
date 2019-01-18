package com.esgi.project.captchup.Game;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.esgi.project.captchup.Models.Level;
import com.esgi.project.captchup.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    public static final String LEVEL_ID = "levelId";
    Level currentLevel;

    CardView prediction1, prediction2, prediction3;
    TextView answer;

    public static GameFragment newInstance(int levelId) {

        Bundle args = new Bundle();
        args.putInt(LEVEL_ID, levelId);
        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TODO: make this code cleaner
        try {
            int levelId = this.getArguments().getInt(LEVEL_ID);
            currentLevel = Level.getLevel(levelId);
        } catch (Exception e) { }
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prediction1 = getView().findViewById(R.id.prediction1);
        prediction2 = getView().findViewById(R.id.prediction2);
        prediction3 = getView().findViewById(R.id.prediction3);


    }
}
