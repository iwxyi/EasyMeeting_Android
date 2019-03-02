package com.iwxyi.easymeeting.Utils;

/**
 * @author: mrxy001
 * @time: 2019.2.20
 * 宇宙超级无敌联网类
 * 一行搞定取网页源码问题
 */

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.iwxyi.easymeeting.Globals.Paths;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ConnectUtil implements Runnable {

    String path, param;
    String method = "GET";
    int what;
    Handler handler;

    /**
     * 静态类工具，一行代码才可联网问题
     * @param handler 要返回的 Handler，进行处理返回的代码
     * @param what    返回的 what，由使用的对象来决定
     * @param path    网址
     * @param param   参数
     */
    static public void Go(int what, String path, String param, Handler handler) {
        Thread thread = new Thread(new ConnectUtil(what, path, param, handler));
        thread.start();
    }

    static public void Go(int what, String path, String[] param, Handler handler) {
        Thread thread = new Thread(new ConnectUtil(what, path, param, handler));
        thread.start();
    }

    static public void Go(String path, String param, Handler handler) {
        Thread thread = new Thread(new ConnectUtil(0, path, param, handler));
        thread.start();
    }

    static public void Go(String path, String param[], Handler handler) {
        Thread thread = new Thread(new ConnectUtil(0, path, param, handler));
        thread.start();
    }

    static public void Go(String path, Handler handler) {
        Thread thread = new Thread(new ConnectUtil(0, path, "", handler));
        thread.start();
    }

    public ConnectUtil(int what, String path, String param, Handler handler) {
        this.handler = handler;
        this.what = what;
        this.path = path;
        this.param = param;

    }

    public ConnectUtil(String path, String param, Handler handler) {
        this(0, path, param, handler);
    }

    public ConnectUtil(String path, Handler handler) {
        this(0, path, "", handler);
    }

    public ConnectUtil(int what, String path, String[] params, Handler handler) {

        this(what, path, "", handler);

        StringBuilder url = new StringBuilder();
        int count = params.length;
        for (int i = 0; i < count; i++) {
            String str = params[i];
            try {
                str = URLEncoder.encode(str, "UTF-8"); // 进行网络编码
            } catch (UnsupportedEncodingException e) {
                str = URLEncoder.encode(str);
            }
            if (i % 2 == 0) {
                if (i > 0) {
                    url.append("&");
                }
                url.append(str).append("=");
            }
            else {
                url.append(str);
            }
        }
        param = url.toString();
    }

    public ConnectUtil(Handler handler, String path, String[] params) {
        this(0, path, params, handler);
    }

    public ConnectUtil post() {
        this.method = "POST";
        return this;
    }

    @Override
    public void run() {
        String result;
        if ("POST".equals(method))
            result = NetworkUtil.post(path, param);
        else
            result = NetworkUtil.get(path, param);
        Message msg = new Message();
        msg.obj = result;
        msg.what = what;
        handler.sendMessage(msg);
        Log.i("====connect", path + "?" + param + ">>>>" + result);
    }

    /**
     * 判断有没有连接网络
     * @param activity
     * @return
     */
    public static boolean isConnect(Activity activity) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        try {
            ConnectivityManager connectivity = (ConnectivityManager) ((Activity) activity)
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    // 判断当前网络是否已经连接
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Log.v("====isConnect", e.toString());
        }
        return false;
    }
}