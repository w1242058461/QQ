package com.example.qq.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qq.R;
import com.example.qq.beans.Student;

import java.util.ArrayList;
import java.util.List;


public class ContactListAdapter extends BaseAdapter {
    private List<Student> beans;
    private Context context;
    public ContactListAdapter(Context context)
    {
        beans = new ArrayList<>();
        this.context = context;
    }
    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Student getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_contact_item, null);
            TextView textView = view.findViewById(R.id.item_txt_list_contact);
            view.setTag(textView);
        }
        else {
            view = convertView;
        }
        TextView textView = (TextView) view.getTag();
        textView.setText(beans.get(position).getName());
        return view;
    }
    //添加新的联系人
    public  void add(Student student)
    {
        beans.add(student);
        notifyDataSetInvalidated();
    }
    //添加初始信息
    public void addAll(List<Student> allStudent) {
        beans.clear();
        beans.addAll(allStudent);
        //通知ListView进行数据刷新
        notifyDataSetInvalidated();
    }
    //删除某个对象
    public void remove(Student student) {
        beans.remove(student);
        notifyDataSetInvalidated();
    }
}
