package io.github.scarletsky.bangumi.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;

import java.util.List;

import io.github.scarletsky.bangumi.BangumiApplication;
import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.api.models.Ep;
import io.github.scarletsky.bangumi.events.UpdateEpEvent;
import io.github.scarletsky.bangumi.utils.BusProvider;
import io.github.scarletsky.bangumi.utils.SessionManager;
import io.github.scarletsky.bangumi.utils.ToastManager;

/**
 * Created by scarlex on 15-7-9.
 */
public class EpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = EpAdapter.class.getSimpleName();
    private SessionManager session = BangumiApplication.getInstance().getSession();
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
                final int menu_bottom_sheet;
                // set box style by watch status
                switch (ep.getWatchStatus()) {
                    case WISH:
                        h.mBox.setBackgroundResource(R.color.primary_darker);
                        h.mSort.setTextColor(ctx.getResources().getColor(android.R.color.white));
                        h.mSort.setPaintFlags(h.mSort.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                        menu_bottom_sheet = R.menu.menu_bottom_sheet_large;
                        break;
                    case WATCHED:
                        h.mBox.setBackgroundResource(R.color.primary);
                        h.mSort.setTextColor(ctx.getResources().getColor(android.R.color.white));
                        h.mSort.setPaintFlags(h.mSort.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                        menu_bottom_sheet = R.menu.menu_bottom_sheet_large;
                        break;
                    case DROP:
                        h.mBox.setBackgroundResource(R.color.grey_500);
                        h.mSort.setPaintFlags(h.mSort.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        menu_bottom_sheet = R.menu.menu_bottom_sheet_large;
                        break;
                    default:
                        h.mBox.setBackgroundResource(R.color.primary_light);
                        h.mSort.setPaintFlags(h.mSort.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                        h.mSort.setTextColor(ctx.getResources().getColor(android.R.color.white));
                        menu_bottom_sheet = R.menu.menu_bottom_sheet;

                }

                // set click listener
                h.mBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (session.getAuth().equals("")) {
                            ToastManager.show(ctx, ctx.getString(R.string.toast_please_login_first));
                            return;
                        }

                        new BottomSheet.Builder((Activity) ctx)
                                .title(ep.getTitle())
                                .sheet(menu_bottom_sheet)
                                .listener(new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        onEpMenuClick(ep, position, which);
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

    private void onEpMenuClick(Ep ep, int position, int which) {
        Ep.WatchStatus ws;
        switch (which) {
            case R.id.menu_wish:
                ws = Ep.WatchStatus.WISH;
                break;
            case R.id.menu_watched:
                ws = Ep.WatchStatus.WATCHED;
                break;
            case R.id.menu_drop:
                ws = Ep.WatchStatus.DROP;
                break;
            default:
                ws = Ep.WatchStatus.UNDO;
        }

        BusProvider.getInstance().post(new UpdateEpEvent(ep.getId(), ws, position));
    }
}
