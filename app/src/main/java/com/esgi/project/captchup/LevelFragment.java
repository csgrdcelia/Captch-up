package com.esgi.project.captchup;


import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LevelFragment extends Fragment {

    public enum LevelType {
        FINISHED,
        UNFINISHED
    }

    int index = 2;
    private RecyclerView recyclerView;
    private List<Level> levels = new ArrayList<>();

    public LevelFragment() {
        // Required empty public constructor
    }

    public static LevelFragment newInstance(int index) {

        Bundle args = new Bundle();
        LevelFragment fragment = new LevelFragment();
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            index = this.getArguments().getInt("index");
        }catch(Exception e) {

        }
        return inflater.inflate(R.layout.fragment_level, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addLevels();
        recyclerView = (RecyclerView)getView().findViewById(R.id.recyclerView);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext())); // DISPLAY 1 PER ROW
        recyclerView.setLayoutManager(new GridLayoutManager(getView().getContext(),2)); // DISPLAY 2 PER ROW
        recyclerView.setAdapter(new LevelAdapter(levels));
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void addLevels()
    {
        //TODO: supprimer ceci pour remplacer par la vraie récupération des niveaux
        Prediction prediction1 = new Prediction(1, "Robot", 80.0, true);
        Prediction prediction2 = new Prediction(2, "Jeu", 85.0, false);
        Prediction prediction3 = new Prediction(3, "Test", 90.0, true);

        List<Prediction> predictions = new ArrayList<>();
        predictions.add(prediction1);
        predictions.add(prediction2);
        predictions.add(prediction3);

        Level level = new Level(1, predictions, "url");
        levels.add(level);

    }
}
