package com.example.qq.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.qq.R;
import com.example.qq.beans.Student;
import com.example.qq.utils.DBHelper;
import com.example.qq.utils.DetailActivity;

import java.util.List;


public class ContactFragment extends Fragment implements View.OnKeyListener , View.OnClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener{
    private TextView searchTxt;
    private FloatingActionButton addBtn;
    private ListView listView;
    private ContactListAdapter adapter;
    private DBHelper helper;
    private int userId;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        //暂时给0值，测试；
        userId = bundle.getInt("userId",-1);
        helper = new DBHelper(getContext(),1);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_contact, container, false);
        initView(view);
        return view;
    }
    private void initView(View view)
    {
        searchTxt = view.findViewById(R.id.searchTxt_Contact);
        addBtn = view.findViewById(R.id.addBtn_Contact);
        listView = view.findViewById(R.id.listView_Contact);
        listView.setAdapter(adapter = new ContactListAdapter(getContext()));
        //添加数据到listView
        adapter.addAll(helper.getAllStudent(userId));
        //添加键盘监听
        searchTxt.setOnKeyListener(this);
        addBtn.setOnClickListener(this);
        //list长按
        listView.setOnItemLongClickListener(this);
        //list点击
        listView.setOnItemClickListener(this);
    }

    //根据名字进行模糊查询
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        String likeStr = searchTxt.getText().toString();
        //根据名字进行模糊查询
        List<Student> students = helper.selectByNameStr(likeStr,userId);
        adapter.addAll(students);
        return false;
    }

    //添加数据
    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_contact_add, null);

        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView nameTxt = view.findViewById(R.id.name_dialog_contact);
                TextView phoneTxt = view.findViewById(R.id.phone_dialog_contact);
                TextView ptPhoneTxt = view.findViewById(R.id.parentPhone_dialog_contact);
                TextView ptPhone1Txt = view.findViewById(R.id.parentPhone2_dialog_contact);
                String nameStr = nameTxt.getText().toString();
                String ph = phoneTxt.getText().toString();
                String ptPh = ptPhoneTxt.getText().toString();
                String ptPh1 = ptPhone1Txt.getText().toString();
                Student student = new Student(userId,nameStr,ph,ptPh,ptPh1);
                //更新数据库
                helper.insertStudent(student);
                //更新显示列表
                adapter.add(student);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //显示
        builder.show();
    }

    //跳转到详情页面，打电话
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(),DetailActivity.class);
        Student student = adapter.getItem(position);
        intent.putExtra("student",student);
        startActivity(intent);
    }
    //修改信息
    private void update(final Student student)
    {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_contact_update, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final TextView phTxt = view.findViewById(R.id.updatePhone_txt_dialog_contact);
        final TextView ptphTxt = view.findViewById(R.id.updatePtPhone_txt_dialog_contact);
        final TextView ptph1Txt = view.findViewById(R.id.updatePtPhone2_txt_dialog_contact);
        //初始化控件
        phTxt.setHint(student.getPhone());
        ptphTxt.setHint(student.getParentPhone());
        ptph1Txt.setHint(student.getParentPhone1());
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String phStr = phTxt.getText().toString();
                String ptPhStr = ptphTxt.getText().toString();
                String ptPh1Str = ptphTxt.getText().toString();
                if(phStr.isEmpty()) phStr = phTxt.getHint().toString();
                if(ptPhStr.isEmpty()) ptPhStr = ptph1Txt.getHint().toString();
                if(ptPh1Str.isEmpty()) ptPh1Str = ptph1Txt.getHint().toString();
                //输入的合理性，正则表达式
                student.setPhone(phStr);
                student.setParentPhone(ptPhStr);
                student.setParentPhone1(ptPh1Str);
                //更新数据库
                helper.updateStudent(phStr,ptPhStr,ptPh1Str,student.getId());
                //更新列表
                adapter.notifyDataSetInvalidated();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    //长按Item，弹出删除和修改菜单
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
        final Student student = adapter.getItem(position);
        //参数2，弹出菜单的目标
        PopupMenu popupMenu = new PopupMenu(getContext(),view);
        popupMenu.inflate(R.menu.menu_contact);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.delete_menu_contact://删除
                        helper.deleteStudentById(student.getId());
                        adapter.remove(student);
                        break;
                    case R.id.update_menu_contact:
                        update(student);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
        return false;
    }
}
