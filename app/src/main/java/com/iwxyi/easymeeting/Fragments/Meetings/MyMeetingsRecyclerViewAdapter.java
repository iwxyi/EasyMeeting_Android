package com.iwxyi.easymeeting.Fragments.Meetings;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iwxyi.easymeeting.Fragments.Meetings.MeetingsFragment.OnMeetingListInteractionListener;
import com.iwxyi.easymeeting.Fragments.Meetings.MeetingsContent.MeetingItem;
import com.iwxyi.easymeeting.R;
import com.iwxyi.easymeeting.Utils.DateTimeUtil;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MeetingItem} and makes a call to the
 * specified {@link OnMeetingListInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMeetingsRecyclerViewAdapter extends RecyclerView.Adapter<MyMeetingsRecyclerViewAdapter.ViewHolder> {

    private List<MeetingItem> mValues;
    private final MeetingsFragment.OnMeetingListInteractionListener mListener;

    public MyMeetingsRecyclerViewAdapter(List<MeetingItem> items, OnMeetingListInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public void setValues(List<MeetingItem> items) {
        mValues = items;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meetings, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        MeetingItem item = mValues.get(position);
        holder.mItem = item;
        holder.mThemeView.setText(item.theme);
        holder.mUsageView.setText(item.usage);
        holder.mTimeView.setText(DateTimeUtil.timestampToString(item.start_time, "MM-dd HH:mm"));
        holder.mRoomView.setText(item.room_name);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onMeetingListInteraction(holder.mItem);
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
        public MeetingItem mItem;
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
