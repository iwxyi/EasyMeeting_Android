package com.iwxyi.easymeeting.Users;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.iwxyi.easymeeting.R;

public class RegisterActivity extends AppCompatActivity {

    private final int RESULT_CODE_REGISTER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view) {
        Toast.makeText(this, "请填写用户名和密码", Toast.LENGTH_SHORT).show();

        return ;
        //setResult(RESULT_CODE_REGISTER);
        //finish();
    }
}
