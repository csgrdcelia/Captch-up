package com.esgi.project.captchup.Level;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esgi.project.captchup.Models.Level;
import com.esgi.project.captchup.R;
import com.esgi.project.captchup.Utils.RecyclerViewClickListener;

import java.util.List;

public class LevelAdapter extends RecyclerView.Adapter<LevelViewHolder> {
    List<Level> list;
    private RecyclerViewClickListener listener;

    public LevelAdapter(List<Level> list, RecyclerViewClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LevelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_level,viewGroup,false);
        return new LevelViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelViewHolder levelViewHolder, int i) {
        Level level = list.get(i);
        levelViewHolder.bind(level);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
