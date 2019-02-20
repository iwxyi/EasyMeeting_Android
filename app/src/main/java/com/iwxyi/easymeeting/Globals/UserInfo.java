package com.iwxyi.easymeeting.Globals;

public class UserInfo {
    public static int state;       // 0 未登录；1 用户；2管理员
    public static int user_id;     // 用户ID
    public static String username; // 账号
    public static String password; // 密码
    public static String nickname; // 昵称
    public static int permission;  // 权限

    public static boolean isLogin() {
        return state != 0;
    }

    public static boolean isUser() {
        return state == 1;
    }

    public static boolean isAdmin() {
        return state == 2;
    }
}
