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

import static java.util.Arrays.asList;
import static java.util.Collections.emptyMap;

public class addProject extends AppCompatActivity {

    EditText nom;
    final String url = "http://192.168.1.30:8069", db = "hanchi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        nom = (EditText) findViewById(R.id.nom);

    }



    private void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {


                final String Mnom = nom.getText().toString();

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


                    final Integer id = (Integer)models.execute("execute_kw", asList(
                            db, uid, extras.getString("password"),
                            "project.project", "create",
                            asList(new HashMap() {{ put("name", Mnom); }})
                    ));

                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Le projet à été créer ", Toast.LENGTH_LONG).show();
                        }

                    });

                    Intent p = new Intent(getApplicationContext(),ListViewProjectList.class);
                    p.putExtra("mail",extras.getString("mail"));
                    p.putExtra("password",extras.getString("password"));
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
