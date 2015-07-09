package io.github.scarletsky.bangumi.ui.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.google.gson.Gson;
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
import io.github.scarletsky.bangumi.utils.BusProvider;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by scarlex on 15-7-9.
 */
public class ImageToolbarActivity extends AppCompatActivity {

    private CollapsingToolbarLayout mCollapsingToolbar;
    private Toolbar mToolbar;
    private ImageView mCollapingToolbarImage;
    private Subject mSubject;
    private List<Ep> mEps = new ArrayList<>();

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_toolbar);

        String subjectStr = getIntent().getStringExtra("subject");
        mSubject = new Gson().fromJson(subjectStr, Subject.class);

        mToolbar = (Toolbar) findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_wrapper);
        mCollapingToolbarImage = (ImageView) findViewById(R.id.collapsing_toolbar_image);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FragmentAdapter pagerAdapter = new FragmentAdapter(
                this,
                getSupportFragmentManager(),
                FragmentAdapter.PagerType.SUBJECT_DETAIL);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
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
                .with(this)
                .load(mSubject.getImages().getLarge())
                .fit()
                .centerCrop()
                .into(mCollapingToolbarImage);


    }
}
