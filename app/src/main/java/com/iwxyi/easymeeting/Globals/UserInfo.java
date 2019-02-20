package com.iwxyi.easymeeting.Globals;

public class UserInfo {
    public int state;       // 0 未登录；1 用户；2管理员
    public int user_id;     // 用户ID
    public String username; // 账号
    public String password; // 密码
    public String nickname; // 昵称
    public int permission;  // 权限

    public boolean isLogin() {
        return state != 0;
    }

    public boolean isUser() {
        return state == 1;
    }

    public boolean isAdmin() {
        return state == 2;
    }
}
