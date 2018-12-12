package com.example.hanch.odoomobile;

import android.view.View;
import android.widget.TextView;

/**
 * Created by hanch on 11/12/2018.
 */

public class TaskViewHolder {
    TextView name;
    TextView email;
    TextView heurs;
    TextView begin;
    TextView end;

    public TaskViewHolder(View v){
        name = v.findViewById(R.id.name);
        email = v.findViewById(R.id.email);
        heurs = v.findViewById(R.id.heurs);
        begin = v.findViewById(R.id.begin);
        end = v.findViewById(R.id.end);

    }
}
