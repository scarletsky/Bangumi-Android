package io.github.scarletsky.bangumi.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.ui.MainActivity;

/**
 * Created by scarlex on 15-7-2.
 */
public abstract class BaseToolbarFragment extends Fragment {

    private MainActivity mActivity;
    private Toolbar mToolbar;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (MainActivity) activity;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mToolbar = getToolbar();
        setToolbarTitle();

        mActivity.setSupportActionBar(mToolbar);
    }

    protected Toolbar getToolbar() {
        return (Toolbar) getView().findViewById(R.id.toolbar);
    }

    protected FragmentManager getFM() {
        return mActivity.getFM();
    }

    protected abstract void setToolbarTitle();
}
