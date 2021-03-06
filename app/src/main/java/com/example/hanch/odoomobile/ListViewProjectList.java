package com.example.hanch.odoomobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class ListViewProjectList extends AppCompatActivity {

    final String url = "http://192.168.43.224:8069", db = "hanchi";
    private ArrayList<Project> projects = new ArrayList<>();
    private ProjectListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_view_project_list);
        this.run();

        ProjectListAdapter adapter = new ProjectListAdapter(this,projects);
        ListView lv = (ListView) findViewById(R.id.MyList);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Integer id  =  (Integer)((Project)projects.get(i)).id;
                Toast.makeText(ListViewProjectList.this,"Projet : " + (String)((Project)projects.get(i)).name ,Toast.LENGTH_LONG).show();

                Intent x = getIntent();
                Bundle extras = getIntent().getExtras();

                Intent intent = new Intent(getApplicationContext(),TasksListActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("mail",extras.getString("mail"));
                intent.putExtra("password",extras.getString("password"));
                startActivity(intent);

            }
        });

    }

    private void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Intent i = getIntent();
                Bundle extras = getIntent().getExtras();

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
                                    db, extras.getString("mail") , extras.getString("password"), emptyMap()));



                    List x = asList((Object[])models.execute("execute_kw", asList(
                            db, uid , extras.getString("password"),
                            "project.project", "search_read",
                            asList(asList()),
                            new HashMap() {{
                                put("fields", asList("name", "create_date" , "date_start" , "label_tasks" , "task_count" , "project_count" , "id"));
                            }}
                    )));

                    JSONArray array = new JSONArray();
                    Iterator<Map<String,Object>> it= x.iterator();
                    while(it.hasNext()) {
                        Map<String,Object> tmp = it.next();
                        JSONObject o = new JSONObject(tmp);
                        int id = o.getInt("id");
                        String Name = o.getString("name");
                        String createdDate = o.getString("create_date");
                        String Count = o.getString("task_count");

                        String tasks = o.getString("label_tasks");
                        Project p = new Project(id,Name,Count,createdDate,tasks);
                        System.out.println(p.toString());
                        projects.add(p);
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

    void add(View v){
        Intent x = getIntent();
        Bundle extras = getIntent().getExtras();
        Intent i = new Intent(this,addProject.class);
        i.putExtra("password",extras.getString("password"));
        i.putExtra("mail",extras.getString("mail"));
        startActivity(i);
    }
}
