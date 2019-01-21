package com.esgi.project.captchup.Game;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.esgi.project.captchup.Level.PredictionViewHolder;
import com.esgi.project.captchup.Models.Level;
import com.esgi.project.captchup.Models.Prediction;
import com.esgi.project.captchup.R;
import com.github.jinatonic.confetti.CommonConfetti;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    public static final String LEVEL = "level";
    Level currentLevel;

    PredictionViewHolder[] predictionViewHolders;
    EditText answerEditText;
    ImageView imageView;

    public static GameFragment newInstance(Level level) {

        Bundle args = new Bundle();
        args.putSerializable(LEVEL, level);
        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TODO: make this code cleaner
        try {
            currentLevel = (Level) this.getArguments().getSerializable(LEVEL);
        } catch (Exception e) { }
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    /**
     *
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        answerEditText = getView().findViewById(R.id.answerEditText);
        imageView = getView().findViewById(R.id.picture);

        Picasso.get().load(currentLevel.getImage()).centerCrop().fit().into(imageView);
        bindPredictions();


        if(!currentLevel.isFinished()) {
            listenAnswer();
        }

    }

    /**
     * Binds predictions of current level to predictions cardviews
     */
    private void bindPredictions()
    {
        predictionViewHolders = new PredictionViewHolder[3];
        predictionViewHolders[0] = new PredictionViewHolder(getView().findViewById(R.id.prediction1));
        predictionViewHolders[0].bind(currentLevel.getPrediction(1));
        predictionViewHolders[1] = new PredictionViewHolder(getView().findViewById(R.id.prediction2));
        predictionViewHolders[1].bind(currentLevel.getPrediction(2));
        predictionViewHolders[2] = new PredictionViewHolder(getView().findViewById(R.id.prediction3));
        predictionViewHolders[2].bind(currentLevel.getPrediction(3));
    }

    /**
     * Listens the answer given by the user
     */
    private void listenAnswer()
    {
        answerEditText.setVisibility(View.VISIBLE);
        answerEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    checkAnswerValidity(answerEditText.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Checks if answer is correct
     */
    private void checkAnswerValidity(String answer) {
        int predictionNumber = currentLevel.getPredictionNumber(answer);

        if(predictionNumber == Prediction.ALREADY_FOUND) {
            Toast.makeText(getContext(), getString(R.string.answer_already_found), Toast.LENGTH_SHORT).show();
        } else if (predictionNumber == Prediction.WRONG_ANSWER) {
            Toast.makeText(getContext(), getString(R.string.try_again), Toast.LENGTH_SHORT).show();
        }
        else {
            predictionViewHolders[predictionNumber].update();
            answerEditText.setText("");
            Toast.makeText(getContext(), getString(R.string.congrats), Toast.LENGTH_SHORT).show();

            if(currentLevel.isFinished())
            {
                answerEditText.setVisibility(View.INVISIBLE);
                launchConfetti();
            }
        }
    }

    /**
     * Launch a shot of confetti
     * Library : Confetti by jinatonic
     */
    private void launchConfetti()
    {
        CommonConfetti.rainingConfetti((ViewGroup)getActivity().findViewById(R.id.mainFragment),
                new int[] { R.color.confetti_1,
                        R.color.confetti_2,
                        R.color.confetti_3,
                        R.color.confetti_4,
                        R.color.confetti_5 })
                .oneShot();
        //TODO: fix bug color
    }


}
