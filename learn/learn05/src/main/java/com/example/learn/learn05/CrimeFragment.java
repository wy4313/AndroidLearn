package com.example.learn.learn05;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.Date;
import java.util.UUID;

/**
 * Created by wangyue20 on 2017/1/29.
 */

public class CrimeFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "CrimeFragment";
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDetailDateButton;
    private CheckBox mSolvedCheckbox;

    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";
    private static final int REQUEST_CODE_DATE = 1;
    private static final int REQUEST_CODE_TIME = 2;
    private static final String BUNDLE_KEY_CRIME_ID =
            "com.example.learn.learn05.CrimeFragment.BundleKeyCrimeId";
    private Button mDetailTimeButton;

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_KEY_CRIME_ID, crimeId);

        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(bundle);
        return crimeFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        CrimeLab crimes = CrimeLab.get(getActivity());
//        UUID crimeUUID = CrimeActivity.getIntentCrimeUUID(getActivity().getIntent());
        UUID crimeUUID = (UUID) getArguments().getSerializable(BUNDLE_KEY_CRIME_ID);
        mCrime = crimes.getCrime(crimeUUID);
        Log.d(TAG, "onCreate: mCrime:" + mCrime + " UUID:" + crimeUUID + " crimeLab:" + crimes);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText) v.findViewById(R.id.fragment_crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged: ");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: ");
                mCrime.setTitle(s.toString());
                setFragmentResult(mCrime.getId());
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: ");

            }
        });

        mDetailDateButton = (Button) v.findViewById(R.id.fragment_crime_details_date);
        mDetailDateButton.setText(
                DateFormat.format(
                        getString(R.string.global_date_format),
                        mCrime.getDate())
        );
        mDetailDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fm = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_CODE_DATE);
                dialog.show(fm, DIALOG_DATE);
                setFragmentResult(mCrime.getId());
            }
        });

        mDetailTimeButton = (Button) v.findViewById(R.id.fragment_crime_details_time);
        mDetailTimeButton.setText(DateFormat.format(
                getString(R.string.global_date_time_format),
                mCrime.getDate())
        );
        mDetailTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fm = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_CODE_TIME);
                dialog.show(fm, DIALOG_TIME);
                setFragmentResult(mCrime.getId());
            }
        });

        mSolvedCheckbox = (CheckBox) v.findViewById(R.id.fragment_crime_check_box);
        mSolvedCheckbox.setChecked(mCrime.isSolved());
        mSolvedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged: ");
                mCrime.setSolved(isChecked);
                setFragmentResult(mCrime.getId());
            }
        });
        return v;
    }

    private void setFragmentResult(UUID uuid) {
//        getActivity().setResult(Activity.RESULT_OK, null);
        CrimePagerActivity activity = (CrimePagerActivity) getActivity();
        activity.updateFlushUUID(uuid);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK != resultCode) {
            return;
        }

        if (requestCode == REQUEST_CODE_DATE) {
            Date date = (Date) data.
                    getSerializableExtra(DatePickerFragment.EXTRA_KEY_DATE);

            /*keep time of day not change*/
            Date crimeDate = mCrime.getDate();
            date.setHours(crimeDate.getHours());
            date.setMinutes(crimeDate.getMinutes());

            mCrime.setDate(date);
            mDetailDateButton.setText(
                    DateFormat.format(
                            getString(R.string.global_date_format),
                            mCrime.getDate())
            );
        } else if (requestCode == REQUEST_CODE_TIME) {
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_KEY_TIME);
            Date crimeDate = mCrime.getDate();
            crimeDate.setHours(date.getHours());
            crimeDate.setMinutes(date.getMinutes());
            mCrime.setDate(crimeDate);
            mDetailTimeButton.setText(
                    DateFormat.format(
                            getString(R.string.global_date_time_format),
                            mCrime.getDate())
            );
        }

    }
}
