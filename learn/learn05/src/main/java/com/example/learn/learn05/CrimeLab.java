package com.example.learn.learn05;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by wangyue20 on 2017/2/3.
 */

public class CrimeLab {
    private static final String TAG = "CrimeLab";

    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        Log.v(TAG, "get: sCrimeLab:" + sCrimeLab);
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }
    }

    public void addCrime(Crime c) {
        mCrimes.add(c);
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (0 == crime.getId().compareTo(id)) {
                Log.v(TAG, "getCrime: find!!!");
                return crime;
            }
        }
        Log.d(TAG, "getCrime: null");
        return null;
    }


}
