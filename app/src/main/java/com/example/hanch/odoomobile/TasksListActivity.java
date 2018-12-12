package com.example.hanch.odoomobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

public class TasksListActivity extends AppCompatActivity {

    final String url = "http://192.168.1.9:8069", db = "hanchi", username = "admin", password = "admin";
    private ArrayList<Tasks> Tasks = new ArrayList<>();
    private TasksListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_list);
        this.run();

        TasksListAdapter adapter = new TasksListAdapter(this,Tasks);
        ListView lv = (ListView) findViewById(R.id.MyList);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);

    }
    private void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                XmlRpcClient Client = new XmlRpcClient();
                final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
                final XmlRpcClient models = new XmlRpcClient() {{
                    setConfig(new XmlRpcClientConfigImpl() {{
                        try {
                            setServerURL(new URL(String.format("%s/xmlrpc/2/object", url)));
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }});
                }};
                try {
                    common_config.setServerURL( new URL(String.format("%s/xmlrpc/2/common", url)));
                    int uid = (int)Client.execute(
                            common_config, "authenticate", asList(
                                    db, username, password, emptyMap()));

                    Intent i = getIntent();
                    Bundle extras = getIntent().getExtras();
                    System.out.println();
                    System.out.println(extras.getInt("id"));


                    List x = asList((Object[])models.execute("execute_kw", asList(
                            db, uid, password,
                            "project.task", "search_read",
                            asList(asList(asList("project_id","=" ,extras.getInt("id") ))),
                            //asList(asList()),
                            new HashMap() {{
                                put("fields", asList("name"  , "user_email" , "planned_hours", "user_id", "date_deadline","create_date"));

                            }}
                    )));
                    System.out.println(x);

                    JSONArray array = new JSONArray();
                    Iterator<Map<String,Object>> it= x.iterator();
                    while(it.hasNext()) {
                        Map<String,Object> tmp = it.next();
                        JSONObject o = new JSONObject(tmp);
                        int id = o.getInt("id");
                        String name = o.getString("name");
                        String email = o.getString("user_email");
                        String heurs = o.getString("planned_hours");
                        String begin = o.getString("create_date");
                        String end = o.getString("date_deadline");
                        Tasks p = new Tasks(id,name,email,heurs,begin,end);
                        System.out.println(p.toString());
                        Tasks.add(p);
                    }



                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (XmlRpcException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void ReturnView(View view) {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
