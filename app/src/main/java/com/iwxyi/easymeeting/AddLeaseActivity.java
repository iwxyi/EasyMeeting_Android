package com.iwxyi.easymeeting;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.iwxyi.easymeeting.Fragments.Leases.LeaseContent;
import com.iwxyi.easymeeting.Globals.App;
import com.iwxyi.easymeeting.Globals.Paths;
import com.iwxyi.easymeeting.Globals.User;
import com.iwxyi.easymeeting.Utils.ConnectUtil;
import com.iwxyi.easymeeting.Utils.DateTimeUtil;
import com.iwxyi.easymeeting.Utils.InputDialog;
import com.iwxyi.easymeeting.Utils.SettingsUtil;
import com.iwxyi.easymeeting.Utils.StringCallback;
import com.iwxyi.easymeeting.Utils.StringUtil;

import net.steamcrafted.lineartimepicker.dialog.LinearDatePickerDialog;
import net.steamcrafted.lineartimepicker.dialog.LinearTimePickerDialog;

public class AddLeaseActivity extends AppCompatActivity implements View.OnClickListener {

    private final int RESULT_CODE_ADD = 4; // 跟MainActivity上面的一样
    private final int RESULT_CODE_Edit = 5; // 跟MainActivity上面的一样
    private final int RESULT_CODE_DELETE = 6; // 跟MainActivity上面的一样

    private int start_time = 0, finish_time = 0;
    private int s_year = 0, s_month = 0, s_date = 0, s_hour = 0, s_minute = 0;
    private int f_year = 0, f_month = 0, f_date = 0, f_hour = 0, f_minute = 0;

    boolean isModify = false; // 是否是修改的
    int lease_id = 0, room_id = 0, admin_id = 0;

    private TextView mStartDateTv;
    private TextView mStartTimeTv;
    private TextView mFinishDateTv;
    private TextView mFinishTimeTv;
    private TextView mNumTv;
    private TextView mThemeTv;
    private TextView mUsageTv;
    private TextView mMessageTv;
    private CheckBox mSweepCb;
    private CheckBox mEntertainCb;
    private CheckBox mRemoteCb;
    private TextView mTermsTv;
    private Button mSignoutBtn;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lease);
        lease_id = getIntent().getIntExtra("lease_id", 0);
        isModify = (lease_id > 0);

        initView();
        initData();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mStartDateTv = (TextView) findViewById(R.id.tv_start_date);
        mStartTimeTv = (TextView) findViewById(R.id.tv_start_time);
        mFinishDateTv = (TextView) findViewById(R.id.tv_finish_date);
        mFinishTimeTv = (TextView) findViewById(R.id.tv_finish_time);
        mNumTv = (TextView) findViewById(R.id.tv_num);
        mThemeTv = (TextView) findViewById(R.id.tv_theme);
        mUsageTv = (TextView) findViewById(R.id.tv_usage);
        mMessageTv = (TextView) findViewById(R.id.tv_message);
        mSweepCb = (CheckBox) findViewById(R.id.cb_sweep);
        mEntertainCb = (CheckBox) findViewById(R.id.cb_entertain);
        mRemoteCb = (CheckBox) findViewById(R.id.cb_remote);
        mTermsTv = (TextView) findViewById(R.id.tv_terms);
        mSignoutBtn = (Button) findViewById(R.id.btn_add_lease);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_delete));

        mStartDateTv.setOnClickListener(this);
        mStartTimeTv.setOnClickListener(this);
        mFinishDateTv.setOnClickListener(this);
        mFinishTimeTv.setOnClickListener(this);
        mSignoutBtn.setOnClickListener(this);
        mStartTimeTv.setOnClickListener(this);
        mFinishTimeTv.setOnClickListener(this);
        mNumTv.setOnClickListener(this);
        mThemeTv.setOnClickListener(this);
        mUsageTv.setOnClickListener(this);
        mMessageTv.setOnClickListener(this);
        mFab.setOnClickListener(this);
    }

    private void initData() {
        if (!isModify) { // 添加租约
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.setTitle("新增会议室租约");
            }
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("新增会议室租约");
            int time = getSuitableTime();
            start_time = time;
            finish_time = time + 7200;
            mFab.hide();
        } else {
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.setTitle("编辑会议室租约");
            }
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("编辑会议室租约");
            LeaseContent.LeaseItem item = LeaseContent.ITEM_MAP.get(lease_id);
            if (item == null) {
                Toast.makeText(this, "未能获取到 lease_id", Toast.LENGTH_SHORT).show();
                return;
            }
            room_id = item.room_id;
            admin_id = item.admin_id;
            start_time = item.start_time;
            finish_time = item.finish_time;
            mNumTv.setText("" + item.num);
            mThemeTv.setText(item.theme);
            mUsageTv.setText(item.usage);
            mMessageTv.setText(item.message);
            mSweepCb.setChecked(item.sweep);
            mEntertainCb.setChecked(item.entertain);
            mRemoteCb.setChecked(item.remote);
            mSignoutBtn.setText("提交修改");
            mTermsTv.setVisibility(View.GONE);
        }

        s_year = DateTimeUtil.getYearFromTimestamp(start_time);
        s_month = DateTimeUtil.getMonthFromTimestamp(start_time);
        s_date = DateTimeUtil.getDateFromTimestamp(start_time);
        s_hour = DateTimeUtil.getHourFromTimestamp(start_time);
        s_minute = DateTimeUtil.getMinuteFromTimestamp(start_time);
        f_year = DateTimeUtil.getYearFromTimestamp(finish_time);
        f_month = DateTimeUtil.getMonthFromTimestamp(finish_time);
        f_date = DateTimeUtil.getDateFromTimestamp(finish_time);
        f_hour = DateTimeUtil.getHourFromTimestamp(finish_time);
        f_minute = DateTimeUtil.getMinuteFromTimestamp(finish_time);
        refreshTimestampShowed();
    }


    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.tv_start_date:
                boolean s_data_tutorial = false;
                if (App.getInt("LinearDatePicker_tutorial") != 1) {
                    s_data_tutorial = true;
                }
                LinearDatePickerDialog.Builder.with(AddLeaseActivity.this)
                        .setYear(s_year)
                        .setMinYear(2000)
                        .setMaxYear(2030)
                        .setShowTutorial(s_data_tutorial)
                        .setButtonCallback(new LinearDatePickerDialog.ButtonCallback() {
                            @Override
                            public void onPositive(DialogInterface dialog, int year, int month, int day) {
                                App.setVal("LinearDatePicker_tutorial", 1);
                                s_year = year;
                                s_month = month;
                                s_date = day;
                                refreshTimestamp();
                                refreshTimestampShowed();
                            }

                            @Override
                            public void onNegative(DialogInterface dialog) {

                            }
                        }).build().show();
                break;
            case R.id.tv_start_time:
                boolean s_time_tutorial = false;
                if (SettingsUtil.getInt(getApplicationContext(), "LinearTimePicker_tutorial") != 1) {
                    s_time_tutorial = true;
                }
                LinearTimePickerDialog.Builder.with(AddLeaseActivity.this)
                        .setShowTutorial(s_time_tutorial)
                        .setButtonCallback(new LinearTimePickerDialog.ButtonCallback() {
                            @Override
                            public void onPositive(DialogInterface dialog, int hour, int minutes) {
                                SettingsUtil.setVal(getApplicationContext(), "LinearTimePicker_tutorial", 1);
                                s_hour = hour;
                                s_minute = minutes;
                                refreshTimestamp();
                                refreshTimestampShowed();
                            }

                            @Override
                            public void onNegative(DialogInterface dialog) {

                            }
                        })
                        .build().show();
                break;
            case R.id.tv_finish_date:
                boolean f_data_tutorial = false;
                if (App.getInt("LinearDatePicker_tutorial") != 1) {
                    f_data_tutorial = true;
                }
                LinearDatePickerDialog.Builder.with(AddLeaseActivity.this)
                        .setYear(s_year)
                        .setMinYear(2000)
                        .setMaxYear(2030)
                        .setShowTutorial(f_data_tutorial)
                        .setButtonCallback(new LinearDatePickerDialog.ButtonCallback() {
                            @Override
                            public void onPositive(DialogInterface dialog, int year, int month, int day) {
                                App.setVal("LinearDatePicker_tutorial", 1);
                                f_year = year;
                                f_month = month;
                                f_date = day;
                                refreshTimestamp();
                                refreshTimestampShowed();
                            }

                            @Override
                            public void onNegative(DialogInterface dialog) {

                            }
                        }).build().show();
                break;
            case R.id.tv_finish_time:
                boolean f_time_tutorial = false;
                if (SettingsUtil.getInt(getApplicationContext(), "LinearTimePicker_tutorial") != 1) {
                    f_time_tutorial = true;
                }
                LinearTimePickerDialog.Builder.with(AddLeaseActivity.this)
                        .setShowTutorial(f_time_tutorial)
                        .setButtonCallback(new LinearTimePickerDialog.ButtonCallback() {
                            @Override
                            public void onPositive(DialogInterface dialog, int hour, int minutes) {
                                SettingsUtil.setVal(getApplicationContext(), "LinearTimePicker_tutorial", 1);
                                f_hour = hour;
                                f_minute = minutes;
                                refreshTimestamp();
                                refreshTimestampShowed();
                            }

                            @Override
                            public void onNegative(DialogInterface dialog) {

                            }
                        })
                        .build().show();
                break;
            case R.id.tv_num:
                if (isModify) {
                    Toast.makeText(this, "人数以签到表(即加入会议的人)为准，不能修改", Toast.LENGTH_SHORT).show();
                    return;
                }
                InputDialog.inputDialog(this, "请输入本次人数", mNumTv.getText().toString(),
                        InputType.TYPE_CLASS_NUMBER, "\\d+", "请输入正确的人数",
                        new StringCallback() {
                            @Override
                            public void onFinish(String result) {
                                mNumTv.setText(result);
                            }
                        });
                break;
            case R.id.tv_theme:// TODO 19/03/03
                InputDialog.inputDialog(this, "请输入本次会议主题", mThemeTv.getText().toString(),
                        new StringCallback() {
                            @Override
                            public void onFinish(String result) {
                                mThemeTv.setText(result);
                            }
                        });
                break;
            case R.id.tv_usage:// TODO 19/03/03
                InputDialog.inputDialog(this, "请输入本次会议用途", mUsageTv.getText().toString(),
                        new StringCallback() {
                            @Override
                            public void onFinish(String result) {
                                mUsageTv.setText(result);
                            }
                        });
                break;
            case R.id.tv_message:// TODO 19/03/03
                InputDialog.inputDialog(this, "请输入额外留言", mMessageTv.getText().toString(),
                        new StringCallback() {
                            @Override
                            public void onFinish(String result) {
                                mMessageTv.setText(result);
                            }
                        });
                break;
            case R.id.btn_add_lease:
                commit();
                break;
            case R.id.fab:
                if (!isModify) {
                    Snackbar.make(v, "请仔细阅读租约条款", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("是否确认删除？\n删除的租约将无法恢复");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ConnectUtil.Get(Paths.getNetpath("deleteLease"), "lease_id=" + lease_id,
                                new StringCallback() {
                                    @Override
                                    public void onFinish(String result) {
                                        result = StringUtil.getXml(result, "result");
                                        if ("OK".equals(result)) {
                                            Toast.makeText(AddLeaseActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                            AddLeaseActivity.this.setResult(RESULT_CODE_DELETE);
                                            AddLeaseActivity.this.finish();
                                        } else {
                                            Toast.makeText(AddLeaseActivity.this, "删除失败" + result, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

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

        if (TextUtils.isEmpty(theme)) {
            Toast.makeText(this, "请输入主题", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] params = new String[]{
                "lease_id", "" + lease_id,
                "user_id", User.id(),
                "start_time", "" + start_time,
                "finish_time", "" + finish_time,
                "theme", theme,
                "usage", usage,
                "message", message,
                "sweep", sweep ? "1" : "0",
                "entertain", entertain ? "1" : "0",
                "remote", remote ? "1" : "0",
                "room_id", "" + room_id,
                "admin_id", "" + admin_id
        };

        String path = isModify ? Paths.getNetpath("updateLease") : Paths.getNetpath("insertLease");

        final ProgressDialog progressDialog = ProgressDialog.show(this, "正在添加...", "");
        ConnectUtil.Post(path, params, new StringCallback() {
            @Override
            public void onFinish(String result) {
                progressDialog.dismiss();
                result = StringUtil.getXml(result, "result");
                if (isModify) {
                    if ("OK".equals(result)) {
                        Toast.makeText(AddLeaseActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        AddLeaseActivity.this.setResult(RESULT_CODE_Edit);
                        AddLeaseActivity.this.finish();
                    } else {
                        Toast.makeText(AddLeaseActivity.this, "修改失败" + result, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if ("OK".equals(result)) {
                        Toast.makeText(AddLeaseActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                        AddLeaseActivity.this.setResult(RESULT_CODE_ADD);
                        AddLeaseActivity.this.finish();
                    } else {
                        Toast.makeText(AddLeaseActivity.this, "添加失败" + result, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void refreshTimestamp() {
        start_time = DateTimeUtil.valuesToTimestamp(s_year, s_month, s_date, s_hour, s_minute);
        finish_time = DateTimeUtil.valuesToTimestamp(f_year, f_month, f_date, f_hour, f_minute);
    }

    /**
     * 通过时间戳刷新开始时间和结束时间
     */
    private void refreshTimestampShowed() {
        mStartDateTv.setText(DateTimeUtil.timestampToString(start_time, "MM-dd"));
        mStartTimeTv.setText(DateTimeUtil.timestampToString(start_time, "HH:mm"));
        mFinishDateTv.setText(DateTimeUtil.timestampToString(finish_time, "MM-dd"));
        mFinishTimeTv.setText(DateTimeUtil.timestampToString(finish_time, "HH:mm"));
    }

    /**
     * 获取合适的开会日期(与网站上的一样)
     * @return
     */
    private int getSuitableTime() {
        int timestamp = App.getTimestamp();
        int hour = DateTimeUtil.getHourFromTimestamp(timestamp);
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
