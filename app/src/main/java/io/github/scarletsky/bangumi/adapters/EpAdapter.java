package io.github.scarletsky.bangumi.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;

import java.util.List;

import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.api.models.Ep;
import io.github.scarletsky.bangumi.events.UpdateEpEvent;
import io.github.scarletsky.bangumi.utils.BusProvider;

/**
 * Created by scarlex on 15-7-9.
 */
public class EpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = EpAdapter.class.getSimpleName();
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final Ep ep = data.get(position);
        ViewHolder h = (ViewHolder) holder;
        h.mSort.setText(String.valueOf(ep.getSort()));

        switch (ep.getStatus()) {
            case AIR:
                h.mSort.setTextColor(ctx.getResources().getColor(android.R.color.white));
                h.mBox.setBackgroundResource(R.color.primary_light);
                h.mBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new BottomSheet.Builder((Activity) ctx)
                                .title(ep.getTitle())
                                .sheet(R.menu.menu_bottom_sheet)
                                .listener(new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Ep.WatchStatus ws;
                                        switch (which) {
                                            case R.id.queue:
                                                ws = Ep.WatchStatus.QUEUE;
                                                break;
                                            case R.id.watched:
                                                ws = Ep.WatchStatus.WATCHED;
                                                break;
                                            default:
                                                ws = Ep.WatchStatus.DROP;
                                                break;
                                        }

                                        BusProvider.getInstance().post(new UpdateEpEvent(ep.getId(), ws, position));
                                    }
                                }).show();
                    }
                });
                break;
            case NA:
            case TODAY:
                h.mSort.setTextColor(ctx.getResources().getColor(R.color.text_primary));
                h.mBox.setBackgroundResource(R.color.grey_300);
                h.mBox.setOnClickListener(null);
                break;
        }

        if (ep.getWatchStatus() == Ep.WatchStatus.WATCHED) {
            h.mBox.setBackgroundResource(R.color.primary);
        }

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
