package com.example.learn.learn04;

/**
 * Created by wangyue20 on 2017/1/25.
 */

public class Question {

    private int mStringResId;
    private boolean mAnswer;

    public int getStringResId() {
        return mStringResId;
    }

    public void setStringResId(int stringResId) {
        mStringResId = stringResId;
    }

    public boolean getAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean answer) {
        mAnswer = answer;
    }

    Question(int stringResId, boolean answer) {
        mStringResId = stringResId;
        mAnswer = answer;
    }
}
