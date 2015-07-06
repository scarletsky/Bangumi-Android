package io.github.scarletsky.bangumi.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.api.ApiManager;
import io.github.scarletsky.bangumi.api.models.Subject;
import io.github.scarletsky.bangumi.events.GetSubjectDetailEvent;
import io.github.scarletsky.bangumi.ui.MainActivity;
import io.github.scarletsky.bangumi.utils.BusProvider;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by scarlex on 15-7-4.
 */
public class ImageToolbarFragment extends Fragment {

    private static final String TAG = ImageToolbarFragment.class.getSimpleName();
    private static final String ARG_SID = "ARG_SUBJECT";
    private CollapsingToolbarLayout mCollapsingToolbar;
    private Toolbar mToolbar;
    private ImageView mCollapingToolbarImage;
    private MainActivity mActivity;
    private Subject mSubject;

    public ImageToolbarFragment(Subject subject) {
        this.mSubject = subject;
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_toolbar, container, false);

        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_image_toolbar_content, new SubjectDetailFragment())
                .commit();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mToolbar = (Toolbar) getView().findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbar = (CollapsingToolbarLayout) getView().findViewById(R.id.collapsing_toolbar_wrapper);
        mCollapingToolbarImage = (ImageView) getView().findViewById(R.id.collapsing_toolbar_image);

        mActivity.setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(ImageToolbarFragment.this).commit();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        setViewsForSubject();

        ApiManager.getBangumiApi().getSubject(mSubject.getId(), new Callback<Subject>() {
            @Override
            public void success(Subject subject, Response response) {
                mSubject.setSummary(subject.getSummary());
                mSubject.setEps(subject.getEps());
                mSubject.setType(subject.getType());
                BusProvider.getInstance().post(new GetSubjectDetailEvent(mSubject));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
    
    private void setViewsForSubject() {

        if (!mSubject.getNameCn().equals("")) {
            mCollapsingToolbar.setTitle(mSubject.getNameCn());
        } else {
            mCollapsingToolbar.setTitle(mSubject.getName());
        }

        Picasso
                .with(getActivity())
                .load(mSubject.getImages().getLarge())
                .fit()
                .centerCrop()
                .into(mCollapingToolbarImage);


    }

}
