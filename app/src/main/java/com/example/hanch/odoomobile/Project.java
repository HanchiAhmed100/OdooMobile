package com.example.hanch.odoomobile;

/**
 * Created by hanch on 10/12/2018.
 */

public class Project {

    int id;
    String name;
    int task_count;
    String create_date;
    String label_tasks;

    public Project(int id ,String name, int task_count, String create_date, String label_tasks) {
        this.id = id;
        this.name = name;
        this.task_count = task_count;
        this.create_date = create_date;
        this.label_tasks = label_tasks;
    }

    public Project(){

    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", task_count=" + task_count +
                ", create_date='" + create_date + '\'' +
                ", label_tasks='" + label_tasks + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getTask_count() {
        return task_count;
    }

    public void setTask_count(int task_count) {
        this.task_count = task_count;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getLabel_tasks() {
        return label_tasks;
    }

    public void setLabel_tasks(String label_tasks) {
        this.label_tasks = label_tasks;
    }
}
