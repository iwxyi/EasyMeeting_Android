package com.iwxyi.easymeeting.Fragments;

import android.annotation.SuppressLint;
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
import com.iwxyi.easymeeting.Globals.UserInfo;
import com.iwxyi.easymeeting.R;
import com.iwxyi.easymeeting.Fragments.dummy.LeaseContent;
import com.iwxyi.easymeeting.Fragments.dummy.LeaseContent.LeaseItem;
import com.iwxyi.easymeeting.Utils.ConnectUtil;

import java.util.Objects;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnLeaseListInteractionListener}
 * interface.
 */
public class LeasesFragment extends Fragment {

    private final int WHAT_REFRESH = 1;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnLeaseListInteractionListener mListener;
    private MyLeasesRecyclerViewAdapter adapter;

    public LeasesFragment() {
    }

    public void refreshLeases() {
        int user_id = UserInfo.user_id;
        ConnectUtil.Go(handler, WHAT_REFRESH, Paths.getNetpath("leases"), "user_id=" + user_id);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_REFRESH :
                    LeaseContent.addItemsFromString(msg.obj.toString());
                    adapter.setValues(LeaseContent.ITEMS);
                    adapter.notifyDataSetChanged();
                    App.setVal("count", LeaseContent.ITEMS.size());
            }
        }
    };

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static LeasesFragment newInstance(int columnCount) {
        LeasesFragment fragment = new LeasesFragment();
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
        View view = inflater.inflate(R.layout.fragment_leases_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new MyLeasesRecyclerViewAdapter(LeaseContent.ITEMS, mListener);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLeaseListInteractionListener) {
            mListener = (OnLeaseListInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLeaseListInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnLeaseListInteractionListener {
        // TODO: Update argument type and name
        void onLeaseListInteraction(LeaseItem item);
    }
}
