package io.github.scarletsky.bangumi.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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

        ApiManager.getBangumiApi().getUserCollection(session.getUserId(), new Callback<List<UserCollection>>() {
            @Override
            public void success(List<UserCollection> userCollections, Response response) {
                data.addAll(userCollections);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    @Override
    protected void setToolbarTitle() {
        getToolbar().setTitle(getString(R.string.title_collection));
    }
}
