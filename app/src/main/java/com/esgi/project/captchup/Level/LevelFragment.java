package com.esgi.project.captchup.Level;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.esgi.project.captchup.Game.GameFragment;
import com.esgi.project.captchup.MainActivity;
import com.esgi.project.captchup.Models.Level;
import com.esgi.project.captchup.R;
import com.esgi.project.captchup.Utils.RecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LevelFragment extends Fragment {

    public static final String LEVEL_FRAGMENT_TYPE = "levelFragmentType";

    public enum LevelFragmentType {
        FINISHED,
        UNFINISHED
    }

    int index = 2;
    private LevelFragmentType levelFragmentType = LevelFragmentType.UNFINISHED;

    private RecyclerView recyclerView;
    private List<Level> levels = new ArrayList<>();

    public LevelFragment() {
        // Required empty public constructor
    }

    public static LevelFragment newInstance(LevelFragmentType type) {

        Bundle args = new Bundle();
        LevelFragment fragment = new LevelFragment();
        args.putSerializable(LEVEL_FRAGMENT_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //TODO: make this code cleaner
        try {
            levelFragmentType = (LevelFragmentType)this.getArguments().get(LEVEL_FRAGMENT_TYPE);
        }catch (Exception e) { }
        return inflater.inflate(R.layout.fragment_level, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(levelFragmentType == LevelFragmentType.FINISHED)
            levels = Level.getFinishedLevels();
        else
            levels = Level.getUnfinishedLevels();


        recyclerView = (RecyclerView)getView().findViewById(R.id.recyclerView);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext())); // DISPLAY 1 PER ROW
        recyclerView.setLayoutManager(new GridLayoutManager(getView().getContext(),2)); // DISPLAY 2 PER ROW

        RecyclerViewClickListener listener = (view,levelId) -> {
            MainActivity activity = (MainActivity) view.getContext();
            Fragment myFragment = GameFragment.newInstance(levelId);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, myFragment).addToBackStack(null).commit();
        };
        recyclerView.setAdapter(new LevelAdapter(levels, listener));
    }
}
