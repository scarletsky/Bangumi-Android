package io.github.scarletsky.bangumi.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import io.github.scarletsky.bangumi.BangumiApplication;
import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.ui.fragments.EpsFragment;
import io.github.scarletsky.bangumi.ui.fragments.SubjectDetailFragment;
import io.github.scarletsky.bangumi.ui.fragments.SubjectGradeFragment;
import io.github.scarletsky.bangumi.ui.fragments.CalendarPageFragment;
import io.github.scarletsky.bangumi.utils.SessionManager;

/**
 * Created by scarlex on 15-7-3.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = FragmentAdapter.class.getSimpleName();
    private String[] mTitles;
    private Context ctx;
    private SessionManager session = BangumiApplication.getInstance().getSession();


    public enum PagerType {
        CALENDAR,
        SUBJECT_DETAIL
    }

    public FragmentAdapter(Context ctx, FragmentManager fm, PagerType mPagerType) {
        super(fm);
        this.ctx = ctx;
        setTitles(mPagerType);
    }

    @Override
    public Fragment getItem(int position) {
        if (mTitles.length == 7) {
            return CalendarPageFragment.newInstance(position);
        } else {

            switch (position) {
                case 0:
                    return SubjectDetailFragment.newInsatnce();
                case 1:
                    return EpsFragment.newInstance();
                default:
                   return SubjectGradeFragment.newInstance();
            }
        }
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
            case SUBJECT_DETAIL:

                if (session.isLogin()) {
                    mTitles = new String[] {
                            ctx.getString(R.string.tabs_subject_info),
                            ctx.getString(R.string.tabs_subject_progress),
                            ctx.getString(R.string.tabs_subject_grade)
                    };
                } else {
                    mTitles = new String[] {
                            ctx.getString(R.string.tabs_subject_info),
                            ctx.getString(R.string.tabs_subject_progress)
                    };
                }
                break;
        }
    }



}
