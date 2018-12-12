package com.example.hanch.odoomobile;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by hanch on 11/12/2018.
 */

public class TasksListAdapter extends ArrayAdapter<String> {

    ArrayList<Tasks> Tasks;
    Activity context;
    private  static LayoutInflater inflater = null;

    public TasksListAdapter(Activity context, ArrayList<Tasks> Task){
        super(context ,R.layout.mytaskslist);
        this.context = (Activity) context;
        this.Tasks = Task;
    }

    @Override
    public int getCount() {
        return Tasks.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int position , @Nullable View convertView, @NonNull ViewGroup parent){
        View r = convertView;
        TaskViewHolder TaskViewHolder = null;
        if(r==null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.mytaskslist,null,true);
            TaskViewHolder = new TaskViewHolder(r);
            r.setTag(TaskViewHolder);
        }else{
            TaskViewHolder = (TaskViewHolder) r.getTag();
        }

        Tasks SelectedOne = Tasks.get(position);
        TaskViewHolder.name.setText(SelectedOne.name);
        TaskViewHolder.email.setText(SelectedOne.email);
        TaskViewHolder.heurs.setText(SelectedOne.heurs);
        TaskViewHolder.begin.setText(SelectedOne.begin);
        TaskViewHolder.end.setText(SelectedOne.end);

        context.registerForContextMenu(TaskViewHolder.name);
        return r;


    }

}
