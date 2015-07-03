package io.github.scarletsky.bangumi.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import io.github.scarletsky.bangumi.R;

/**
 * Created by scarlex on 15-7-3.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = RecyclerAdapter.class.getSimpleName();

    private Context ctx;
    private String[] data;

    public RecyclerAdapter(Context ctx, String[] data) {
        this.ctx = ctx;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.adapter_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder h = (ViewHolder) holder;

        h.mCardTitle.setText(data[position]);
        Picasso
                .with(ctx)
                .load("https://www.baidu.com/img/bdlogo.png")
                .placeholder(R.drawable.ic_action_settings)
                .error(R.drawable.ic_action_menu)
                .into(h.mCardImage);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView mCard;
        public ImageView mCardImage;
        public TextView mCardTitle;

        public ViewHolder(View v) {
            super(v);
            mCard = (CardView) v.findViewById(R.id.card);
            mCardImage = (ImageView) v.findViewById(R.id.card_image);
            mCardTitle = (TextView) v.findViewById(R.id.card_title);
        }
    }
}
