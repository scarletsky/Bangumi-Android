package io.github.scarletsky.bangumi.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.adapters.CardRecyclerAdapter;
import io.github.scarletsky.bangumi.api.ApiManager;
import io.github.scarletsky.bangumi.api.models.Subject;
import io.github.scarletsky.bangumi.api.responses.SearchResponse;
import io.github.scarletsky.bangumi.ui.activities.MainActivity;
import io.github.scarletsky.bangumi.ui.widget.MarginDecoration;
import io.github.scarletsky.bangumi.utils.ToastManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by scarlex on 15-7-12.
 */
public class SearchFragment extends Fragment {

    private static final String TAG = SearchFragment.class.getSimpleName();
    private SwipeRefreshLayout mSwipeRefresh;
    private CardRecyclerAdapter adapter;
    private List<Subject> data = new ArrayList<>();
    private MainActivity mActivity;



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        toolbar.setTitle(null);
        mActivity.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.getSupportFragmentManager().popBackStack();
            }
        });

        // setup search view
        final SearchView search = (SearchView) getView().findViewById(R.id.search);

        ImageView searchIcon = (ImageView) search.findViewById(R.id.search_mag_icon);
        searchIcon.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search.clearFocus();
                if (query.trim().length() > 0) {
                    searchSubjects(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // setup recyclerview
        adapter = new CardRecyclerAdapter(getActivity(), data);
        RecyclerView mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_wrapper).findViewById(R.id.recycler);
        mRecyclerView.addItemDecoration(new MarginDecoration(getActivity()));
        mRecyclerView.setAdapter(adapter);

        mSwipeRefresh = (SwipeRefreshLayout) getView().findViewById(R.id.recycler_wrapper);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefresh.setRefreshing(false);
            }
        });
    }

    private void searchSubjects(String query) {
        mSwipeRefresh.setRefreshing(true);

        ApiManager.getBangumiApi().search(query, new Callback<SearchResponse>() {
            @Override
            public void success(SearchResponse searchResponse, Response response) {
                mSwipeRefresh.setRefreshing(false);
                if (searchResponse.getList() == null) {
                    ToastManager.show(getActivity(), getString(R.string.toast_search_no_results));
                    return;
                }

                data.clear();
                data.addAll(searchResponse.getList());
                adapter.notifyDataSetChanged();
                ToastManager.show(
                        getActivity(),
                        String.format(
                            getString(R.string.toast_search_results_successfully),
                            searchResponse.getResults(),
                            searchResponse.getList().size()
                        ));
            }

            @Override
            public void failure(RetrofitError error) {
                mSwipeRefresh.setRefreshing(false);
                ToastManager.show(getActivity(), getString(R.string.toast_network_error));
            }
        });
    }
}
