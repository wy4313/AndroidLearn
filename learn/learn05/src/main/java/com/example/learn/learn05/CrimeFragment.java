package com.example.learn.learn05;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;

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


    private static final String BUNDLE_KEY_CRIME_ID = "com.example.learn.learn05.CrimeFragment.BundleKeyCrimeId";

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
                setFragmentResult();
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: ");

            }
        });

        mDetailDateButton = (Button) v.findViewById(R.id.fragment_crime_details_date);
        mDetailDateButton.setText(mCrime.getDate().toString());
        mDetailDateButton.setEnabled(false);

        mSolvedCheckbox = (CheckBox) v.findViewById(R.id.fragment_crime_check_box);
        mSolvedCheckbox.setChecked(mCrime.isSolved());
        mSolvedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged: ");
                mCrime.setSolved(isChecked);
                setFragmentResult();
            }
        });
        return v;
    }

    private void setFragmentResult() {
        getActivity().setResult(Activity.RESULT_OK, null);
    }
}
