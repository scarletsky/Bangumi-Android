package io.github.scarletsky.bangumi.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.api.models.Calendar;
import io.github.scarletsky.bangumi.ui.fragments.RecyclerFragment;

/**
 * Created by scarlex on 15-7-3.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {

    private String[] mTitles;
    private Context ctx;

    public enum PagerType {
        CALENDAR
    }

    public FragmentAdapter(Context ctx, FragmentManager fm, PagerType mPagerType) {
        super(fm);
        this.ctx = ctx;
        setTitles(mPagerType);
    }

    @Override
    public Fragment getItem(int position) {
        return RecyclerFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    private void setTitles(PagerType mPagerType) {
        switch (mPagerType) {
            case CALENDAR:
                mTitles = new String[] {
                        ctx.getString(R.string.tabs_monday),
                        ctx.getString(R.string.tabs_tuesday),
                        ctx.getString(R.string.tabs_wednesday),
                        ctx.getString(R.string.tabs_thursday),
                        ctx.getString(R.string.tabs_friday),
                        ctx.getString(R.string.tabs_saturday),
                        ctx.getString(R.string.tabs_sunday)
                };
                break;
        }
    }



}
