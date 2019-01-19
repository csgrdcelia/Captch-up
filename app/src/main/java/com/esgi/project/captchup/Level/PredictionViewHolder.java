package com.esgi.project.captchup.Level;

import android.graphics.Color;
import android.view.View;
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

    public PredictionViewHolder(View itemView) {
        v = itemView;
        tvPrecision = (TextView) itemView.findViewById(R.id.tvPrecision);
        pbPrecision = (ProgressBar) itemView.findViewById(R.id.progressBar);
    }

    public void bind(Prediction prediction){
        if(prediction != null) {
            if(prediction.getFound()) {
                v.setVisibility(View.GONE);
            }
            else {
                this.prediction = prediction;
                tvPrecision.setText(prediction.getPrecision() + "%");
                pbPrecision.setProgress(prediction.getPrecision().intValue());
            }
        }

    }

    public void setFound()
    {
        v.setVisibility(View.GONE);
    }

}
