package com.example.hanch.odoomobile;

import android.view.View;
import android.widget.TextView;

/**
 * Created by hanch on 04/12/2018.
 */

public class ViewHolder {
    TextView ProjectName;
    TextView ProjectTasks;
    TextView ProjectTasksCount;
    TextView ProjectDate;


    public ViewHolder(View v){
        ProjectName = v.findViewById(R.id.ProjectName);
        ProjectTasks = v.findViewById(R.id.TasksName);
        ProjectTasksCount = v.findViewById(R.id.TasksCount);
        ProjectDate = v.findViewById(R.id.CreatedDate);
    }
}
