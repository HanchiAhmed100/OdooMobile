package com.example.hanch.odoomobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyMap;

public class AddTasks extends AppCompatActivity {

    EditText nom,heures,ech;
    final String url = "http://192.168.1.30:8069", db = "hanchi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_tasks);
        nom = (EditText) findViewById(R.id.nom);
        heures = (EditText) findViewById(R.id.heurs);
        ech = (EditText) findViewById(R.id.echance);

    }



    private void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {


                final String Mnom = nom.getText().toString();
                final String Mheures = heures.getText().toString();
                final String Mech = ech.getText().toString();


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
                    common_config.setServerURL(new URL(String.format("%s/xmlrpc/2/common", url)));

                    int uid = (int) Client.execute(
                            common_config, "authenticate", asList(
                                    db, extras.getString("mail"), extras.getString("password"), emptyMap()));



                    final int projid = extras.getInt("id");
                    final Integer id = (Integer)models.execute("execute_kw", asList(
                            db, uid, extras.getString("password"),

                            "project.task", "create",
                            asList(new HashMap() {{ put("name", Mnom);put("date_deadline",Mech );put("planned_hours",Mheures ); put("project_id",projid ); }})

                    ));

                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Une tache a été ajouter ", Toast.LENGTH_LONG).show();
                        }

                    });

                    Intent p = new Intent(getApplicationContext(),TasksListActivity.class);
                    p.putExtra("mail",extras.getString("mail"));
                    p.putExtra("password",extras.getString("password"));
                    p.putExtra("id",projid);
                    startActivity(p);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (XmlRpcException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    void create(View v) {
        this.run();
    }


}
