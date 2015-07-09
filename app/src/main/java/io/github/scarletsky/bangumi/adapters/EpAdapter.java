package io.github.scarletsky.bangumi.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.api.models.Ep;

/**
 * Created by scarlex on 15-7-9.
 */
public class EpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context ctx;
    private List<Ep> data;

    public EpAdapter(Context ctx, List<Ep> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.adapter_ep, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Ep ep = data.get(position);
        ViewHolder h = (ViewHolder) holder;
        h.mSort.setText(String.valueOf(ep.getSort()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout mBox;
        public TextView mSort;

        public ViewHolder(View v) {
            super(v);
            mBox = (RelativeLayout) v.findViewById(R.id.box_ep);
            mSort = (TextView) v.findViewById(R.id.box_ep_sort);
        }
    }
}
