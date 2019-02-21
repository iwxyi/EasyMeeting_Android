package com.iwxyi.easymeeting;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.iwxyi.easymeeting.Fragments.LeasesFragment;
import com.iwxyi.easymeeting.Fragments.dummy.LeaseContent;
import com.iwxyi.easymeeting.Globals.UserInfo;
import com.iwxyi.easymeeting.Users.LoginActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LeasesFragment.OnLeaseListInteractionListener {

    private final int REQUEST_CODE_LOGIN = 1;
    private final int RESULT_CODE_LOGIN = 1;
    private FrameLayout mContentFl;

    private LeasesFragment leasesFragment;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        if (!UserInfo.isLogin()) {
            startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), REQUEST_CODE_LOGIN);
        }
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mContentFl = (FrameLayout) findViewById(R.id.fl_content);

        View drawView = navigationView.getHeaderView(0);
        ImageView mHeadIv = (ImageView)drawView.findViewById(R.id.iv_avatar);
        mHeadIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserInfo.isLogin()) { // 用户已经登录，切换到用户信息界面
                    //startActivityForResult(new Intent(getApplicationContext(), PersonActivity.class), REQUEST_CODE_PERSON);
                } else {
                    startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class), REQUEST_CODE_LOGIN);
                }
            }
        });

        fm = getSupportFragmentManager();
        leasesFragment = new LeasesFragment();
        FragmentTransaction ft = fm.beginTransaction();
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
            return true;
        } else if (id == R.id.action_refresh) {
            if (leasesFragment != null) {
                leasesFragment.refreshLeases();
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
        hideFragment(ft);

        if (id == R.id.nav_camera) {
            if (leasesFragment == null) {
                leasesFragment = new LeasesFragment();
                ft.add(R.id.fl_content, leasesFragment, "leases");
            } else {
                ft.show(leasesFragment);
            }
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void hideFragment(FragmentTransaction ft) {
        if (leasesFragment != null) {
            ft.hide(leasesFragment);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CODE_LOGIN) { // 登录成功
            if (leasesFragment != null) {
                leasesFragment.refreshLeases();
            }
        }
    }

    @Override
    public void onLeaseListInteraction(LeaseContent.LeaseItem item) {

    }
}
