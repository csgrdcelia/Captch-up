package com.esgi.project.captchup.Level;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.project.captchup.Models.Level;
import com.esgi.project.captchup.R;
import com.esgi.project.captchup.Utils.RecyclerViewClickListener;
import com.squareup.picasso.Picasso;

public class LevelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    Level level;
    private TextView textView;
    private ImageView imageView;
    private RecyclerViewClickListener listener;

    public LevelViewHolder(View itemView, RecyclerViewClickListener listener) {
        super(itemView);
        this.listener = listener;
        itemView.setOnClickListener(this);
        textView = (TextView) itemView.findViewById(R.id.text);
        imageView = (ImageView) itemView.findViewById(R.id.image);
    }

    public void bind(Level level){
        this.level = level;
        //textViewView.setText(level.getAdvancement());
        Picasso.get().load(level.getImage()).centerCrop().fit().into(imageView);
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, level.getId());
    }
}
