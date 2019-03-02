package com.iwxyi.easymeeting.Fragments.Meetings;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iwxyi.easymeeting.Globals.App;
import com.iwxyi.easymeeting.Globals.Paths;
import com.iwxyi.easymeeting.Globals.User;
import com.iwxyi.easymeeting.R;
import com.iwxyi.easymeeting.Fragments.Meetings.MeetingsContent.MeetingItem;
import com.iwxyi.easymeeting.Utils.ConnectUtil;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnMeetingListInteractionListener}
 * interface.
 */
public class MeetingsFragment extends Fragment {

    private final int WHAT_REFRESH = 1;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnMeetingListInteractionListener mListener;
    private MyMeetingsRecyclerViewAdapter adapter;

    private ProgressDialog progressDialog;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MeetingsFragment() {
    }

    public void refreshMeetings() {
        int user_id = User.user_id;
        ConnectUtil.Go(WHAT_REFRESH, Paths.getNetpath("meetings"), "user_id=" + user_id, handler);
    }

    public void showProgressDialog() {
        progressDialog = ProgressDialog.show(getActivity(), "刷新列表", "正在获取您参加的会议", true, false);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case WHAT_REFRESH:
                    MeetingsContent.addItemsFromString(msg.obj.toString());
                    adapter.setValues(MeetingsContent.ITEMS);
                    adapter.notifyDataSetChanged();
                    App.setVal("count", MeetingsContent.ITEMS.size());
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    break;
            }
        }
    };

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MeetingsFragment newInstance(int columnCount) {
        MeetingsFragment fragment = new MeetingsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meetings_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new MyMeetingsRecyclerViewAdapter(MeetingsContent.ITEMS, mListener);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMeetingListInteractionListener) {
            mListener = (OnMeetingListInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMeetingListInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnMeetingListInteractionListener {
        // TODO: Update argument type and name
        void onMeetingListInteraction(MeetingItem item);
    }
}
