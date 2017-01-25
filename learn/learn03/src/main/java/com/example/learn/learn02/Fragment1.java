package com.example.learn.learn02;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment1 extends Fragment {
    private static final String TAG = "fragment1";
    private static int mStartCount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        mStartCount = sharedPreferences.getInt(getString(R.string.shared_preferences_key_1), 0);
        Log.d(TAG, "onStart: mStartCount:" + mStartCount);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: " + this.toString());
        TextView textView = (TextView) getActivity().findViewById(R.id.frag1_text);
        int frag2Value = getActivity().getPreferences(Context.MODE_APPEND)
                .getInt(getString(R.string.shared_preferences_key_2), 0);
        textView.setText("fragment1:" + mStartCount + " fragement2:" + frag2Value);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: " + this.toString());
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putInt(getString(R.string.shared_preferences_key_1), (++mStartCount));
        edit.commit();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }
}