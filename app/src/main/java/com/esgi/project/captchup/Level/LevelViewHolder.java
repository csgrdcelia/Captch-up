package com.esgi.project.captchup.Level;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.project.captchup.Models.Level;
import com.esgi.project.captchup.R;

public class LevelViewHolder extends RecyclerView.ViewHolder {
    private TextView textViewView;
    private ImageView imageView;

    public LevelViewHolder(View itemView) {
        super(itemView);

        textViewView = (TextView) itemView.findViewById(R.id.text);
        imageView = (ImageView) itemView.findViewById(R.id.image);
    }

    public void bind(Level level){
        textViewView.setText(level.getAdvancement());
        imageView.setImageResource(R.drawable.robot);
    }
}
