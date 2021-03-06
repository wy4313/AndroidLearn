package com.example.learn.learn04;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityLearn04 extends AppCompatActivity {
    private static final String TAG = "MainActivityLearn04";

    public static final String KEY_QUESTION_INDEX =
            "com.example.learn.learn04.MainActivityLearn04.BundleKeyQuestionIndex";

    private static final int REQUEST_CODE_CHEAT = 1;


    private TextView mTextView;
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mPreviousButton;
    private ImageButton mNextButton;
    private int mQuestionIndex;
    private Button mCheatButton;
    private boolean mIsCheater;
    private SharedPreferences mSharedPreference;

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

        if (null != savedInstanceState) {
            mQuestionIndex = savedInstanceState.getInt(KEY_QUESTION_INDEX, 0);
            mQuestionIndex--;
        } else {
            mQuestionIndex = -1;
        }
        updateQuestionText(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Toast.makeText(getApplicationContext(),
                String.format("save state index:%d", mQuestionIndex),
                Toast.LENGTH_SHORT).show();
        outState.putInt(KEY_QUESTION_INDEX, mQuestionIndex);
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
                mIsCheater = false;
                updateQuestionText(false);
            }
        });

        mNextButton = (ImageButton) this.findViewById(R.id.next_btn);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsCheater = false;
                updateQuestionText(true);
            }
        });

        mCheatButton = (Button) this.findViewById(R.id.cheat_btn);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivityLearn04ShowAnswer.newIntent(
                        MainActivityLearn04.this,
                        mQuestionIndex,
                        mQuestions[mQuestionIndex].getAnswer());
                mIsCheater = false;
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult requestCode:" + requestCode + " resultCode:" + resultCode);
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (resultCode == Activity.RESULT_OK) {
                if (null == data) {
                    Log.d(TAG, "onActivityResult: data is null");
                    return;
                }
                //mIsCheater = MainActivityLearn04ShowAnswer.isAnswerShown(data);
            }
        } else {
            Log.d(TAG, "onActivityResult: unknown requestCode:" + requestCode);
        }
        Log.d(TAG, "onActivityResult: mIsCheater:" + mIsCheater);
    }


    private void checkAnswer(boolean answer) {
        int toastTextID = 0;
        if (mSharedPreference == null) {
            mSharedPreference = getSharedPreferences(
                    MainActivityLearn04ShowAnswer.getCheatSharedPreferenceName(),
                    Context.MODE_APPEND);
        }
        if (MainActivityLearn04ShowAnswer.isAnswerShown(mSharedPreference, mQuestionIndex)) {
            Toast.makeText(getApplicationContext(), R.string.cheat_wrong_text, Toast.LENGTH_SHORT)
                    .show();
            return;
        }

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
        Toast.makeText(getApplicationContext(),
                String.format("show question index:%d", mQuestionIndex),
                Toast.LENGTH_SHORT).show();
        mTextView.setText(mQuestions[mQuestionIndex].getStringResId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }
}
