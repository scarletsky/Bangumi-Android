package io.github.scarletsky.bangumi.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Slider;
import com.squareup.otto.Subscribe;

import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.api.models.Collection;
import io.github.scarletsky.bangumi.events.EditSubjectGradeEvent;
import io.github.scarletsky.bangumi.events.GetCollectionEvent;
import io.github.scarletsky.bangumi.events.UpdateSubjectGradeEvent;
import io.github.scarletsky.bangumi.utils.BusProvider;
import io.github.scarletsky.bangumi.utils.ToastManager;

/**
 * Created by scarlex on 15-7-14.
 */
public class SubjectGradeFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private static final String TAG = SubjectGradeFragment.class.getSimpleName();

    private LinearLayout mSubjectGradeWrapper;
    private TextView mSubjectStatus;
    private TextView mSubjectRating;
    private TextView mSubjectComment;
    private ProgressBar mProgressBar;
    private RadioGroup mSubjectStatusField;
    private Slider mSubjectRatingField;
    private MaterialEditText mSubjectCommentField;

    private Collection mCollection = new Collection();

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
        mSubjectStatusField = (RadioGroup) getView().findViewById(R.id.subject_status_field);
        mSubjectRatingField = (Slider) getView().findViewById(R.id.subject_rating_field);
        mSubjectCommentField = (MaterialEditText) getView().findViewById(R.id.subject_comment_field);

        mSubjectStatusField.setOnCheckedChangeListener(this);
    }

    @Subscribe
    public void onGetCollectionEvent(GetCollectionEvent event) {

        if (event.getCollection() != null) {
            mCollection = event.getCollection();

            String statusName = Collection.WatchStatus.getById(mCollection.getStatus().getId()).getName();
            mSubjectStatus.setText(statusName);
            mSubjectRating.setText(String.valueOf(mCollection.getRating()));
            mSubjectComment.setText(mCollection.getCommentDetail());

            hideEditFields();
        }

        mProgressBar.setVisibility(View.GONE);
        mSubjectGradeWrapper.setVisibility(View.VISIBLE);
    }

    @Subscribe
    public void onEditSubjectGradeEvent(EditSubjectGradeEvent event) {

        if (event.isFinish()) {

            if (isInputsValid()) {
                mCollection.setRating(mSubjectRatingField.getValue());
                mCollection.setComment(mSubjectCommentField.getText().toString());

                BusProvider.getInstance().post(new UpdateSubjectGradeEvent(mCollection));
            }
        } else {
            showEditFields();
        }
    }

    private void hideEditFields() {
        mSubjectStatus.setVisibility(View.VISIBLE);
        mSubjectRating.setVisibility(View.VISIBLE);
        mSubjectComment.setVisibility(View.VISIBLE);
        mSubjectStatusField.setVisibility(View.GONE);
        mSubjectRatingField.setVisibility(View.GONE);
        mSubjectCommentField.setVisibility(View.GONE);
    }

    private void showEditFields() {
        setupEditFields();
        mSubjectStatus.setVisibility(View.GONE);
        mSubjectRating.setVisibility(View.GONE);
        mSubjectComment.setVisibility(View.GONE);
        mSubjectStatusField.setVisibility(View.VISIBLE);
        mSubjectRatingField.setVisibility(View.VISIBLE);
        mSubjectCommentField.setVisibility(View.VISIBLE);
    }

    private void setupEditFields() {
        if (mCollection != null) {

            if (mCollection.getStatus().getId() != 0) {
                ((RadioButton) mSubjectStatusField.getChildAt(mCollection.getStatus().getId() - 1)).setChecked(true);
            }

            mSubjectRatingField.setValue(mCollection.getRating(), false);
            mSubjectCommentField.setText(mCollection.getComment());
        }
    }

    private boolean isInputsValid() {
        boolean flag = true;

        if (mCollection.getStatus().getId() == 0) {
            ToastManager.show(getActivity(), getString(R.string.toast_please_choose_collection_status));
            flag = false;
        }

        return flag;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.subject_status_wish:
                mCollection.getStatus().setId(Collection.WatchStatus.WISH.getId());
                mCollection.getStatus().setType(Collection.WatchStatus.WISH.getType());
                break;
            case R.id.subject_status_watched:
                mCollection.getStatus().setId(Collection.WatchStatus.WATCHED.getId());
                mCollection.getStatus().setType(Collection.WatchStatus.WATCHED.getType());
                break;
            case R.id.subject_status_watching:
                mCollection.getStatus().setId(Collection.WatchStatus.WATCHING.getId());
                mCollection.getStatus().setType(Collection.WatchStatus.WATCHING.getType());
                break;
            case R.id.subject_status_on_hold:
                mCollection.getStatus().setId(Collection.WatchStatus.ON_HOLD.getId());
                mCollection.getStatus().setType(Collection.WatchStatus.ON_HOLD.getType());
                break;
            case R.id.subject_status_drop:
                mCollection.getStatus().setId(Collection.WatchStatus.DROP.getId());
                mCollection.getStatus().setType(Collection.WatchStatus.DROP.getType());
                break;
        }
    }
}
