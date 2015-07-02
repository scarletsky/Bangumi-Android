package io.github.scarletsky.bangumi.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drawer, container, false);
    }

    @Override
    public void setToolbarTitle() {
        getToolbar().setTitle(getString(R.string.app_name));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final DrawerLayout mDrawerLayout = (DrawerLayout) getView().findViewById(R.id.drawer_main);

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
                break;
            case R.id.menu_logout:
                break;
        }

        return false;
    }
}

