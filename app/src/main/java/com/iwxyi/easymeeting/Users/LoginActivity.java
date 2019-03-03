package com.iwxyi.easymeeting.Users;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.iwxyi.easymeeting.Globals.App;
import com.iwxyi.easymeeting.Globals.Paths;
import com.iwxyi.easymeeting.Globals.User;
import com.iwxyi.easymeeting.R;
import com.iwxyi.easymeeting.Utils.ConnectUtil;
import com.iwxyi.easymeeting.Utils.StringCallback;
import com.iwxyi.easymeeting.Utils.StringUtil;

public class LoginActivity extends AppCompatActivity {

    private final int RESULT_CODE_LOGIN     = 1;
    private final int REQUEST_CODE_REGISTER = 2;
    private final int RESULT_CODE_REGISTER  = 2;

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

        User.username = App.getVal("username");
        User.password = App.getVal("password");
        mUsernameTv.setText(User.username);
        mPasswordTv.setText(User.password);
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
        User.username = username;
        User.password = password;

        String[] param = {"username", username, "password", password};
        ConnectUtil.Go(Paths.getNetpath("login"), param, new StringCallback(){
            @Override
            public void onFinish(String result) {
                progressDialog.dismiss();
                User.user_id = StringUtil.getXmlInt(result, "user_id");
                if (User.user_id != 0) {
                    User.state    = 1;
                    User.nickname = StringUtil.getXml   (result, "nickname");
                    User.credit   = StringUtil.getXmlInt(result, "credit"  );
                    User.mobile   = StringUtil.getXml   (result, "mobile"  );
                    User.email    = StringUtil.getXml   (result, "email"   );
                    User.company  = StringUtil.getXml   (result, "company" );
                    User.post     = StringUtil.getXml   (result, "post"    );

                    App.setVal("user_id",  User.user_id );
                    App.setVal("username", User.username);
                    App.setVal("password", User.password);
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CODE_LOGIN);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "登录失败，用户名或者密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void toRegister(View view) {
        startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), REQUEST_CODE_REGISTER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CODE_REGISTER) { // 登录成功
            setResult(RESULT_CODE_REGISTER);
            finish();
        }
    }

}
