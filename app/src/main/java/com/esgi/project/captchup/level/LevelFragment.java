package com.esgi.project.captchup.level;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esgi.project.captchup.game.GameFragment;
import com.esgi.project.captchup.MainActivity;
import com.esgi.project.captchup.models.Level;
import com.esgi.project.captchup.models.Prediction;
import com.esgi.project.captchup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LevelFragment extends Fragment {

    public enum LevelFragmentType {
        FINISHED,
        UNFINISHED
    }

    public static final String LEVEL_FRAGMENT_TYPE = "levelFragmentType";
    private LevelFragmentType levelFragmentType = LevelFragmentType.UNFINISHED;
    int index = 2;
    private RecyclerView recyclerView;
    private List<Level> levels;

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
        levelFragmentType = (LevelFragmentType)this.getArguments().get(LEVEL_FRAGMENT_TYPE);
        return inflater.inflate(R.layout.fragment_level, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setActionBarTitle();

        recyclerView = (RecyclerView)getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getView().getContext(),2)); // DISPLAY 2 PER ROW

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Level.LEVELS_ROOT + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid() );

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot parent) {
                levels = new ArrayList<>();
                for (DataSnapshot levelSnapshot : parent.getChildren()) {
                    Level level = levelSnapshot.getValue(Level.class);

                    for(DataSnapshot predictionSnapshot : levelSnapshot.child(Prediction.PREDICTIONS_ROOT).getChildren())
                        level.addPrediction(predictionSnapshot.getValue(Prediction.class));

                    if(levelFragmentType == LevelFragmentType.UNFINISHED && !level.isFinished())
                        levels.add(level);
                    else if (levelFragmentType == LevelFragmentType.FINISHED && level.isFinished())
                        levels.add(level);
                }

                LevelClickListener listener = (view, level) -> {
                    MainActivity activity = (MainActivity) view.getContext();
                    Fragment myFragment = GameFragment.newInstance((Level)level);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, myFragment).addToBackStack(null).commit();
                };
                recyclerView.setAdapter(new LevelAdapter(levels, listener, getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    /**
     * Sets the action bar title
     */
    private void setActionBarTitle() {
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (levelFragmentType == LevelFragmentType.FINISHED)
            actionBar.setTitle(getString(R.string.finished_levels));
        else
            actionBar.setTitle(getString(R.string.unfinished_levels));
    }

}
