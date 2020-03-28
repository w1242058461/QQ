package com.example.qq.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.qq.R;
import com.example.qq.login.LoginActivity;
import com.example.qq.utils.DBHelper;


public class PersonalFragment extends Fragment implements  View.OnClickListener{
    private TextView textView ;
    private TextView remarkView ;
    private Button button;
    private int userId;
    private DBHelper helper;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        userId = bundle.getInt("userId");
        helper = new DBHelper(getContext(),1);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_personal, container,false);
        initView(view);
        searchInfo();
        return view;
    }
    private void searchInfo()
    {
        SQLiteDatabase database = helper.getReadableDatabase();
        String sql = "select * from "+DBHelper.USER_TABLE +" where id =?";
        Cursor cursor = database.rawQuery(sql, new String[]{userId + ""});
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            textView.setText(name);
            remarkView.setText(remark);
        }
    }
    private void initView(View view)
    {
        textView = view.findViewById(R.id.name_txt_personal);
        remarkView = view.findViewById(R.id.remark_txt_personal);
        button = view.findViewById(R.id.out_btn_personal);
        //监听
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }
}
