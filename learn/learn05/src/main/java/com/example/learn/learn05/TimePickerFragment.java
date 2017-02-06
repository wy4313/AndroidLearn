package com.example.learn.learn05;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

/**
 * Created by wangyue20 on 2017/2/6.
 */

public class TimePickerFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_time, null);

        TimePicker mTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_time_picker);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(mTimePicker);
        builder.setPositiveButton(android.R.string.ok, null);

        return builder.create();
    }
}

