package com.example.learn.learn05;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;


/**
 * Created by wangyue20 on 2017/2/3.
 */

public class CrimeListFragment extends Fragment {
    private static final String TAG = "CrimeListFragment";
    private static final int REQUEST_CODE_CRIME_ACTIVITY = 1;
    private static final int REQUEST_CODE_CRIME_PAGER_ACTIVITY = 2;

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private List<Crime> mCrimeList;
    private static boolean mSubtitleVisible;
    private MenuItem mSubTitleMenuOption;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: ");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        //updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        mSubTitleMenuOption = menu.findItem(R.id.menu_item_show_subtitle);
        Log.d(TAG, "onCreateOptionsMenu: mSubtitleVisible:" + mSubtitleVisible);
        updateSubtitle();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getContext()).addCrime(crime);

                Intent intent = CrimePagerActivity.newIntent(getContext(), crime.getId());
                startActivityForResult(intent, REQUEST_CODE_CRIME_PAGER_ACTIVITY);
                return true;
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                //getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            case android.R.id.home:
                Log.d(TAG, "onOptionsItemSelected: home has selected");
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        int count = CrimeLab.get(getContext()).getCrimes().size();
        String subTitle = getString(R.string.subtitle_format, count);
        Log.d(TAG, "updateSubtitle: mSubtitleVisible:" + mSubtitleVisible);
        if (false == mSubtitleVisible) {
            subTitle = null;
        }
        CrimeListActivity activity = (CrimeListActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subTitle);

        if (mSubtitleVisible) {
            mSubTitleMenuOption.setTitle(R.string.hide_subtitle);
        } else {
            mSubTitleMenuOption.setTitle(R.string.show_subtitle);
        }
    }

    private void updateUI() {
        if (mAdapter == null) {
            CrimeLab crimeLab = CrimeLab.get(getActivity());
            mCrimeList = crimeLab.getCrimes();
            mAdapter = new CrimeAdapter(mCrimeList);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public CheckBox mSolvedCheckBox;
        public TextView mTitleTextView;
        public TextView mDateTextView;
        private Crime mCrime;
        private int mPosition;


        public CrimeHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
            mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (mCrime != null) {
                        mCrime.setSolved(isChecked);
                    }
                }
            });
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
        }


        public void bindCrime(Crime crime, int position) {
            mCrime = crime;
            mPosition = position;
            mTitleTextView.setText(crime.getTitle());
            mDateTextView.setText(crime.getDate().toString());
            mSolvedCheckBox.setChecked(crime.isSolved());
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
            Intent intent = CrimePagerActivity.newIntent(getContext(), mCrime.getId());
            //startActivity(intent);
            Log.d(TAG, "onClick: position:" + mPosition);
            startActivityForResult(intent, REQUEST_CODE_CRIME_PAGER_ACTIVITY);
//            startActivityForResult(intent, REQUEST_CODE_CRIME_ACTIVITY);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CRIME_PAGER_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    List<UUID> uuids = (List<UUID>) data
                            .getSerializableExtra(CrimePagerActivity.EXTRA_KEY_FLUSH_CRIME_IDS);
                    Log.d(TAG, "onActivityResult: uuid list size:" + uuids.size());
                    for (UUID uuid : uuids) {
                        Log.d(TAG, "onActivityResult: uuid:" + uuid);
                        mAdapter.notifyItemChanged(
                                mCrimeList.indexOf(CrimeLab.get(getContext()).getCrime(uuid))
                        );
                    }
                }
            }
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_crime,
                    parent, false);

            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime, position);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }


}
