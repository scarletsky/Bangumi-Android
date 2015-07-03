package io.github.scarletsky.bangumi.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import io.github.scarletsky.bangumi.R;

/**
 * Created by scarlex on 15-7-2.
 */
public class DrawerFragment extends BaseToolbarFragment implements OnNavigationItemSelectedListener {

    private static final String TAG = DrawerFragment.class.getSimpleName();

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer, container, false);
        CalendarFragment mCalendarFragment = new CalendarFragment();
        getFM().beginTransaction().replace(R.id.frame_base_toolbar_content, mCalendarFragment).commit();
        return view;
    }

    @Override
    public void setToolbarTitle() {
        getToolbar().setTitle(getString(R.string.app_name));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDrawerLayout = (DrawerLayout) getView().findViewById(R.id.drawer_main);
        mNavigationView = (NavigationView) getView().findViewById(R.id.drawer_nav);
        mNavigationView.setNavigationItemSelectedListener(this);

        getToolbar().setNavigationIcon(R.drawable.ic_action_menu);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.menu_settings:
                Log.d(TAG, "clickkkk settings");
                break;
            case R.id.menu_logout:
                Log.d(TAG, "clickkkk logout");
                break;
        }

        mDrawerLayout.closeDrawer(Gravity.LEFT);
        return false;
    }
}

