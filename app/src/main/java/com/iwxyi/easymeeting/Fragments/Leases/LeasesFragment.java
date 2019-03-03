package com.iwxyi.easymeeting.Fragments.Leases;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iwxyi.easymeeting.Globals.App;
import com.iwxyi.easymeeting.Globals.Paths;
import com.iwxyi.easymeeting.Globals.User;
import com.iwxyi.easymeeting.R;
import com.iwxyi.easymeeting.Fragments.Leases.LeaseContent.LeaseItem;
import com.iwxyi.easymeeting.Utils.ConnectUtil;
import com.iwxyi.easymeeting.Utils.StringCallback;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnLeaseListInteractionListener}
 * interface.
 */
public class LeasesFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnLeaseListInteractionListener mListener;
    private MyLeasesRecyclerViewAdapter adapter;

    private ProgressDialog progressDialog;

    public LeasesFragment() {
    }

    public void refreshLeases() {
        int user_id = User.user_id;
        ConnectUtil.Go(Paths.getNetpath("leases"), "user_id=" + user_id, new StringCallback(){
            @Override
            public void onFinish(String result) {
                LeaseContent.addItemsFromString(result);
                adapter.setValues(LeaseContent.ITEMS);
                adapter.notifyDataSetChanged();
                App.setVal("count", LeaseContent.ITEMS.size());
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    public void showProgressDialog() {
        progressDialog = ProgressDialog.show(getActivity(), "刷新列表", "正在获取您的租约", true, false);
    }

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
