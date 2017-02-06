package com.example.learn.learn05;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by wangyue20 on 2017/2/6.
 */

public class TimePickerFragment extends DialogFragment {
    private static final String TAG = "TimePickerFragment";

    private static final String ARGS_KEY_DATE = "TimePickerFragment.ArgsKeyDate";
    public static final String EXTRA_KEY_TIME =
            "com.example.learn.learn05.TimePickerFragment.ExtraKeyTime";
    private TimePicker mTimePicker;

    public static TimePickerFragment newInstance(Date Date) {
        Bundle args = new Bundle();
        args.putSerializable(ARGS_KEY_DATE, Date);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_time, null);

        mTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_time_picker);
        mTimePicker.setIs24HourView(true);
        Bundle args = getArguments();
        if (args != null) {
            Date date = (Date) getArguments().getSerializable(ARGS_KEY_DATE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
//        mTimePicker.setHour(calendar.get(Calendar.HOUR));
//        mTimePicker.setMinute(calendar.get(Calendar.MINUTE));
            mTimePicker.setCurrentHour(calendar.get(Calendar.HOUR));
            mTimePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(mTimePicker);
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),
                                "hour:" + mTimePicker.getCurrentHour() + " minute:" + mTimePicker.getCurrentMinute(),
                                Toast.LENGTH_SHORT).show();
                        Date date = new Date();
                        date.setHours(mTimePicker.getCurrentHour());
                        date.setMinutes(mTimePicker.getCurrentMinute());
                        setResult(Activity.RESULT_OK, date);
                    }
                });

        return builder.create();
    }

    private void setResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            Log.d(TAG, "setResult: target fragment is null");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_KEY_TIME, date);

        getTargetFragment().onActivityResult(
                getTargetRequestCode(), resultCode, intent);
    }
}

