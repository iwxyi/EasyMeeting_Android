package com.iwxyi.easymeeting.Users;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iwxyi.easymeeting.Globals.App;
import com.iwxyi.easymeeting.Globals.UserInfo;
import com.iwxyi.easymeeting.R;

public class PersonActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mNicknameTv;
    private TextView mUsernameTv;
    private TextView mPasswordTv;
    private TextView mMobileTv;
    private TextView mEmailTv;
    private TextView mCompanyTv;
    private TextView mPostTv;
    private TextView mCreditTv;
    private TextView mUsedDayTv;
    private TextView mCountTv;
    private Button mSignoutBtn;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
        initData();
    }

    private void initView() {
        mNicknameTv = (TextView) findViewById(R.id.tv_nickname);
        mNicknameTv.setOnClickListener(this);
        mUsernameTv = (TextView) findViewById(R.id.tv_username);
        mUsernameTv.setOnClickListener(this);
        mPasswordTv = (TextView) findViewById(R.id.tv_password);
        mPasswordTv.setOnClickListener(this);
        mMobileTv = (TextView) findViewById(R.id.tv_mobile);
        mMobileTv.setOnClickListener(this);
        mEmailTv = (TextView) findViewById(R.id.tv_email);
        mEmailTv.setOnClickListener(this);
        mCompanyTv = (TextView) findViewById(R.id.tv_company);
        mCompanyTv.setOnClickListener(this);
        mPostTv = (TextView) findViewById(R.id.tv_post);
        mPostTv.setOnClickListener(this);
        mCreditTv = (TextView) findViewById(R.id.tv_credit);
        mCreditTv.setOnClickListener(this);
        mUsedDayTv = (TextView) findViewById(R.id.tv_usedDay);
        mUsedDayTv.setOnClickListener(this);
        mCountTv = (TextView) findViewById(R.id.tv_leaseCount);
        mCountTv.setOnClickListener(this);
        mSignoutBtn = (Button) findViewById(R.id.btn_signout);
        mSignoutBtn.setOnClickListener(this);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(this);
    }

    private void initData() {
        mNicknameTv.setText(UserInfo.nickname);
        mUsernameTv.setText(UserInfo.username);
        mPasswordTv.setText(UserInfo.password);
        mMobileTv.setText(UserInfo.mobile);
        mEmailTv.setText(UserInfo.email);
        mCompanyTv.setText(UserInfo.company);
        mPostTv.setText(UserInfo.post);
        mCreditTv.setText(""+UserInfo.credit);

        int firstOpen = App.getInt("firstOpen");
        int now = App.getTimestamp();
        int delta = now - firstOpen; // 秒
        int day = delta / 3600 / 24;
        int hour = delta % (3600*24) / 3600;
        int minute = delta % 3600 / 60;
        String usedDay = String.format("%d天%d小时%d分钟",day, hour, minute);
        mUsedDayTv.setText(usedDay);

        int num = App.getInt("count");
        mCountTv.setText("共有租约" + num + "条");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_nickname:
                // TODO 19/02/23
                break;
            case R.id.tv_username:
                // TODO 19/02/23
                break;
            case R.id.tv_password:
                // TODO 19/02/23
                break;
            case R.id.tv_mobile:
                // TODO 19/02/23
                break;
            case R.id.tv_email:
                // TODO 19/02/23
                break;
            case R.id.tv_company:
                // TODO 19/02/23
                break;
            case R.id.tv_post:
                // TODO 19/02/23
                break;
            case R.id.tv_credit:
                // TODO 19/02/23
                break;
            case R.id.tv_usedDay:
                // TODO 19/02/23
                break;
            case R.id.tv_leaseCount:
                // TODO 19/02/23
                break;
            case R.id.btn_signout:
                // TODO 19/02/23
                break;
            case R.id.fab:
                // TODO 19/02/23
                break;
            default:
                break;
        }
    }
}
