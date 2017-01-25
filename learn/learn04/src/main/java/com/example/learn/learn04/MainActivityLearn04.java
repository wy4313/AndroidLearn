package com.example.learn.learn04;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityLearn04 extends AppCompatActivity {

    private TextView mTextView;
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mPreviousButton;
    private ImageButton mNextButton;
    private int mQuestionIndex;

    private Question[] mQuestions = new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_learn04);

        loadingViews();

        mQuestionIndex = -1;
        updateQuestionText(true);
    }

    private void loadingViews() {
        mTextView = (TextView) this.findViewById(R.id.question_text);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestionText(true);
            }
        });


        mTrueButton = (Button) this.findViewById(R.id.true_btn);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) this.findViewById(R.id.false_btn);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mPreviousButton = (ImageButton) this.findViewById(R.id.previous_btn);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestionText(false);
            }
        });

        mNextButton = (ImageButton) this.findViewById(R.id.next_btn);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestionText(true);
            }
        });
    }

    private void checkAnswer(boolean answer) {
        int toastTextID = 0;
        if (answer == mQuestions[mQuestionIndex].getAnswer()) {
            toastTextID = R.string.correct_text;
        } else {
            toastTextID = R.string.incorrect_text;
        }
        Toast.makeText(getApplicationContext(), toastTextID, Toast.LENGTH_SHORT)
                .show();
    }

    private void updateQuestionText(boolean next) {
        if (next) {
            mQuestionIndex = ++mQuestionIndex % mQuestions.length;
        } else {
            if (mQuestionIndex == 0) {
                mQuestionIndex = mQuestions.length;
            }
            mQuestionIndex = --mQuestionIndex % mQuestions.length;
        }
        mTextView.setText(mQuestions[mQuestionIndex].getStringResId());
    }
}
