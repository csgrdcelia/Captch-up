package com.esgi.project.captchup.Level;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.project.captchup.Models.Level;
import com.esgi.project.captchup.R;
import com.esgi.project.captchup.Utils.CacheImage;
import com.esgi.project.captchup.Utils.RecyclerViewClickListener;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

public class LevelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    Level level;
    private TextView textView;
    private ImageView imageView;
    private RecyclerViewClickListener listener;
    private Context context;

    public LevelViewHolder(View itemView, RecyclerViewClickListener listener, Context context) {
        super(itemView);
        this.listener = listener;
        itemView.setOnClickListener(this);
        textView = itemView.findViewById(R.id.text);
        imageView = itemView.findViewById(R.id.image);
        this.context = context;
    }

    public void bind(Level level){
        this.level = level;
        textView.setText(level.getAdvancement());

        new CacheImage(imageView, level.getImage(), context).run();
            //new CacheSaver(imageView, context).execute(new URL(level.getImage()));

        //TODO load from cache
        /*Picasso.get()
                .load(new CacheImage(level.getImage(), context).getPath())
                .placeholder(R.drawable.robot)
                .centerCrop().fit().into(imageView);*/
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, level);
    }
}
