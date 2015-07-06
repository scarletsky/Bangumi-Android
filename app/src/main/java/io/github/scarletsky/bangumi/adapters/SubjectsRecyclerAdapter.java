package io.github.scarletsky.bangumi.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.api.models.Subject;
import io.github.scarletsky.bangumi.events.GetSubjectEvent;
import io.github.scarletsky.bangumi.utils.BusProvider;

/**
 * Created by scarlex on 15-7-3.
 */
public class SubjectsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = SubjectsRecyclerAdapter.class.getSimpleName();

    private Context ctx;
    private List<Subject> data;

    public SubjectsRecyclerAdapter(Context ctx, List<Subject> data) {
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

        final Subject mSubject = data.get(position);

        h.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusProvider.getInstance().post(new GetSubjectEvent(mSubject));
            }
        });

        if (!mSubject.getNameCn().equals("")) {
            h.mCardTitle.setText(mSubject.getNameCn());
        } else {
            h.mCardTitle.setText(mSubject.getName());
        }

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
}
