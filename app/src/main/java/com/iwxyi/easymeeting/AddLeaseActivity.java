package com.iwxyi.easymeeting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iwxyi.easymeeting.Globals.App;
import com.iwxyi.easymeeting.Globals.User;
import com.iwxyi.easymeeting.Users.PersonActivity;
import com.iwxyi.easymeeting.Utils.InputDialog;
import com.iwxyi.easymeeting.Utils.StringCallback;
import com.iwxyi.easymeeting.Utils.StringUtil;

public class AddLeaseActivity extends AppCompatActivity implements View.OnClickListener {

    int start_time = 0, end_time = 0;
    private TextView mStartTimeTv;
    private TextView mFinishTimeTv;
    private TextView mNumTv;
    private TextView mThemeTv;
    private TextView mUsageTv;
    private TextView mMessageTv;
    private CheckBox mSweepCb;
    private CheckBox mEntertainCb;
    private CheckBox mRemoteCb;
    private TextView mUsedDayTv;
    private Button mSignoutBtn;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lease);
        initView();
        initData();
    }

    private void initData() {
        int time = App.getTimestamp();
        mStartTimeTv.setText(""+time);
        mFinishTimeTv.setText(""+time);
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mStartTimeTv = (TextView) findViewById(R.id.tv_start_time);
        mFinishTimeTv = (TextView) findViewById(R.id.tv_finish_time);
        mNumTv = (TextView) findViewById(R.id.tv_num);
        mThemeTv = (TextView) findViewById(R.id.tv_theme);
        mUsageTv = (TextView) findViewById(R.id.tv_usage);
        mMessageTv = (TextView) findViewById(R.id.tv_message);
        mSweepCb = (CheckBox) findViewById(R.id.cb_sweep);
        mEntertainCb = (CheckBox) findViewById(R.id.cb_entertain);
        mRemoteCb = (CheckBox) findViewById(R.id.cb_remote);
        mUsedDayTv = (TextView) findViewById(R.id.tv_usedDay);
        mSignoutBtn = (Button) findViewById(R.id.btn_add_lease);
        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mSignoutBtn.setOnClickListener(this);
        mStartTimeTv.setOnClickListener(this);
        mFinishTimeTv.setOnClickListener(this);
        mNumTv.setOnClickListener(this);
        mThemeTv.setOnClickListener(this);
        mUsageTv.setOnClickListener(this);
        mMessageTv.setOnClickListener(this);
        mFab.setOnClickListener(this);
    }


    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.tv_start_time:// TODO 19/03/03
                break;
            case R.id.tv_finish_time:// TODO 19/03/03
                break;
            case R.id.tv_num:
                InputDialog.inputDialog(this, "请输入本次人数", mNumTv.getText().toString(),
                        InputType.TYPE_CLASS_NUMBER, "\\d+", "请输入正确的人数",
                        new StringCallback(){
                            @Override
                            public void onFinish(String result) {
                                ((EditText)v).setText(result);
                            }
                        });
                break;
            case R.id.tv_theme:// TODO 19/03/03
                InputDialog.inputDialog(this, "请输入本次会议主题", mThemeTv.getText().toString(),
                        new StringCallback(){
                            @Override
                            public void onFinish(String result) {
                                ((EditText)v).setText(result);
                            }
                        });
                break;
            case R.id.tv_usage:// TODO 19/03/03
                InputDialog.inputDialog(this, "请输入本次会议用途", mThemeTv.getText().toString(),
                        new StringCallback(){
                            @Override
                            public void onFinish(String result) {
                                ((EditText)v).setText(result);
                            }
                        });
                break;
            case R.id.tv_message:// TODO 19/03/03
                InputDialog.inputDialog(this, "请输入额外留言", mThemeTv.getText().toString(),
                        new StringCallback(){
                            @Override
                            public void onFinish(String result) {
                                ((EditText)v).setText(result);
                            }
                        });
                break;
            case R.id.btn_add_lease:

                break;
            case R.id.fab:
                Snackbar.make(v, "请仔细阅读租约条款", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            default:
                break;
        }
    }

    boolean canMatch(String str, String pat) {
        return StringUtil.canMatch(str, pat);
    }
}
