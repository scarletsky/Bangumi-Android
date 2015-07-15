package io.github.scarletsky.bangumi.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.events.ClickNavigateIconEvent;
import io.github.scarletsky.bangumi.ui.activities.MainActivity;
import io.github.scarletsky.bangumi.utils.BusProvider;

/**
 * Created by scarlex on 15-7-2.
 */
public abstract class BaseToolbarFragment extends Fragment {

    private static final String TAG = BaseToolbarFragment.class.getSimpleName();
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mToolbar = (Toolbar) getView().findViewById(R.id.toolbar_wrapper).findViewById(R.id.toolbar);
        setToolbarTitle();
        mActivity.setSupportActionBar(mToolbar);
        setupForMenu();
    }

    protected abstract void setToolbarTitle();

    protected Toolbar getToolbar() {
        return mToolbar;
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

}
