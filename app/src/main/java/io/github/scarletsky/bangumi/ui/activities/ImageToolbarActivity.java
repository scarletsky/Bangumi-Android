package io.github.scarletsky.bangumi.ui.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.astuetz.PagerSlidingTabStrip;
import com.google.gson.Gson;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.github.scarletsky.bangumi.BangumiApplication;
import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.adapters.FragmentAdapter;
import io.github.scarletsky.bangumi.api.ApiManager;
import io.github.scarletsky.bangumi.api.models.Collection;
import io.github.scarletsky.bangumi.api.models.Ep;
import io.github.scarletsky.bangumi.api.models.Subject;
import io.github.scarletsky.bangumi.api.models.SubjectEp;
import io.github.scarletsky.bangumi.api.models.SubjectProgress;
import io.github.scarletsky.bangumi.api.responses.BaseResponse;
import io.github.scarletsky.bangumi.events.EditSubjectGradeEvent;
import io.github.scarletsky.bangumi.events.GetCollectionEvent;
import io.github.scarletsky.bangumi.events.GetSubjectDetailEvent;
import io.github.scarletsky.bangumi.events.GetSubjectEpsEvent;
import io.github.scarletsky.bangumi.events.UpdateSubjectGradeEvent;
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
public class ImageToolbarActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ImageToolbarActivity.class.getSimpleName();

    private MaterialDialog mProgressDialog;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private ImageView mCollapingToolbarImage;
    private FloatingActionButton mFabEdit;
    private FloatingActionButton mFabDone;

    private Subject mSubject;
    private Collection mCollection;
    private List<Ep> mEps = new ArrayList<>();
    private List<SubjectProgress.Ep> mSubjectProgressEps = new ArrayList<>();
    private SessionManager session = BangumiApplication.getInstance().getSession();
    private int currentPosition = 0;

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

        mFabEdit = (FloatingActionButton) findViewById(R.id.fab_edit);
        mFabDone = (FloatingActionButton) findViewById(R.id.fab_done);
        mFabEdit.setOnClickListener(this);
        mFabDone.setOnClickListener(this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.collapsing_toolbar);
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
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (currentPosition != position) {

                    currentPosition = position;

                    switch (position) {
                        case 0:
                            BusProvider.getInstance().post(new GetSubjectDetailEvent(mSubject));
                            hideFab();
                            break;
                        case 1:
                            BusProvider.getInstance().post(new GetSubjectEpsEvent(mEps));
                            hideFab();
                            break;
                        case 2:
                            BusProvider.getInstance().post(new GetCollectionEvent(mCollection));
                            onViewEditing(false);
                            break;
                    }

                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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

                                // if subject has no progress, the `subjectProgress` will be null;
                                // if not login, it will return error response,
                                // so `subjectProgress.getEps()` will be null
                                if (subjectProgress != null && subjectProgress.getEps() != null) {
                                    mergeWithProgress(subjectEp.getEps(), subjectProgress.getEps());
                                }

                                if (subjectEp.getEps() != null) {
                                    mEps.addAll(subjectEp.getEps());
                                }

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

        ApiManager.getBangumiApi().getSubjectCollection(mSubject.getId(), session.getAuth(), new Callback<Collection>() {
            @Override
            public void success(Collection collection, Response response) {
                if (collection.getLasttouch() != 0) {
                    mCollection = collection;
                    BusProvider.getInstance().post(new GetCollectionEvent(mCollection));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error.getMessage());
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

    @Subscribe
    public void onUpdateSubjectGradeEvent(UpdateSubjectGradeEvent event) {

        showProgressDialog();

        ApiManager.getBangumiApi().updateCollection(
                mSubject.getId(),
                event.getCollection().getStatus().getType(),
                event.getCollection().getRating(),
                event.getCollection().getCommentOriginal(),
                session.getAuth(),
                new Callback<Collection>() {
                    @Override
                    public void success(Collection collection, Response response) {
                        onViewEditing(false);
                        hideProgressDialog();

                        if (collection.getLasttouch() != 0) {
                            BusProvider.getInstance().post(new GetCollectionEvent(collection));
                            ToastManager.show(ImageToolbarActivity.this, getString(R.string.toast_collection_update_successfully));
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        hideProgressDialog();

                    }
                }
        );
    }

    private void setViewsForSubject() {

        if (!mSubject.getNameCn().equals("")) {
            mCollapsingToolbar.setTitle(mSubject.getNameCn());
        } else {
            mCollapsingToolbar.setTitle(mSubject.getName());
        }

        if (mSubject.getImages() != null) {
            Picasso
                    .with(this)
                    .load(mSubject.getImages().getLarge())
                    .fit()
                    .centerCrop()
                    .into(mCollapingToolbarImage);
        }


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
        mProgressDialog.dismiss();
    }

    private void clickEditFab() {
        BusProvider.getInstance().post(new EditSubjectGradeEvent());
        onViewEditing(true);
    }

    private void clickDoneFab() {
        EditSubjectGradeEvent event = new EditSubjectGradeEvent();
        event.setIsFinish(true);
        BusProvider.getInstance().post(event);
    }

    private void onViewEditing(boolean isOnEdit) {
        if (isOnEdit) {
            mFabDone.setVisibility(View.VISIBLE);
            mFabEdit.setVisibility(View.GONE);
        } else {
            mFabDone.setVisibility(View.GONE);
            mFabEdit.setVisibility(View.VISIBLE);
        }
    }

    private void hideFab() {
        mFabDone.setVisibility(View.GONE);
        mFabEdit.setVisibility(View.GONE);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fab_edit:
                clickEditFab();
                break;
            case R.id.fab_done:
                clickDoneFab();
                break;
        }
    }

}
