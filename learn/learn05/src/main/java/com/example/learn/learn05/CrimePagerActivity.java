package com.example.learn.learn05;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;
import java.util.UUID;

/**
 * Created by wangyue20 on 2017/2/4.
 */

public class CrimePagerActivity extends FragmentActivity {
    private static final String TAG = "CrimePagerActivity";

    private List<Crime> mCrimes;
    private ViewPager mViewPager;
    private UUID mCrimeId;

    private static final String EXTRA_KEY_CIMRE_ID =
            "com.example.learn.learn05.CrimePagerActivity.ExtraKeyCrimeId";

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_KEY_CIMRE_ID, crimeId);
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

        mCrimeId = (UUID) getIntent().getSerializableExtra(EXTRA_KEY_CIMRE_ID);
        Crime crime = CrimeLab.get(this).getCrime(mCrimeId);
        mViewPager.setCurrentItem(mCrimes.indexOf(crime));
    }
}
