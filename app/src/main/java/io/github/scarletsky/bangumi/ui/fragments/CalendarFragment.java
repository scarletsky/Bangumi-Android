package io.github.scarletsky.bangumi.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.adapters.FragmentAdapter;

/**
 * Created by scarlex on 15-7-2.
 */
public class CalendarFragment extends Fragment {

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

    }

}
