package com.example.qq.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.qq.R;
import com.example.qq.beans.Student;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView idTxt;
    private TextView nameTxt;
    private Button phoneBtn;
    private Button ptphoneBtn;
    private Button ptphone1Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        //反序列化提取对象
        Student student = (Student) intent.getSerializableExtra("student");
        initView(student);
    }
    private void initView(Student student)
    {
        idTxt = findViewById(R.id.id_txt_detail);
        nameTxt = findViewById(R.id.name_txt_detail);
        phoneBtn = findViewById(R.id.phone_txt_detail);
        ptphoneBtn = findViewById(R.id.ptPhone_txt_detail);
        ptphone1Btn = findViewById(R.id.ptPhone1_txt_detail);
        //初始化控件值
        idTxt.setText(student.getId()+"");
        nameTxt.setText(student.getName());
        phoneBtn.setText(student.getPhone());
        if(!student.getParentPhone().isEmpty()) {
            ptphoneBtn.setVisibility(View.VISIBLE);
            ptphoneBtn.setText(student.getParentPhone());
        }
        if(!student.getParentPhone1().isEmpty()) {
            ptphone1Btn.setVisibility(View.VISIBLE);
            ptphone1Btn.setText(student.getParentPhone1());
        }
        //添加监听
        phoneBtn.setOnClickListener(this);
        ptphoneBtn.setOnClickListener(this);
        ptphone1Btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Button btn = (Button) v;
        //打电话
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + btn.getText().toString().trim()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
