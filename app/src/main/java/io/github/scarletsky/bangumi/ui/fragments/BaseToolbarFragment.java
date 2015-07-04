package io.github.scarletsky.bangumi.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.events.ClickNavigateIconEvent;
import io.github.scarletsky.bangumi.events.SetToolbarEvent;
import io.github.scarletsky.bangumi.ui.MainActivity;
import io.github.scarletsky.bangumi.utils.BusProvider;

/**
 * Created by scarlex on 15-7-2.
 */
public class BaseToolbarFragment extends Fragment {

    private MainActivity mActivity;
    private Toolbar mToolbar;

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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base_toolbar, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mToolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        mActivity.setSupportActionBar(mToolbar);
    }

    @Subscribe
    public void onSetToolbarEvent(final SetToolbarEvent event) {

        if (event.getTitle() != null) {
            mToolbar.setTitle(event.getTitle());
        }

        if (event.getIconType() != null) {
            switch (event.getIconType()) {
                case MENU:
                    setupForMenu();
                    break;
                case BACK:
                    setupForBack();
                    break;
            }

        }

    }

    private void setupForMenu() {
        mToolbar.setNavigationIcon(R.drawable.ic_action_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusProvider.getInstance().post(new ClickNavigateIconEvent(ClickNavigateIconEvent.NavigateIconType.MENU));
            }
        });
    }

    private void setupForBack() {
        mToolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusProvider.getInstance().post(new ClickNavigateIconEvent(ClickNavigateIconEvent.NavigateIconType.BACK));
            }
        });
    }
}
