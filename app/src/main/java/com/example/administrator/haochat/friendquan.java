package com.example.administrator.haochat;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class friendquan extends AppCompatActivity {
    private List<friendquanclass> fqlist=new ArrayList<friendquanclass>();
    private ListView lv;
    private friendquanadapter fadapter;
    private Context con;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendquan);
        con=this;
        lv = findViewById(R.id.fqq);

        fadapter = new friendquanadapter(con, R.layout.friendquanitem, fqlist);

        lv.setAdapter(fadapter);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //更新
                if (msg.getData().get("info1").equals("s11")) {
                    fadapter.notifyDataSetChanged();
                }
            }
        };
        new Thread(){
            @Override
            public void run() {


                updatelist();


            }
        }.start();
    }
    private void updatelist(){
        try{
            InetAddress addr = InetAddress.getByName("47.100.222.47");
//                            String addr=((ApplicationTrans) getApplication()).getToname();
            Socket sk = new Socket(addr, 30001);
            BufferedReader reader = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            String toname = ((ApplicationTrans) getApplication()).getToname();
            OutputStream out = sk.getOutputStream();
            JSONObject jsonobject = new JSONObject();
            jsonobject.put("fromname", toname);
            jsonobject.put("zhuangtai", 5);
            out.write((jsonobject + "\n").getBytes());

            String Line = reader.readLine();

            JSONArray j = new JSONArray(Line);
            for (int i = 0; i < j.length(); i++) {
                JSONObject obj = j.getJSONObject(i);
                int a=obj.getInt("num");
                String num=a+"";
                String message=obj.getString("message");
                friendquanclass f=new friendquanclass(message,num);
                fqlist.add(f);

            }
//                            Intent ddd=new Intent(FreindActivity.this,MainActivity.class);
//                            startActivity(ddd);
            Message msg = Message.obtain();
            msg.getData().putString("info1", "s11");
            handler.sendMessage(msg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private  void initnr(){
        for (int i=0;i<6;i++)
        {
            friendquanclass f1=new friendquanclass("ssss","5");
            fqlist.add(f1);
        }
    }
}
