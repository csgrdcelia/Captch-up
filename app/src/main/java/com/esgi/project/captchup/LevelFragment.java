package com.esgi.project.captchup;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LevelFragment extends Fragment {

    public enum LevelType {
        FINISHED,
        UNFINISHED
    }

    int index = 2;

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
    public void onResume() {
        super.onResume();
        setTextView(String.valueOf(index));
    }

    public void setTextView(String text) {
        TextView tv = getView().findViewById(R.id.tv);
        tv.setText(text);
    }
}
