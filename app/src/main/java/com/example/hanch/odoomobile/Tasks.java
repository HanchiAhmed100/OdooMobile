package com.example.hanch.odoomobile;

/**
 * Created by hanch on 11/12/2018.
 */

public class Tasks {

    int id;
    String name;
    String email;
    String heurs;
    String begin;
    String end;

    public Tasks(int id, String name, String email, String heurs, String begin, String end) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.heurs = heurs;
        this.begin = begin;
        this.end = end;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", heurs='" + heurs + '\'' +
                ", begin='" + begin + '\'' +
                ", end='" + end + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeurs() {
        return heurs;
    }

    public void setHeurs(String heurs) {
        this.heurs = heurs;
    }
}
