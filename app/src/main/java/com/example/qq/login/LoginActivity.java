package com.example.qq.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qq.utils.ConfigUtil;
import com.example.qq.utils.DBHelper;
import com.example.qq.utils.MainActivity;
import com.example.qq.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView nameTxt;
    private TextView pwdTxt;
    private CheckBox checkBox;
    private Button loginBtn;
    private TextView registTxt;
    private TextView resetTxt;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new DBHelper(getApplicationContext(), 1);
        initView();
        initNmandPwdTxt();
    }

    //1.初始化用户名和密码控件
    private void initNmandPwdTxt() {
        SharedPreferences sp = getSharedPreferences(ConfigUtil.USER_SP, MODE_PRIVATE);
        String nameStr = sp.getString(ConfigUtil.USER_NAME, "");
        String pwdStr = sp.getString(ConfigUtil.USER_PWD, "");
        nameTxt.setText(nameStr);
        pwdTxt.setText(pwdStr);
    }

    private void initView() {
        nameTxt = findViewById(R.id.userNmTxt);
        pwdTxt = findViewById(R.id.pwdTxt);
        checkBox = findViewById(R.id.remenberChb);
        loginBtn = findViewById(R.id.loginBtn);
        registTxt = findViewById(R.id.registTxt);
        resetTxt = findViewById(R.id.resetPwdTxt);
        //监听
        loginBtn.setOnClickListener(this);
        registTxt.setOnClickListener(this);
        resetTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String nameStr = nameTxt.getText().toString();
        String pwdStr = pwdTxt.getText().toString();
        switch (v.getId()) {
            case R.id.loginBtn://2.登录
                login(nameStr, pwdStr);
                break;
            case R.id.registTxt://3.注册
                Intent intent = new Intent(getApplicationContext(), RegistActivity.class);
                intent.putExtra("name", nameStr);
                startActivity(intent);
                break;
            case R.id.resetPwdTxt://4.重置密码

                break;
        }
    }

    private void login(String nameStr, String pwdStr) {
        if (nameStr.isEmpty() || pwdStr.isEmpty()) {
            Toast.makeText(getApplicationContext(), "用户名或密码不能为空！！", Toast.LENGTH_LONG).show();
        } else {
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String sql = "select * from " + DBHelper.USER_TABLE + " where name = ? and pwd = ?";
            Cursor cursor = database.rawQuery(sql, new String[]{nameStr, pwdStr});
            if (cursor.getCount() > 0) //用户名和密码存在，跳转到Main
            {
                //游标放置第一位
                cursor.moveToFirst();
                SharedPreferences sp = getSharedPreferences(ConfigUtil.USER_SP, MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                if (checkBox.isChecked()) {
                    edit.putString(ConfigUtil.USER_NAME, nameStr);
                    edit.putString(ConfigUtil.USER_PWD, pwdStr);
                } else {
                    edit.putString(ConfigUtil.USER_NAME, "");
                    edit.putString(ConfigUtil.USER_PWD, "");
                }
                //提交
                edit.commit();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //传入Id，给个人主页
                intent.putExtra("userId", cursor.getInt(cursor.getColumnIndex("id")));
                startActivity(intent);
                finish();
            } else {
                //1.密码错误
                String namesql = "select * from " + DBHelper.USER_TABLE + " where name = ?";
                Cursor cursor1 = database.rawQuery(namesql, new String[]{namesql});
                if (cursor1.getCount() > 0) {
                    Toast.makeText(getApplicationContext(), "密码错误,请重新输入！！", Toast.LENGTH_LONG).show();
                } else {//2.用户不存在
                    Toast.makeText(getApplicationContext(), "用户不存在请注册！！", Toast.LENGTH_LONG).show();
                }
            }
            //关闭连接
            database.close();
        }
    }
}

