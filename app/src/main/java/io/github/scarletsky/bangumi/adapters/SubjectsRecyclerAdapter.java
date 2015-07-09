package io.github.scarletsky.bangumi.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.api.models.Subject;
import io.github.scarletsky.bangumi.api.models.UserCollection;
import io.github.scarletsky.bangumi.events.GetSubjectEvent;
import io.github.scarletsky.bangumi.utils.BusProvider;

/**
 * Created by scarlex on 15-7-3.
 */
public class SubjectsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_NORMAL = 1;
    public static final int VIEW_TYPE_WITH_PROGRESS = 2;

    private static final String TAG = SubjectsRecyclerAdapter.class.getSimpleName();

    private Context ctx;
    private List<?> data;
    private int viewType = 1;


    public SubjectsRecyclerAdapter(Context ctx, List<?> data) {
        this.ctx = ctx;
        this.data = data;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.adapter_card, parent, false);
        return viewType == VIEW_TYPE_WITH_PROGRESS ? new ViewHolderWithProgress(v) : new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder h = (ViewHolder) holder;
        final Subject mSubject;

        if (data.get(position) instanceof Subject) {

            mSubject = (Subject) data.get(position);

        } else if (data.get(position) instanceof UserCollection) {

            ViewHolderWithProgress hp = (ViewHolderWithProgress) holder;
            UserCollection mUserCollection = (UserCollection) data.get(position);
            mSubject = mUserCollection.getSubject();

            int currentProgress = mUserCollection.getEpStatus();
            String maxProgress = mSubject.getEps() == 0 ? "??" : String.valueOf(mSubject.getEps());

            hp.mProgressLabel.setText(currentProgress + "/" + maxProgress);
            hp.mProgressBar.setMax(mSubject.getEps());
            hp.mProgressBar.setProgress(mUserCollection.getEpStatus());
            hp.mProgressBar.getProgressDrawable().setColorFilter(ctx.getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_IN);

        } else {

            return;

        }

        h.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusProvider.getInstance().post(new GetSubjectEvent(mSubject));
            }
        });

        // set card title
        if (!mSubject.getNameCn().equals("")) {
            h.mCardTitle.setText(mSubject.getNameCn());
        } else {
            h.mCardTitle.setText(mSubject.getName());
        }

        // set card image
        Picasso
                .with(ctx)
                .load(mSubject.getImages().getLarge())
                .placeholder(R.drawable.ic_action_settings)
                .error(R.drawable.ic_action_menu)
                .fit()
                .centerCrop()
                .into(h.mCardImage);
    }

    @Override
    public int getItemCount() {
        return data.size();
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

    private static class ViewHolderWithProgress extends ViewHolder {

        public ProgressBar mProgressBar;
        public TextView mProgressLabel;

        public ViewHolderWithProgress(View v) {
            super(v);
            mProgressBar = (ProgressBar) v.findViewById(R.id.card_progress);
            mProgressLabel = (TextView) v.findViewById(R.id.card_progress_label);
            v.findViewById(R.id.card_progress_wrapper).setVisibility(View.VISIBLE);
        }
    }
}
