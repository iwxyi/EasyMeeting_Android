package com.iwxyi.easymeeting;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.iwxyi.easymeeting.Globals.App;
import com.iwxyi.easymeeting.Utils.DateTimeUtil;
import com.iwxyi.easymeeting.Utils.InputDialog;
import com.iwxyi.easymeeting.Utils.StringCallback;

public class AddLeaseActivity extends AppCompatActivity implements View.OnClickListener {

    private int start_time = 0, finish_time = 0;

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
        int time = getSuitableTime();
        start_time = time;
        finish_time = time + 7200;
        mStartTimeTv.setText(DateTimeUtil.timestampToString(start_time, "MM-dd HH:mm"));
        mFinishTimeTv.setText(DateTimeUtil.timestampToString(finish_time, "MM-dd HH:mm"));
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
                        new StringCallback() {
                            @Override
                            public void onFinish(String result) {
                                ((EditText) v).setText(result);
                            }
                        });
                break;
            case R.id.tv_theme:// TODO 19/03/03
                InputDialog.inputDialog(this, "请输入本次会议主题", mThemeTv.getText().toString(),
                        new StringCallback() {
                            @Override
                            public void onFinish(String result) {
                                ((EditText) v).setText(result);
                            }
                        });
                break;
            case R.id.tv_usage:// TODO 19/03/03
                InputDialog.inputDialog(this, "请输入本次会议用途", mThemeTv.getText().toString(),
                        new StringCallback() {
                            @Override
                            public void onFinish(String result) {
                                ((EditText) v).setText(result);
                            }
                        });
                break;
            case R.id.tv_message:// TODO 19/03/03
                InputDialog.inputDialog(this, "请输入额外留言", mThemeTv.getText().toString(),
                        new StringCallback() {
                            @Override
                            public void onFinish(String result) {
                                ((EditText) v).setText(result);
                            }
                        });
                break;
            case R.id.btn_add_lease:
                commit();
                break;
            case R.id.fab:
                Snackbar.make(v, "请仔细阅读租约条款", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            default:
                break;
        }
    }

    private void commit() {
        int start_time = this.start_time;
        int finish_time = this.finish_time;
        String theme = mThemeTv.getText().toString();
        String usage = mUsageTv.getText().toString();
        String message = mMessageTv.getText().toString();
        boolean sweep = mSweepCb.isChecked();
        boolean entertain = mEntertainCb.isChecked();
        boolean remote = mRemoteCb.isChecked();

    }

    private int getSuitableTime() {
        int timestamp = App.getTimestamp();
        int hour = DateTimeUtil.getHourFromTimestamp(timestamp); // 必须要加L，不然会溢出
        int minute = DateTimeUtil.getMinuteFromTimestamp(timestamp);

        // 设置成下一个整点
        timestamp += 60 * (60 - minute);
        minute = 0;
        hour++;

        // 再设置成一小时后
        hour++;
        timestamp += 3600;

        // 判断是否符合开会的时间
        if (hour >= 10 && hour <= 14) {
            timestamp += 3600 * (14 - hour);
            hour = 14;
        } else if (hour > 16 && hour < 18) {
            timestamp += 3600 * (19 - hour);
            hour = 19;
        } else if (hour >= 20 && hour < 24) { // 晚上
            timestamp += 3600 * (24 - hour + 8); // 设置成明早8点
            hour = 8;
        } else if (hour >= 24) { // 23:??, 设置为明早8点
            timestamp += 3600 * (8 + 24 - hour);
            hour = 8;
        }

        return timestamp;
    }

}
