package com.example.learn.learn04;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivityLearn04ShowAnswer extends AppCompatActivity {
    private static final String TAG = "Learn04ShowAnswer";
    private static final String CHEAT_SHARED_PREFERENCE_NAME = "cheat_shared_preference";
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private boolean mAnswer;
    private int mQuestionIndex;
    private Button mCleanPreferencesButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_learn04_show_answer);

        loadingViews();
    }

    private void loadingViews() {
        mAnswerTextView = (TextView) this.findViewById(R.id.answer_text);

        mShowAnswerButton = (Button) this.findViewById(R.id.show_answer_btn);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                mAnswer = intent.getBooleanExtra(EXTRA_QUESTION_ANSWER, false);
                mQuestionIndex = intent.getIntExtra(EXTRA_QUESTION_INDEX, 0);
                mAnswerTextView.setText("QuestionIndex:" + mQuestionIndex + " Answer is:" + mAnswer);
                setActivityAnswerShowResult();
            }
        });

        mCleanPreferencesButton = (Button) this.findViewById(R.id.clean_preferences);
        mCleanPreferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clear preferences");
                SharedPreferences sharedPreferences =
                        getSharedPreferences(CHEAT_SHARED_PREFERENCE_NAME, Context.MODE_APPEND);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().apply();
            }
        });

    }


    private static final String EXTRA_QUESTION_ANSWER =
            "com.example.learn.learn04.MainActivityLearn04ShowAnswer.ExtraQuestionAnswer";
    private static final String EXTRA_QUESTION_INDEX =
            "com.example.learn.learn04.MainActivityLearn04ShowAnswer.ExtraQuestionIndex";

    public static Intent newIntent(Context packageContext, int questionIndex, boolean answer) {
        Intent i = new Intent(packageContext, MainActivityLearn04ShowAnswer.class);
        i.putExtra(EXTRA_QUESTION_INDEX, questionIndex);
        i.putExtra(EXTRA_QUESTION_ANSWER, answer);
        return i;
    }

    private static final String EXTRA_ANSWER_HAS_SHOW =
            "com.example.learn.learn04.MainActivityLearn04ShowAnswer.ExtraAnswerHasShow";

    private void setActivityAnswerShowResult() {
//        Intent i = new Intent();
//        i.putExtra(EXTRA_ANSWER_HAS_SHOW, true);
//        setResult(Activity.RESULT_OK, i);

        SharedPreferences sharedPreferences = getSharedPreferences(CHEAT_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(String.format("Question[%d]_Cheat", mQuestionIndex), true);

        editor.apply();
    }

    public static String getCheatSharedPreferenceName() {
        return CHEAT_SHARED_PREFERENCE_NAME;
    }

    public static boolean isAnswerShown(SharedPreferences sharedPreferences, int questionIndex) {
//        return intent.getBooleanExtra(EXTRA_ANSWER_HAS_SHOW, false);S
        return sharedPreferences.getBoolean(String.format("Question[%d]_Cheat", questionIndex), false);
    }
}
