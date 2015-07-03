package io.github.scarletsky.bangumi.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.adapters.RecyclerAdapter;

/**
 * Created by scarlex on 15-7-3.
 */
public class RecyclerFragment extends Fragment {

    private Activity ctx;
    private String[] data;

    public static RecyclerFragment newInstance() {
        RecyclerFragment mRecyclerFragment = new RecyclerFragment();

        Bundle args = new Bundle();
        mRecyclerFragment.setArguments(args);

        return mRecyclerFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.ctx = activity;
        this.data = new String[] {
                "111",
                "222",
                "333",
                "444",
                "555",
                "666",
                "777",
                "888",
                "999",
                "111",
                "222",
                "333",
                "444",
                "555",
                "666",
                "777",
                "888",
                "999",
                "111",
                "222",
                "333",
                "444",
                "555",
                "666",
                "777",
                "888",
                "999",
                "111",
                "222",
                "333",
                "444",
                "555",
                "666",
                "777",
                "888",
                "999",
                "111",
                "222",
                "333",
                "444",
                "555",
                "666",
                "777",
                "888",
                "999",
                "111",
                "222",
                "333",
                "444",
                "555",
                "666",
                "777",
                "888",
                "999",
                "111",
                "222",
                "333",
                "444",
                "555",
                "666",
                "777",
                "888",
                "999",
                "111",
                "222",
                "333",
                "444",
                "555",
                "666",
                "777",
                "888",
                "999",
                "111",
                "222",
                "333",
                "444",
                "555",
                "666",
                "777",
                "888",
                "999",
                "111",
                "222",
                "333",
                "444",
                "555",
                "666",
                "777",
                "888",
                "999",
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);

        RecyclerAdapter adapter = new RecyclerAdapter(ctx, data);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(ctx, 2));
        mRecyclerView.setAdapter(adapter);

        return view;
    }
}
