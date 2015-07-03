package io.github.scarletsky.bangumi.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.adapters.FragmentAdapter;
import io.github.scarletsky.bangumi.api.ApiManager;
import io.github.scarletsky.bangumi.api.models.Calendar;
import io.github.scarletsky.bangumi.events.LoadCalendarEvent;
import io.github.scarletsky.bangumi.utils.BusProvider;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by scarlex on 15-7-2.
 */
public class CalendarFragment extends Fragment {

    private List<Calendar> mCalendars;

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentAdapter pagerAdapter = new FragmentAdapter(
                getActivity(),
                getActivity().getSupportFragmentManager(),
                FragmentAdapter.PagerType.CALENDAR);

        TabLayout tabs = (TabLayout) getView().findViewById(R.id.tabs);
        ViewPager pager = (ViewPager) getView().findViewById(R.id.pager);

        pager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(pager);

        tabs.setTabsFromPagerAdapter(pagerAdapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mCalendars != null) {
                    BusProvider.getInstance().post(new LoadCalendarEvent(mCalendars));
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ApiManager.getBangumiApi().listCalendar(new Callback<List<Calendar>>() {
            @Override
            public void success(List<Calendar> calendars, Response response) {
                mCalendars = calendars;
                BusProvider.getInstance().post(new LoadCalendarEvent(calendars));
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }
}
