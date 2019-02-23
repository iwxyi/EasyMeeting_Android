package com.iwxyi.easymeeting.Fragments.Leases;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iwxyi.easymeeting.Fragments.Leases.LeasesFragment.OnLeaseListInteractionListener;
import com.iwxyi.easymeeting.Fragments.Leases.LeaseContent.LeaseItem;
import com.iwxyi.easymeeting.R;
import com.iwxyi.easymeeting.Utils.DateTimeUtil;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link LeaseItem} and makes a call to the
 * specified {@link LeasesFragment.OnLeaseListInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyLeasesRecyclerViewAdapter extends RecyclerView.Adapter<MyLeasesRecyclerViewAdapter.ViewHolder> {

    private List<LeaseItem> mValues;
    private final OnLeaseListInteractionListener mListener;

    public MyLeasesRecyclerViewAdapter(List<LeaseItem> items, OnLeaseListInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public void setValues(List<LeaseItem> items) {
        mValues = items;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_leases, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        LeaseItem item = mValues.get(position);
        holder.mItem = item;
        holder.mThemeView.setText(item.theme);
        holder.mUsageView.setText(item.usage);
        holder.mTimeView.setText(DateTimeUtil.longToString(item.start_time, "MM-dd HH:mm"));
        holder.mRoomView.setText(item.room_name);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onLeaseListInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public LeaseItem mItem;
        public final TextView mThemeView;
        public final TextView mUsageView;
        public final TextView mTimeView;
        public final TextView mRoomView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mThemeView = (TextView) view.findViewById(R.id.tv_theme);
            mUsageView = (TextView) view.findViewById(R.id.tv_usage);
            mTimeView = (TextView) view.findViewById(R.id.tv_time);
            mRoomView = (TextView) view.findViewById(R.id.tv_room);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mThemeView.getText() + "'";
        }
    }
}
