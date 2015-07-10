package io.github.scarletsky.bangumi.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.astuetz.PagerSlidingTabStrip;
import com.google.gson.Gson;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.github.scarletsky.bangumi.BangumiApplication;
import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.adapters.FragmentAdapter;
import io.github.scarletsky.bangumi.api.ApiManager;
import io.github.scarletsky.bangumi.api.models.Ep;
import io.github.scarletsky.bangumi.api.models.Subject;
import io.github.scarletsky.bangumi.api.models.SubjectEp;
import io.github.scarletsky.bangumi.api.models.SubjectProgress;
import io.github.scarletsky.bangumi.api.responses.BaseResponse;
import io.github.scarletsky.bangumi.events.GetSubjectDetailEvent;
import io.github.scarletsky.bangumi.events.GetSubjectEpsEvent;
import io.github.scarletsky.bangumi.events.UpdateEpEvent;
import io.github.scarletsky.bangumi.events.UpdatedEpEvent;
import io.github.scarletsky.bangumi.utils.BusProvider;
import io.github.scarletsky.bangumi.utils.SessionManager;
import io.github.scarletsky.bangumi.utils.ToastManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by scarlex on 15-7-9.
 */
public class ImageToolbarActivity extends AppCompatActivity {

    private static final String TAG = ImageToolbarActivity.class.getSimpleName();
    private MaterialDialog mProgressDialog;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private Toolbar mToolbar;
    private ImageView mCollapingToolbarImage;
    private Subject mSubject;
    private List<Ep> mEps = new ArrayList<>();
    private List<SubjectProgress.Ep> mSubjectProgressEps = new ArrayList<>();
    private SessionManager session = BangumiApplication.getInstance().getSession();

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
        initProgressDialog();

        ApiManager.getBangumiApi().getSubjectLarge(mSubject.getId(), new Callback<SubjectEp>() {
            @Override
            public void success(final SubjectEp subjectEp, Response response) {

                mSubject.setSummary(subjectEp.getSummary());
                mSubject.setType(subjectEp.getType());

                ApiManager.getBangumiApi().getSubjectProgress(
                        session.getUserId(),
                        session.getAuth(),
                        mSubject.getId(),
                        new Callback<SubjectProgress>() {
                    @Override
                    public void success(SubjectProgress subjectProgress, Response response) {

                        if (subjectProgress != null) {
                            mergeWithProgress(subjectEp.getEps(), subjectProgress.getEps());
                        }

                        mEps.addAll(subjectEp.getEps());
                        BusProvider.getInstance().post(new GetSubjectDetailEvent(mSubject));
                        BusProvider.getInstance().post(new GetSubjectEpsEvent(mEps));
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Subscribe
    public void onUpdateEpEvent(final UpdateEpEvent event) {
        showProgressDialog();
        ApiManager.getBangumiApi().updateEp(
                event.getEpId(),
                event.getStatus().getStr(),
                session.getAuth(),
                new Callback<BaseResponse>() {
            @Override
            public void success(BaseResponse baseResponse, Response response) {
                hideProgressDialog();
                System.out.println(baseResponse.getError());

                if (baseResponse.getCode() == 200) {
                    BusProvider.getInstance().post(new UpdatedEpEvent(event.getStatus().getWatchStatusId(), event.getPosition()));
                    ToastManager.show(ImageToolbarActivity.this, getString(R.string.toast_ep_update_successfully));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                hideProgressDialog();

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

    // set subject eps with progress
    private void mergeWithProgress(List<Ep> eps, List<SubjectProgress.Ep> epsProgress) {

        for (SubjectProgress.Ep epProgress : epsProgress) {

            for (Ep ep : eps) {

                if (epProgress.getEpId() == ep.getId()) {
                    ep.setWatchStatus(epProgress.getStatus().getId());
                    break;
                }

            }

        }

    }

    private void initProgressDialog() {
        mProgressDialog = new MaterialDialog
                .Builder(this)
                .content(getString(R.string.dialog_ep_updating))
                .progress(true, 0)
                .theme(Theme.LIGHT)
                .cancelable(false)
                .build();
    }

    private void showProgressDialog() {
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        mProgressDialog.hide();
    }
}
