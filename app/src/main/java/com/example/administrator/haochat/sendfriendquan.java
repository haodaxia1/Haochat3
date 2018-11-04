package com.example.administrator.haochat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

public class sendfriendquan extends AppCompatActivity {
    private EditText t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendfriendquan);
        t=findViewById(R.id.edt_message);

    }

    public void commit(View view) {


            new Thread(){
                @Override
                public void run() {
                    try{
                    String message=t.getText().toString();
                    String fromname = ((ApplicationTrans) getApplication()).getFromname();
                    Date date=new Date();
                    String datestring=date.toLocaleString();
                    InetAddress addr= InetAddress.getByName("47.100.222.47");
                    Socket sk=new Socket(addr,30001);
                    OutputStream out=sk.getOutputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(sk.getInputStream()));
                    JSONObject jsonobject=new JSONObject();
                    jsonobject.put("fromname",fromname);
                    jsonobject.put("message",message);
                    jsonobject.put("time",datestring);
                    jsonobject.put("zhuangtai",6);
                    out.write((jsonobject+"\n").getBytes());
                    out.close();
                    }catch (Exception e){
                    e.printStackTrace();
                }
                }
            }.start();
            t.setText("");



    }
}
