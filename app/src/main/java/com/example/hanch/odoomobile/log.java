package com.example.hanch.odoomobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyMap;

public class log extends AppCompatActivity {

    EditText mail, password;
    final String url = "http://192.168.43.224:8069", db = "hanchi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_log);
        mail = (EditText) findViewById(R.id.Username);
        password = (EditText) findViewById(R.id.password);

    }

    private void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String Smail = mail.getText().toString();
                String Spassword = password.getText().toString();


                System.out.println(Smail+"-----------------------------------"+Spassword);
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
                    try{
                        String uid = (String) Client.execute(
                                common_config, "authenticate", asList(
                                        db, Smail, Spassword, emptyMap())).toString();

                        System.out.println(uid);
                        if(uid == "false"){

                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Mail ou mot de passe incorrecte !", Toast.LENGTH_LONG).show();

                                }

                            });

                            Intent i = new Intent(getApplicationContext(),log.class);
                            startActivity(i);


                        }else {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), " Bonjour  ", Toast.LENGTH_LONG).show();
                                }

                            });
                            Intent i = new Intent(getApplicationContext(),ListViewProjectList.class);
                            i.putExtra("uid",uid);
                            i.putExtra("mail",Smail);
                            i.putExtra("password",Spassword);
                            startActivity(i);
                        }

                    }catch (Error e){
                        e.printStackTrace();
                    }


                } catch (MalformedURLException e) {
                    TextView ErrMsg = (TextView) findViewById(R.id.ErrMsg);
                    ErrMsg.setText("Mail ou mot de passe incorrecte !");
                    e.printStackTrace();
                } catch (XmlRpcException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    void Login(View v) {
        this.run();
    }

}
