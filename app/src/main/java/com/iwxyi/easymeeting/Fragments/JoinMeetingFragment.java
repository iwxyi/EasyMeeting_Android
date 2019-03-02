package com.iwxyi.easymeeting.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.iwxyi.easymeeting.Globals.App;
import com.iwxyi.easymeeting.Globals.Paths;
import com.iwxyi.easymeeting.Globals.User;
import com.iwxyi.easymeeting.R;
import com.iwxyi.easymeeting.Utils.ConnectUtil;
import com.iwxyi.easymeeting.Utils.NetworkCallback;
import com.iwxyi.easymeeting.Utils.StringUtil;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnJoinInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JoinMeetingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinMeetingFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnJoinInteractionListener mListener;
    private EditText mIdEt;
    private Button mJoinBtn;

    private ProgressDialog progressDialog;

    public JoinMeetingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JoinMeetingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JoinMeetingFragment newInstance(String param1, String param2) {
        JoinMeetingFragment fragment = new JoinMeetingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_join_meeting, container, false);
        initView(view); // 在这里初始化 fragment 的控件
        return view;
    }

    private void initView(@NonNull final View itemView) {
        mIdEt = (EditText) itemView.findViewById(R.id.et_id);
        mJoinBtn = (Button) itemView.findViewById(R.id.btn_join);
        mJoinBtn.setOnClickListener(this);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onJoinInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnJoinInteractionListener) {
            mListener = (OnJoinInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnJoinInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_join:
                String id = mIdEt.getText().toString();
                if (!StringUtil.canMatch(id, "\\d+")) {
                    App.toast("租约ID格式不对");
                    return ;
                }
                String[] params = new String[]{"user_id", User.id(), "lease_id", id};
                progressDialog = ProgressDialog.show(getActivity(), "请稍等", "正在加入", true, false);
                ConnectUtil.Go(Paths.getNetpath("joinLease"), params, new NetworkCallback(){
                    @Override
                    public void onFinish(String result) {
                        if (result.equals("OK")) {
                            App.toast("加入会议成功");
                        } else {
                            App.toast("加入会议失败" + result);
                        }
                        progressDialog.dismiss();
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnJoinInteractionListener {
        // TODO: Update argument type and name
        void onJoinInteraction(Uri uri);
    }
}
