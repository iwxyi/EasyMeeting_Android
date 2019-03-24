package com.iwxyi.easymeeting;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.iwxyi.easymeeting.Fragments.JoinMeetingFragment;
import com.iwxyi.easymeeting.Fragments.Leases.LeaseContent;
import com.iwxyi.easymeeting.Fragments.Leases.LeasesFragment;
import com.iwxyi.easymeeting.Fragments.Meetings.MeetingsContent;
import com.iwxyi.easymeeting.Fragments.Meetings.MeetingsFragment;
import com.iwxyi.easymeeting.Globals.App;
import com.iwxyi.easymeeting.Globals.User;
import com.iwxyi.easymeeting.SettingsActivitys.SettingsActivity;
import com.iwxyi.easymeeting.Users.LoginActivity;
import com.iwxyi.easymeeting.Users.PersonActivity;
import com.iwxyi.easymeeting.Utils.DateTimeUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LeasesFragment.OnLeaseListInteractionListener,
            MeetingsFragment.OnMeetingListInteractionListener, JoinMeetingFragment.OnJoinInteractionListener{

    private final int REQUEST_CODE_LOGIN    = 1;
    private final int REQUEST_CODE_REGISTER = 2;
    private final int REQUEST_CODE_PERSON   = 3;
    private final int REQUEST_CODE_ADD      = 4;
    private final int REQUEST_CODE_EDIT     = 5;
    private final int REQUEST_CODE_DELETE   = 6;
    private final int REQUEST_CODE_JOIN     = 7;
    private final int REQUEST_CODE_EXIT     = 8;

    private final int RESULT_CODE_LOGIN     = 1;
    private final int RESULT_CODE_REGISTER  = 2;
    private final int RESULT_CODE_PERSON    = 3;
    private final int RESULT_CODE_ADD       = 4;
    private final int RESULT_CODE_EDIT      = 5;
    private final int RESULT_CODE_DELETE    = 6;
    private final int RESULT_CODE_JOIN      = 7;
    private final int RESULT_CODE_EXIT      = 8;

    private FrameLayout mContentFl;
    private FragmentManager fm;
    private LeasesFragment leasesFragment;
    private MeetingsFragment meetingsFragment;
    private JoinMeetingFragment joinFragment;
    private int drawerMenuIndex = 1;

    private TextView mNicknameTv;
    private TextView mSignatureTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        if (!User.isLogin()) {
            startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), REQUEST_CODE_LOGIN);
        }

        if (App.getInt("firstOpen") == 0) {
            App.setVal("firstOpen", DateTimeUtil.getTimestamp());
        }
    }

    private void initView() {
        // APP Bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // FAB 按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startActivityForResult(new Intent(MainActivity.this, AddLeaseActivity.class), REQUEST_CODE_ADD);
            }
        });

        // 抽屉控件
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mContentFl = (FrameLayout) findViewById(R.id.fl_content);


        // 抽屉头像
        View drawView = navigationView.getHeaderView(0);
        ImageView mHeadIv = (ImageView) drawView.findViewById(R.id.iv_avatar);
        mNicknameTv = (TextView) drawView.findViewById(R.id.tv_nickname);
        mSignatureTv = (TextView) drawView.findViewById(R.id.tv_signature);
        mHeadIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (User.isLogin()) { // 用户已经登录，切换到用户信息界面
                    startActivityForResult(new Intent(getApplicationContext(), PersonActivity.class), REQUEST_CODE_PERSON);
                } else {
                    startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class), REQUEST_CODE_LOGIN);
                }
            }
        });

        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        leasesFragment = new LeasesFragment();
        ft.add(R.id.fl_content, leasesFragment, "leases").commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 右上角菜单被单击
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_refresh) {
            if (drawerMenuIndex == 1) {
                if (leasesFragment != null) {
                    leasesFragment.refreshLeases();
                    leasesFragment.showProgressDialog();
                }
            } else if (drawerMenuIndex == 2) {
                if (meetingsFragment != null) {
                    meetingsFragment.refreshMeetings();
                    meetingsFragment.showProgressDialog();
                }
            }


        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 侧滑菜单被点击
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction ft = fm.beginTransaction();

        if (id == R.id.my_leases) {
            hideFragment(ft);
            if (leasesFragment == null) {
                leasesFragment = new LeasesFragment();
                ft.add(R.id.fl_content, leasesFragment, "leases");
                leasesFragment.refreshLeases();
            } else {
                ft.show(leasesFragment);
            }
            drawerMenuIndex = 1;
        } else if (id == R.id.my_meeting) {
            hideFragment(ft);
            if (meetingsFragment == null) {
                meetingsFragment = new MeetingsFragment();
                ft.add(R.id.fl_content, meetingsFragment, "meetings");
                meetingsFragment.refreshMeetings();
                //meetingsFragment.showProgressDialog();
            } else {
                ft.show(meetingsFragment);
            }
            drawerMenuIndex = 2;
        } else if (id == R.id.join_meeting) {
            hideFragment(ft);
            if (joinFragment == null) {
                joinFragment = new JoinMeetingFragment();
                ft.add(R.id.fl_content, joinFragment, "join");
            } else {
                ft.show(joinFragment);
            }
            drawerMenuIndex = 3;
        } else if (id == R.id.user_certif) {
            hideFragment(ft);
            drawerMenuIndex = 4;
        } else if (id == R.id.nav_share) {
            Intent intent = new Intent();
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享APP");
            intent.putExtra(Intent.EXTRA_TEXT, "推荐您使用：EasyMeeting智能会议室管理系统");
            intent = Intent.createChooser(intent, "分享APP给好友");
            startActivity(intent);
            return true;
        } else if (id == R.id.nav_send) {
            Intent intent = new Intent();
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "会议邀请");
            intent.putExtra(Intent.EXTRA_TEXT, "请选择要分享的好友");
            intent = Intent.createChooser(intent, "邀请用户加入会议");
            startActivity(intent);
            return true;
        }

        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void hideFragment(FragmentTransaction ft) {
        if (leasesFragment != null) {
            ft.hide(leasesFragment);
        }
        if (meetingsFragment != null) {
            ft.hide(meetingsFragment);
        }
        if (joinFragment != null) {
            ft.hide(joinFragment);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CODE_LOGIN) { // 登录成功
            if (!User.isLogin()) {
                finish();
                return ;
            }

            if (leasesFragment != null) {
                leasesFragment.refreshLeases();
            }
            mNicknameTv.setText(User.nickname);
            // 设置签名，默认 公司 职位
            String signature = "";
            if (!User.company.isEmpty()) {
                signature = User.company;
            }
            if (!User.post.isEmpty()) {
                if (!signature.isEmpty())
                    signature += " ";
                signature += User.post;
            }
            // 设置邮箱、手机号等
            if (signature.isEmpty()) {
                if (!User.email.isEmpty())
                    signature = User.email;
                else if (!User.mobile.isEmpty())
                    signature = User.mobile;
                else
                    signature = "信用度："+ User.credit;
            }
            mSignatureTv.setText(signature);
        } else if (resultCode == RESULT_CODE_ADD || resultCode == RESULT_CODE_EDIT ||
                resultCode == RESULT_CODE_DELETE) {
            if (drawerMenuIndex == 1 && leasesFragment != null) {
                leasesFragment.refreshLeases();
                leasesFragment.showProgressDialog();
            } else if (drawerMenuIndex == 2 && meetingsFragment != null) {
                meetingsFragment.refreshMeetings();
                meetingsFragment.showProgressDialog();
            }
        }
    }

    @Override
    public void onLeaseListInteraction(LeaseContent.LeaseItem item) {
        Intent intent = new Intent(this, AddLeaseActivity.class);
        intent.putExtra("lease_id", item.lease_id);
        startActivityForResult(intent, REQUEST_CODE_ADD);
    }

    @Override
    public void onMeetingListInteraction(MeetingsContent.MeetingItem item) {

    }

    @Override
    public void onJoinInteraction(Uri uri) {

    }
}
