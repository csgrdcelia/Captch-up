package com.esgi.project.captchup.Level;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.esgi.project.captchup.Models.Level;
import com.esgi.project.captchup.Models.Prediction;
import com.esgi.project.captchup.R;

public class PredictionViewHolder {
    Prediction prediction;
    TextView tvPrecision;
    ProgressBar pbPrecision;

    public PredictionViewHolder(View itemView) {
        tvPrecision = (TextView) itemView.findViewById(R.id.tvPrecision);
        pbPrecision = (ProgressBar) itemView.findViewById(R.id.progressBar);
    }

    public void bind(Prediction prediction){
        if(prediction != null) {
            this.prediction = prediction;
            tvPrecision.setText(prediction.getPrecision() + "%");
            pbPrecision.setProgress(prediction.getPrecision().intValue());
        }
    }
}
