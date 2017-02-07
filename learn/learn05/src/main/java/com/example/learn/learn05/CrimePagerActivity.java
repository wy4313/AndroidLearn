package com.example.learn.learn05;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by wangyue20 on 2017/2/4.
 */

public class CrimePagerActivity extends AppCompatActivity {
    private static final String TAG = "CrimePagerActivity";

    private List<Crime> mCrimes;
    private ViewPager mViewPager;
    private UUID mCrimeId;
    private List<UUID> mUUIDList;

    private static final String EXTRA_KEY_CRIME_ID =
            "com.example.learn.learn05.CrimePagerActivity.ExtraKeyCrimeId";
    public static final String EXTRA_KEY_FLUSH_CRIME_IDS =
            "com.example.learn.learn05.CrimePagerActivity.ExtraKeyFlushCrimeIds";

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_KEY_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();

        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        mCrimeId = (UUID) getIntent().getSerializableExtra(EXTRA_KEY_CRIME_ID);
        Crime crime = CrimeLab.get(this).getCrime(mCrimeId);
        mViewPager.setCurrentItem(mCrimes.indexOf(crime));
    }

    public void updateFlushUUID(UUID uuid) {
        if (mUUIDList == null) {
            mUUIDList = new ArrayList<>();
        }

        if (mUUIDList.contains(uuid)) {
            //uuid already in list
            return;
        }
        mUUIDList.add(uuid);
    }

    private void setReturnResult(int resultCode) {
        if (mUUIDList == null) {
            Log.d(TAG, "setResult: uuid list is null");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_KEY_FLUSH_CRIME_IDS, (Serializable) mUUIDList);
        setResult(resultCode, intent);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Log.d(TAG, "onBackPressed: !!!");
        if (mUUIDList != null && mUUIDList.size() != 0) {
            setReturnResult(Activity.RESULT_OK);
        }
        finish();
    }
}
