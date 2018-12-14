package com.example.hanch.odoomobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

public class MainActivity extends AppCompatActivity {

    final String url = "http://192.168.43.224:8069", db = "hanchi", username = "admin", password = "admin";
    TextView myid ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.run();
        //this.change();
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
                    System.out.println("-------------- uid output ---------------------");
                    System.out.println(uid);


                    List x = asList((Object[])models.execute("execute_kw", asList(
                            db, uid, password,
                            "project.project", "search_read",
                            asList(asList()),
                            new HashMap() {{
                                put("fields", asList("name", "create_date" , "date_start" , "label_tasks" , "task_count" , "project_count" , "id"));
                                put("limit", 5);
                            }}
                    )));

                    JSONArray array = new JSONArray();
                    Iterator<Map<String,Object>> it= x.iterator();
                    while(it.hasNext()) {
                        Map<String,Object> tmp = it.next();
                        JSONObject o = new JSONObject(tmp);
                        System.out.println(o);
                        String s = o.getString("name");
                        System.out.println(s);
                        array.put(o);



                        /*Set<String> keys =  tmp.keySet();
                        Iterator<String> it2 = keys.iterator();
                        while(it2.hasNext()) {
                            System.out.println("******************"+it2.next()+"******************");
                        }*/
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

    public void changeView(View view) {
        Intent i = new Intent(this,TasksListActivity.class);
        startActivity(i);
    }
    public void ListView(View view) {
        Intent i = new Intent(this,ListViewProjectList.class);
        startActivity(i);
    }
    public void loginView(View view) {
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
    }
    public void logView(View view) {
        Intent i = new Intent(this,log.class);
        startActivity(i);
    }
}
