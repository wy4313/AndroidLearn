package com.example.learn.learn05;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.Serializable;
import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {
    private static final String TAG = "CrimeActivity";


    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }

    private static final String EXTRA_CRIME_ID = "com.example.learn.learn05.CrimeActivity.CrimeUUID";

    public static Intent newIntent(Context packageContext, UUID uuid) {
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, uuid);
        Log.d(TAG, "newIntent: uuid:" + uuid);
        return intent;
    }

    public static UUID getIntentCrimeUUID(Intent intent) {
        return (UUID) intent.getSerializableExtra(EXTRA_CRIME_ID);
    }
}
