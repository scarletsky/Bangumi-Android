package io.github.scarletsky.bangumi.ui.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.scarletsky.bangumi.BangumiApplication;
import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.adapters.CardRecyclerAdapter;
import io.github.scarletsky.bangumi.api.ApiManager;
import io.github.scarletsky.bangumi.api.models.UserCollection;
import io.github.scarletsky.bangumi.ui.widget.MarginDecoration;
import io.github.scarletsky.bangumi.utils.BusProvider;
import io.github.scarletsky.bangumi.utils.SessionManager;
import io.github.scarletsky.bangumi.utils.ToastManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by scarlex on 15-7-8.
 */
public class CollectionFragment extends BaseToolbarFragment {

    private static final String TAG = CollectionFragment.class.getSimpleName();
    private SessionManager session = BangumiApplication.getInstance().getSession();
    private CardRecyclerAdapter adapter;
    private List<UserCollection> data = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefresh;

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
        return inflater.inflate(R.layout.fragment_collection, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new CardRecyclerAdapter(getActivity(), data);
        adapter.setViewType(CardRecyclerAdapter.VIEW_TYPE_WITH_PROGRESS);
        RecyclerView mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_wrapper).findViewById(R.id.recycler);
        mRecyclerView.addItemDecoration(new MarginDecoration(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);

        mSwipeRefresh = (SwipeRefreshLayout) getView().findViewById(R.id.recycler_wrapper);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCollections();
            }
        });

        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                getCollections();
            }
        });
    }

    @Override
    protected void setToolbarTitle() {
        getToolbar().setTitle(getString(R.string.title_collection));
    }

    private void getCollections() {
        mSwipeRefresh.setRefreshing(true);

        ApiManager.getBangumiApi().getUserCollection(session.getUserId(), new Callback<List<UserCollection>>() {
            @Override
            public void success(List<UserCollection> userCollections, Response response) {
                data.clear();
                if (userCollections != null) {
                    data.addAll(userCollections);
                }
                adapter.notifyDataSetChanged();
                mSwipeRefresh.setRefreshing(false);
                ToastManager.show(getActivity(), getString(R.string.toast_get_collections));
            }

            @Override
            public void failure(RetrofitError error) {
                mSwipeRefresh.setRefreshing(false);
                ToastManager.show(getActivity(), getString(R.string.toast_network_error));
            }
        });
    }
}
