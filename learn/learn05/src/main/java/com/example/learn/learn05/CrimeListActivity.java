package com.example.learn.learn05;

import android.support.v4.app.Fragment;

/**
 * Created by wangyue20 on 2017/2/3.
 */

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
