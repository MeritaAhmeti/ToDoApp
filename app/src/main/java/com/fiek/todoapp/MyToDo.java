package com.fiek.todoapp;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class MyToDo {
    public String title;
    public String date;
    public String desc;
    public String key;

    public MyToDo() {
    }

    public MyToDo(String title,String date,String desc,String key) {
        this.title = title;
        this.date = date;
        this.desc = desc;
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}