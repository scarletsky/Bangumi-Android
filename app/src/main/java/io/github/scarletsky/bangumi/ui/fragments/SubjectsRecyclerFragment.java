package io.github.scarletsky.bangumi.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.adapters.CardRecyclerAdapter;
import io.github.scarletsky.bangumi.api.models.Calendar;
import io.github.scarletsky.bangumi.api.models.Subject;
import io.github.scarletsky.bangumi.events.LoadCalendarEvent;
import io.github.scarletsky.bangumi.ui.widget.MarginDecoration;
import io.github.scarletsky.bangumi.utils.BusProvider;

/**
 * Created by scarlex on 15-7-3.
 */
public class SubjectsRecyclerFragment extends Fragment {

    private Activity ctx;
    private List<Subject> data = new ArrayList<>();
    private int position;
    private CardRecyclerAdapter adapter;

    public static SubjectsRecyclerFragment newInstance(int position) {

        SubjectsRecyclerFragment mSubjectsRecyclerFragment = new SubjectsRecyclerFragment();

        Bundle args = new Bundle();
        args.putInt("ARG_POSITION", position);
        mSubjectsRecyclerFragment.setArguments(args);

        return mSubjectsRecyclerFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.ctx = activity;
        this.position = getArguments().getInt("ARG_POSITION");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);

        adapter = new CardRecyclerAdapter(ctx, data);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mRecyclerView.addItemDecoration(new MarginDecoration(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);

        return view;
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

    @Subscribe
    public void onLoadCalendarEvent(LoadCalendarEvent event) {
        Calendar mCalendar = event.getCalendars().get(position);
        this.data.clear();
        this.data.addAll(mCalendar.getItems());
        adapter.notifyDataSetChanged();
    }
}
