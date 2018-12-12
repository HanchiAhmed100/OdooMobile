package com.example.hanch.odoomobile;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hanch on 04/12/2018.
 */

public class ProjectListAdapter extends ArrayAdapter<String>{

    ArrayList<Project> projects;
    Activity context;
    private  static LayoutInflater inflater = null;

    public  ProjectListAdapter(Activity context, ArrayList<Project> projects){
        super(context ,R.layout.mylistfile);
        this.context = (Activity) context;
        this.projects = projects;
    }

    @Override
    public int getCount() {
        return projects.size();
    }



    @Override
    public long getItemId(int i) {
        return i;
    }


    public View getView(int position , @Nullable View convertView, @NonNull ViewGroup parent){
        View r = convertView;
        ViewHolder viewHolder = null;
        if(r==null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.mylistfile,null,true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) r.getTag();
        }

        Project  SelectedOne = projects.get(position);

        viewHolder.ProjectName.setText(SelectedOne.name);
        viewHolder.ProjectDate.setText(SelectedOne.create_date);
        viewHolder.ProjectTasks.setText(SelectedOne.label_tasks);
        viewHolder.ProjectDate.setText(SelectedOne.create_date);
        context.registerForContextMenu(viewHolder.ProjectName);
        return r;
    }

}

