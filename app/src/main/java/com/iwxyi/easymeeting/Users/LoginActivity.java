package com.iwxyi.easymeeting.Users;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.iwxyi.easymeeting.Globals.Paths;
import com.iwxyi.easymeeting.Globals.UserInfo;
import com.iwxyi.easymeeting.R;
import com.iwxyi.easymeeting.Utils.ConnectUtil;
import com.iwxyi.easymeeting.Utils.StringUtil;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsernameTv;
    private EditText mPasswordTv;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        //透明状态栏          
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    private void initView() {
        mUsernameTv = (EditText) findViewById(R.id.tv_username);
        mPasswordTv = (EditText) findViewById(R.id.tv_password);
    }

    public void toLogin(View view) {
        String username = mUsernameTv.getText().toString();
        String password = mPasswordTv.getText().toString();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return ;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return ;
        }

        progressDialog = ProgressDialog.show(this, "请稍等", "正在登录", true, false);
        UserInfo.username = username;
        UserInfo.password = password;

        String[] param = {"username", username, "password", password};
        ConnectUtil.Go(handler, Paths.getNetpath("login"), param);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss();
            String result = msg.obj.toString();
            UserInfo.user_id = StringUtil.getXmlInt(result, "user_id");
            if (UserInfo.user_id != 0) {
                UserInfo.state = 1;
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "登录失败，用户名或者密码错误", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void toRegister(View view) {


    }

}
