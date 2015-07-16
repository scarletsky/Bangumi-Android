package io.github.scarletsky.bangumi.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.scarletsky.bangumi.R;

/**
 * Created by scarlex on 15-7-16.
 */
public class AboutFragment extends BaseToolbarFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    protected void setToolbarTitle() {
        getToolbar().setTitle(getString(R.string.title_about));
    }
}
