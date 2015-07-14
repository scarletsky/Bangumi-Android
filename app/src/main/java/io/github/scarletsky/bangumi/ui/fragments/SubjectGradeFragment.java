package io.github.scarletsky.bangumi.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.events.GetCollectionEvent;
import io.github.scarletsky.bangumi.utils.BusProvider;

/**
 * Created by scarlex on 15-7-14.
 */
public class SubjectGradeFragment extends Fragment{

    private LinearLayout mSubjectGradeWrapper;
    private TextView mSubjectStatus;
    private TextView mSubjectRating;
    private TextView mSubjectComment;
    private ProgressBar mProgressBar;


    public static SubjectGradeFragment newInstance() {
        return new SubjectGradeFragment();
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
        return inflater.inflate(R.layout.fragment_subject_grade, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mProgressBar = (ProgressBar) getView()
                .findViewById(R.id.subject_grade_progress_bar_wrapper)
                .findViewById(R.id.progress_bar);
        mSubjectGradeWrapper = (LinearLayout) getView().findViewById(R.id.subject_grade_wrapper);
        mSubjectStatus = (TextView) getView().findViewById(R.id.subject_status);
        mSubjectRating = (TextView) getView().findViewById(R.id.subject_rating);
        mSubjectComment = (TextView) getView().findViewById(R.id.subject_comment);
    }

    @Subscribe
    public void onGetCollectionEvent(GetCollectionEvent event) {

        if (event.getCollection() != null) {
            mSubjectStatus.setText(event.getCollection().getStatus().getName());
            mSubjectRating.setText(String.valueOf(event.getCollection().getRating()));
            mSubjectComment.setText(event.getCollection().getComment());
        }

        mProgressBar.setVisibility(View.GONE);
        mSubjectGradeWrapper.setVisibility(View.VISIBLE);

    }
}
