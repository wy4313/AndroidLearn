package com.example.learn.learn02;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;


import static android.content.pm.PackageManager.PERMISSION_GRANTED;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String TESTFILE = "testFile";
    private Fragment mFragPortrait;
    private Fragment mFragLandscape;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction mFragmentTransaction = getFragmentManager().beginTransaction();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        sharedPref.edit().clear().apply();

        filePathTest();

        DisplayMetrics dm = getResources().getDisplayMetrics();
        if (dm.heightPixels > dm.widthPixels) {
            if (mFragPortrait == null) {
                mFragPortrait = new Fragment1();
            }
            mFragmentTransaction.replace(R.id.main_layout, mFragPortrait);
            mFragmentTransaction.addToBackStack(null);
            mFragmentTransaction.commit();
        } else {
            if (mFragLandscape == null) {
                mFragLandscape = new Fragment2();
            }
            mFragmentTransaction.replace(R.id.main_layout, mFragLandscape);
            mFragmentTransaction.addToBackStack(null);
            mFragmentTransaction.commit();
        }
    }

    private void filePathTest() {
        File cacheDir = getCacheDir();
        File extCacheDir = getExternalCacheDir();
        File fileDir = getFilesDir();

        File externalStoragePublicDir = Environment.getExternalStoragePublicDirectory("mp4");
        Log.d(TAG, "filePathTest: externalStoragePublicDir:" + externalStoragePublicDir);


        File externalStorageDir = Environment.getExternalStorageDirectory();
        Log.d(TAG, "filePathTest: externalStorageDir:" + externalStorageDir);

        File rootDir = Environment.getRootDirectory();
        Log.d(TAG, "filePathTest: root dir:" + rootDir);


        Log.d(TAG, "onCreate: cachedir:" + cacheDir +
                " extCacheDir:" + extCacheDir +
                " fileDir:" + fileDir);

        if (PERMISSION_GRANTED != ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Log.e(TAG, "filePathTest: checkSelfPermission failed");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.e(TAG, "filePathTest: to request permissions");
                MainActivity.this.requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
            }
        } else {
            Log.e(TAG, "filePathTest: checkSelfPermission pause");
            //File testFile = new File(fileDir, TESTFILE);
            File testFile = new File(Environment.getExternalStorageDirectory(), "test.txt");
            if (testFile.exists()) {
                Log.d(TAG, "onCreate: file to string:" + testFile.toString());
                Log.d(TAG, "onCreate: file:" + testFile.toString() + " exists");
                String line;
                StringBuilder strBuilder = new StringBuilder();
                BufferedReader brd = null;
                try {
                    brd = new BufferedReader(new FileReader(testFile));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    assert brd != null;
                    while ((line = brd.readLine()) != null) {
                        strBuilder.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "onCreate: read buffer[" + strBuilder.toString() + "]");

            } else {
                Log.d(TAG, "onCreate: file not exist");
            }

            try {
                FileOutputStream outFileStream = openFileOutput(TESTFILE, Context.MODE_APPEND);
                outFileStream.write("BiLiBiLi".getBytes());
                outFileStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d(TAG, "onConfigurationChanged: ORIENTATION_LANDSCAPE" +
                    " newConfig.screenHeightDp:" + newConfig.screenHeightDp +
                    " newConfig.screenWidthDp:" + newConfig.screenWidthDp);
            if (mFragLandscape == null) {
                mFragLandscape = new Fragment2();
            }
            SharedPreferences sharedPerf = getPreferences(Context.MODE_PRIVATE);
            int value = sharedPerf.getInt(getString(R.string.shared_preferences_key_1), 103);
            Log.d(TAG, "onConfigurationChanged: value:" + value);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_layout, mFragLandscape);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d(TAG, "onConfigurationChanged: ORIENTATION_PORTRAIT" +
                    " newConfig.screenHeightDp:" + newConfig.screenHeightDp +
                    " newConfig.screenWidthDp:" + newConfig.screenWidthDp);
            if (mFragPortrait == null) {
                mFragPortrait = new Fragment1();
            }
            SharedPreferences sharedPerf = getPreferences(Context.MODE_PRIVATE);
            int value = sharedPerf.getInt(getString(R.string.shared_preferences_key_1), 103);
            Log.d(TAG, "onConfigurationChanged: value:" + value);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_layout, mFragPortrait);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e(TAG, "onRequestPermissionsResult: requestCode:" + requestCode);
        for (int i = 0; i < permissions.length; i++) {
            Log.e(TAG, "onRequestPermissionsResult: permission:" + permissions[i] + " results:" + grantResults[i]);
            if (0 == permissions[i].compareToIgnoreCase(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    && grantResults[i] == PERMISSION_GRANTED) {
                filePathTest();
            }
        }
    }
}