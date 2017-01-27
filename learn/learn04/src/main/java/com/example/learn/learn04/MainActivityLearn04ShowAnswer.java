package com.example.learn.learn04;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.learn.learn04.MainActivityLearn04.KEY_QUESTION_INDEX;

public class MainActivityLearn04ShowAnswer extends AppCompatActivity {

    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_learn04_show_answer);

        mAnswerTextView = (TextView) this.findViewById(R.id.answer_text);

        mShowAnswerButton = (Button) this.findViewById(R.id.show_answer_btn);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                boolean answer = intent.getBooleanExtra(KEY_QUESTION_INDEX, false);
                mAnswerTextView.setText("Answer is:" + answer);
            }
        });
    }
}
