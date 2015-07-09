package io.github.scarletsky.bangumi.ui.fragments;

import android.app.Activity;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.astuetz.PagerSlidingTabStrip;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.adapters.FragmentAdapter;
import io.github.scarletsky.bangumi.api.ApiManager;
import io.github.scarletsky.bangumi.api.models.Ep;
import io.github.scarletsky.bangumi.api.models.Subject;
import io.github.scarletsky.bangumi.api.models.SubjectEp;
import io.github.scarletsky.bangumi.events.GetSubjectDetailEvent;
import io.github.scarletsky.bangumi.events.GetSubjectEpsEvent;
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
    private CollapsingToolbarLayout mCollapsingToolbar;
    private Toolbar mToolbar;
    private ImageView mCollapingToolbarImage;
    private MainActivity mActivity;
    private Subject mSubject;
    private List<Ep> mEps = new ArrayList<>();

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
        return inflater.inflate(R.layout.fragment_image_toolbar, container, false);
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

        FragmentAdapter pagerAdapter = new FragmentAdapter(
                getActivity(),
                getActivity().getSupportFragmentManager(),
                FragmentAdapter.PagerType.SUBJECT_DETAIL);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) getView().findViewById(R.id.tabs);
        ViewPager pager = (ViewPager) getView().findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);
        tabs.setViewPager(pager);

        setViewsForSubject();

        ApiManager.getBangumiApi().getSubjectLarge(mSubject.getId(), new Callback<SubjectEp>() {
            @Override
            public void success(SubjectEp subjectEp, Response response) {

                mSubject.setSummary(subjectEp.getSummary());
                mSubject.setType(subjectEp.getType());
                mEps.addAll(subjectEp.getEps());
                BusProvider.getInstance().post(new GetSubjectDetailEvent(mSubject));
                BusProvider.getInstance().post(new GetSubjectEpsEvent(mEps));

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
