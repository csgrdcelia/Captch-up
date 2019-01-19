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

    /**
     * Binds current prediction value to view
     * @param prediction
     */
    public void bind(Prediction prediction){
        if(prediction != null) {
            this.prediction = prediction;
            pbPrecision.setProgress(prediction.getPrecision().intValue());
            setTextViewText();
        }

    }

    public void setTextViewText() {
        if(prediction.getFound())
            tvPrecision.setText(prediction.getValue());
        else
            tvPrecision.setText(prediction.getPrecision() + "%");
    }

    public void setIconImage()
    {
        //TODO: add icon "found" or "question mark" and adapt it there
    }

}
