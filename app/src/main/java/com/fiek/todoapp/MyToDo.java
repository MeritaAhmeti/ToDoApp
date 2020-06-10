package com.fiek.todoapp;

public class MyToDo {
    String titletodo;
    String datetodo;
    String desctodo;
    String keytodo;

    public MyToDo() {
    }

    public MyToDo(String titletodo, String datetodo, String desctodo, String keytodo) {
        this.titletodo = titletodo;
        this.datetodo = datetodo;
        this.desctodo = desctodo;
        this.keytodo = keytodo;
    }

    public String getKeytodo() {
        return keytodo;
    }

    public void setKeytodo(String keytodo) {
        this.keytodo = keytodo;
    }

    public String getTitletodo() {
        return titletodo;
    }

    public void setTitletodo(String titletodo) {
        this.titletodo = titletodo;
    }

    public String getDatetodo() {
        return datetodo;
    }

    public void setDatetodo(String datetodo) {
        this.datetodo = datetodo;
    }

    public String getDesctodo() {
        return desctodo;
    }

    public void setDesctodo(String desctodo) {
        this.desctodo = desctodo;
    }
}
