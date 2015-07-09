package io.github.scarletsky.bangumi.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.adapters.EpAdapter;
import io.github.scarletsky.bangumi.api.models.Ep;
import io.github.scarletsky.bangumi.events.GetSubjectEpsEvent;
import io.github.scarletsky.bangumi.ui.widget.MarginDecoration;
import io.github.scarletsky.bangumi.utils.BusProvider;

/**
 * Created by scarlex on 15-7-9.
 */
public class EpsFragment extends Fragment {

    private static final String TAG = EpsFragment.class.getSimpleName();
    private List<Ep> data = new ArrayList<>();
    private EpAdapter adapter;

    public static EpsFragment newInstance() {
        return new EpsFragment();
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
        return inflater.inflate(R.layout.fragment_ep, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new EpAdapter(getActivity(), data);
        RecyclerView mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler);
        mRecyclerView.addItemDecoration(new MarginDecoration(getActivity()));
        mRecyclerView.setAdapter(adapter);

    }

    @Subscribe
    public void onGetSubjectEpsEvent(GetSubjectEpsEvent event) {
        this.data.addAll(event.getEps());
        this.adapter.notifyDataSetChanged();
    }
}
