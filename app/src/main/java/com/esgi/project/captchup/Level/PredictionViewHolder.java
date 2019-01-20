package com.esgi.project.captchup.Level;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.esgi.project.captchup.Models.Level;
import com.esgi.project.captchup.Models.Prediction;
import com.esgi.project.captchup.R;

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
            pbPrecision.setProgress(prediction.getPrecision().intValue());
            update();
        }

    }

    public void update() {
        setTextViewText();
        setMedia();
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

    private void setTextViewText() {
        if(prediction.getFound())
            tvPrecision.setText(prediction.getValue());
        else
            tvPrecision.setText(prediction.getPrecision() + "%");
    }

}