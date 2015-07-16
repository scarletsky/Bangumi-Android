package io.github.scarletsky.bangumi.ui.fragments;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.api.models.Subject;
import io.github.scarletsky.bangumi.events.GetSubjectDetailEvent;
import io.github.scarletsky.bangumi.ui.activities.WebviewActivity;
import io.github.scarletsky.bangumi.utils.BusProvider;

/**
 * Created by scarlex on 15-7-4.
 */
public class SubjectDetailFragment extends Fragment {

    private static final String TAG = SubjectDetailFragment.class.getSimpleName();
    private ProgressBar mProgressBar;
    private LinearLayout mSubjectDetailWrapper;
    private TextView mSubjectSummary;
    private TextView mSubjectType;
    private TextView mSubjectDate;
    private TextView mSubjectDateLabel;
    private TextView mSubjectWeekday;
    private TextView mSubjectWeekdayLabel;
    private TextView mSubjectWeb;

    public static SubjectDetailFragment newInsatnce() {
        return new SubjectDetailFragment();
    }

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
        return inflater.inflate(R.layout.fragment_subject_detail, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mProgressBar = (ProgressBar) getView()
                .findViewById(R.id.include_subject_detail_progress_bar)
                .findViewById(R.id.progress_bar);
        mSubjectDetailWrapper = (LinearLayout) getView().findViewById(R.id.subject_detail_wrapper);
        mSubjectSummary = (TextView) getView().findViewById(R.id.subject_summary);
        mSubjectType = (TextView) getView().findViewById(R.id.subject_type);
        mSubjectDate = (TextView) getView().findViewById(R.id.subject_date);
        mSubjectDateLabel = (TextView) getView().findViewById(R.id.subject_date_label);
        mSubjectWeekday = (TextView) getView().findViewById(R.id.subject_weekday);
        mSubjectWeekdayLabel = (TextView) getView().findViewById(R.id.subject_weekday_label);
        mSubjectWeb = (TextView) getView().findViewById(R.id.subject_web);

    }

    @Subscribe
    public void onGetSubjectDetailEvent(GetSubjectDetailEvent event) {

        final Subject mSubject = event.getSubject();

        mSubjectSummary.setText(mSubject.getSummary().trim());
        mSubjectDate.setText(mSubject.getAirDate());
        mSubjectWeekday.setText(mSubject.getAirWeekday());
        mSubjectType.setText(mSubject.getTypeDetail());
        mSubjectWeb.setText(mSubject.getUrl());
        mSubjectWeb.setPaintFlags(mSubjectWeb.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mSubjectWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), WebviewActivity.class);
                i.putExtra("subjectUrl", mSubject.getUrl());
                startActivity(i);
            }
        });

        if (mSubject.getAirDate().equals("")) {
            mSubjectDate.setVisibility(View.GONE);
            mSubjectDateLabel.setVisibility(View.GONE);
        }

        if (mSubject.getAirWeekday().equals("")) {
            mSubjectWeekday.setVisibility(View.GONE);
            mSubjectWeekdayLabel.setVisibility(View.GONE);
        }

        hideProgressBar();
        showSubjectDetail();
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void showSubjectDetail() {
        mSubjectDetailWrapper.setVisibility(View.VISIBLE);
    }
}
