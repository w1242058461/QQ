package com.example.qq.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.qq.beans.Student;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static String DB_NAME = "student.db";
    //个人信息表
    public static String USER_TABLE = "user";
    //联系人
    public static String CONTACT_TABLE = "student";

    public DBHelper(@androidx.annotation.Nullable Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table user (id integer primary key autoincrement" +
                ",name varchar not null" +
                ",pwd varchar no null" +
                ",remark varchar)";
        db.execSQL(sql);
        sql = "create table student (id integer primary key autoincrement" +
                ",name varchar not null" +
                ",userId varchar not null" +
                ",phone varchar not null" +
                ",parentphone varchar" +
                ",parentphone1 varchar)";
        db.execSQL(sql);
    }
    //根据userId查找所有学生信息
    public List<Student> getAllStudent(int userId)
    {
        List<Student> students = new ArrayList<>();
        String sql = "select * from "+CONTACT_TABLE +" where userId = ?";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, new String[]{userId + ""});
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                int userid = cursor.getInt(cursor.getColumnIndex("userId"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                String parentphone =cursor.getString(cursor.getColumnIndex("parentphone"));
                String parentphone1 = cursor.getString(cursor.getColumnIndex("parentphone1"));
                Student student = new Student(id,userid,name,phone,parentphone,parentphone1);
                students.add(student);
            }
            while (cursor.moveToNext());
        }
        database.close();
        return students;
    }
    public List<Student> selectByNameStr(String nameStr,int userId)
    {
        List<Student> students = new ArrayList<>();
        String sql = "select * from "+CONTACT_TABLE +" where name like \'%"+nameStr+"%\' and userId="+userId;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql,null);
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                int userid = cursor.getInt(cursor.getColumnIndex("userId"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                String parentphone =cursor.getString(cursor.getColumnIndex("parentphone"));
                String parentphone1 = cursor.getString(cursor.getColumnIndex("parentphone1"));
                Student student = new Student(id,userid,name,phone,parentphone,parentphone1);
                students.add(student);
            }
            while (cursor.moveToNext());
        }
        database.close();
        return students;
    }
    //删除
    public void deleteStudentById(int id)
    {
        String sql = "delete  from " +CONTACT_TABLE +" where id = ?";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql,new Integer[]{id});
        database.close();
    }
    //添加
    public void insertStudent(Student student)
    {
        String sql = "insert into "+CONTACT_TABLE+" (userid,name,phone,parentphone,parentphone1) values (?,?,?,?,?)";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql,new Object[]{student.getUserId(),student.getName(),student.getPhone(),student.getParentPhone(),student.getParentPhone1()});
        database.close();
    }
    //更新
    public void updateStudent(String phone,String ptPhone,String ptPhone1,int id)
    {
        String sql = "update "+CONTACT_TABLE+" set phone = ? ,parentphone = ?,parentphone1= ? where id = ?";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql,new Object[]{phone,ptPhone,ptPhone1,id});
        database.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
