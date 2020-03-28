package com.example.qq.login;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qq.R;
import com.example.qq.utils.DBHelper;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView nameTxt ;
    private TextView pwdTxt;
    private TextView rePwdTxt;
    private TextView remarkTxt;
    private Button registBtn;
    private DBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        helper = new DBHelper(getApplicationContext(),1);
        initView();
    }
    private void initView()
    {
        nameTxt = findViewById(R.id.userNmTxt_regist);
        pwdTxt = findViewById(R.id.pwdTxt_regist);
        rePwdTxt = findViewById(R.id.resetPwdTxt_regist);
        remarkTxt = findViewById(R.id.remarkTxt_regist);
        registBtn = findViewById(R.id.registBtn_regist);
        registBtn.setOnClickListener(this);
        String name = getIntent().getStringExtra("name");
        nameTxt.setText(name);
    }

    @Override
    public void onClick(View v) {
        String nameStr = nameTxt.getText().toString();
        String pwdStr = pwdTxt.getText().toString();
        String rePwdStr = rePwdTxt.getText().toString();
        String remarkStr = remarkTxt.getText().toString();
        //1.有效值判断
        if(nameStr.length() < 6 || pwdStr.length()<6)
        {
            Toast.makeText(getApplicationContext(),"用户名或密码长度不能小于六位",Toast.LENGTH_LONG).show();
        }
        else if(!pwdStr.equals(rePwdStr))
        {
            Toast.makeText(getApplicationContext(),"两次输入的密码不一致请重新输入！",Toast.LENGTH_LONG).show();
            //清空确认密码框
            rePwdTxt.setText("");
        }
        else{
            SQLiteDatabase database = helper.getWritableDatabase();
            String sql = "insert into "+ DBHelper.USER_TABLE +" (name,pwd,remark) values (?,?,?)";
            database.execSQL(sql,new String[] {nameStr,pwdStr,remarkStr});
            database.close();
            finish();
        }
    }
}

