package com.esgi.project.captchup.Game;

import android.content.res.Configuration;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.esgi.project.captchup.Models.Prediction;
import com.esgi.project.captchup.R;

import java.text.DecimalFormat;

public class PredictionViewHolder {
    View v;
    Prediction prediction;
    TextView tvPrecision;
    ProgressBar pbPrecision;
    ImageView ivValid;

    public PredictionViewHolder(View itemView) {
        v = itemView;
        tvPrecision = (TextView) itemView.findViewById(R.id.tvPrecision);
        pbPrecision = (ProgressBar) itemView.findViewById(R.id.progressBar);
        ivValid = (ImageView) itemView.findViewById(R.id.validIV);
    }

    /**
     * Binds current prediction value to view
     * @param prediction
     */
    public void bind(Prediction prediction){
        if(prediction != null) {
            this.prediction = prediction;
            pbPrecision.setProgress((int)(prediction.getPrecision() * 100));
            update();
        }
    }

    public void update() {
        setTextViewText();
        setMedia();
        updateOrientationConf();
    }

    private void setTextViewText() {
        if(prediction.getFound())
            tvPrecision.setText(prediction.getValue());
        else
            tvPrecision.setText(new DecimalFormat("#.##").format (prediction.getPrecision() * 100) + "%");
    }

    private void setMedia() {
        if(prediction.getFound()) {
            ivValid.setVisibility(View.VISIBLE);
            pbPrecision.setVisibility(View.GONE);
        } else {
            ivValid.setVisibility(View.GONE);
            pbPrecision.setVisibility(View.VISIBLE);
        }
    }

    private void updateOrientationConf() {
        int orientation = v.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            tvPrecision.setVisibility(View.GONE);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT){
            tvPrecision.setVisibility(View.VISIBLE);
        }
    }

}
