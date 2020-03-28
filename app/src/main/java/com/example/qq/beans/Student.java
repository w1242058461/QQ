package com.example.qq.beans;

import java.io.Serializable;

public class Student implements Serializable {
    private int id;
    private int userId;
    private String name;
    private String phone;
    private String parentPhone;
    private String parentPhone1;

    public Student() {
    }

    public Student(int userId,String name, String phone, String parentPhone,String parentPhone1) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.parentPhone = parentPhone;
        this.parentPhone1 = parentPhone1;
    }
    public Student(int id,int userId,String name, String phone, String parentPhone,String parentPhone1) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.parentPhone = parentPhone;
        this.parentPhone1 = parentPhone1;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public String getParentPhone1() {
        return parentPhone1;
    }

    public void setParentPhone1(String parentPhone1) {
        this.parentPhone1 = parentPhone1;
    }
}
