package com.example.qq.utils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.qq.R;
import com.example.qq.login.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {
    private TextView textView;
    private int cnt = 5;
    private Timer timer = new Timer();
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        textView = findViewById(R.id.skipTxt);
        handler = new Handler();
        //不点击5秒后直接跳转
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                //点击后直接跳转
                startLogin();
            }
        }, 5000);
        //点击后跳转
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (runnable != null) {
                    //结束及时
                    handler.removeCallbacks(runnable);
                }
                //跳转
                startLogin();
            }
        });
        //计时操作
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //UI上面运行的线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cnt--;
                        textView.setText("跳过" + cnt);
                        if (cnt <= 0) {
                            timer.cancel();
                        }
                    }
                });
            }
        }, 1000, 1000);
    }

    //跳转页面
    private void startLogin() {
        SharedPreferences sp = getSharedPreferences(ConfigUtil.USER_SP, MODE_PRIVATE);
        String name = sp.getString(ConfigUtil.USER_NAME, "");//key,default
        String pwd = sp.getString(ConfigUtil.USER_PWD, "");
        Intent intent;
        if (name.isEmpty() || pwd.isEmpty()) {//没有登录信息
            intent = new Intent(getApplicationContext(), LoginActivity.class);
        } else//跳转到主页面
            intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
