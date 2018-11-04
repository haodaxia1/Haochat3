package com.example.administrator.haochat;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class chat extends AppCompatActivity {
    private List<ChatContent> contents=new ArrayList<ChatContent>();
    private ChatContentAdapter adapter;
    private ListView chatLv;
    private ListView edt_accept;
    private EditText edt_commit;
    private EditText edt;
    private Handler handler;
    private Context con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        this.setTitle(((ApplicationTrans) getApplication()).getToname());
        chatLv=findViewById(R.id.chatListLv);
        for(int i=0;i<3;i++){
            ChatContent cc=new ChatContent();
            cc.setContent("hello world!");
            cc.setMe(i%2==0?true:false);
            contents.add(cc);
        }
        adapter=new ChatContentAdapter(this,R.layout.chat_layout,contents);
        chatLv.setAdapter(adapter);
        edt_commit=findViewById(R.id.sendMsg);

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                //更新
                if(msg.getData().get("info").equals("s")){
                    adapter.notifyDataSetChanged();
                }
            }
        };

        class MyThread extends Thread {

            public MyThread(String name){
                super(name);
            }
            @Override
            public void run() {
                while(true){
//                    contents.clear();
//                    int i=0;
//                    if(0==i%3){
//                        contents.clear();
//                        i++;
//                    }



                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    updatelist();

                }
            }
        }
        MyThread s=new MyThread("s");
        s.start();

//        new Thread(){
//            @Override
//            public void run() {
//                while(true){
////                    contents.clear();
//                    updatelist();
//
//                }
//            }
//        }.start();

    }
    public void updatelist(){
        try{
            InetAddress addr= InetAddress.getByName("47.100.222.47");
//            String addr=((ApplicationTrans) getApplication()).getToname();
            Socket sk=new Socket(addr,30001);
            BufferedReader reader=new BufferedReader(new InputStreamReader(sk.getInputStream()));
            String fromname=((ApplicationTrans) getApplication()).getFromname();
            OutputStream out=sk.getOutputStream();
            String toName=((ApplicationTrans) getApplication()).getToname();
            JSONObject jsonobject=new JSONObject();
            jsonobject.put("fromname",fromname);
            jsonobject.put("toname",toName);
            jsonobject.put("zhuangtai",1);
            out.write((jsonobject+"\n").getBytes());
            String Line=reader.readLine();


            JSONArray j=new JSONArray(Line);
            for(int i=0;i<j.length();i++){
                JSONObject obj=j.getJSONObject(i);
                ChatContent cc = new ChatContent();
                String na=obj.getString("message");
                cc.setContent(na);
                if(fromname.equals(obj.getString("fromname"))){
                    cc.setMe(true);
                } else{
                    cc.setMe(false);
                }
                contents.add(cc);
            }

            Message msg=Message.obtain();
            msg.getData().putString("info","s");
            handler.sendMessage(msg);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void send(View view) {
        final String message2=edt_commit.getText().toString();
//        EditText et=findViewById(R.id.sendMsg);
//
//        ChatContent cc=new ChatContent();
//        cc.setContent(et.getText().toString());
//        double ran=Math.random();
//        if(ran>0.5){
//            cc.setMe(true);
//        }
//        et.setText("");
//        contents.add(cc);
//
//        adapter.notifyDataSetChanged();
        new Thread(){

            @Override
            public void run() {
                try{
                    InetAddress addr= InetAddress.getByName("47.100.222.47");
                    String message=message2;
                    Socket sk=new Socket(addr,30001);
                    OutputStream out=sk.getOutputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(sk.getInputStream()));
                    String fromname=((ApplicationTrans) getApplication()).getFromname();
                    Log.d("fromname",fromname+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2");

                    Date date=new Date();
                    String datestring=date.toLocaleString();
                    String toName=((ApplicationTrans) getApplication()).getToname();;
                    Log.d("datetime",datestring+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                    JSONObject jsonobject=new JSONObject();
                    jsonobject.put("fromname",fromname);
                    jsonobject.put("toname",toName);
                    jsonobject.put("message",message);
                    jsonobject.put("time",datestring);
                    jsonobject.put("zhuangtai",0);
                    out.write((jsonobject+"\n").getBytes());
                    String Line=reader.readLine();

//                    JSONArray j=new JSONArray(Line);
//                    for(int i=0;i<j.length();i++){
//                        JSONObject obj=j.getJSONObject(i);
//                        ChatContent cc = new ChatContent();
//                        String na=obj.getString("message");
//                        cc.setContent(na);
//                        if(fromname.equals(obj.getString("fromname"))){
//                            cc.setMe(true);
//                        } else{
//                          cc.setMe(false);
//                        }
//                        contents.add(cc);
//                        //contents.add(true,new ChatContent(obj.getString("message")));
//                        Log.d("$$$$$$$$$$$$$$$$$",obj.getString("fromname")+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//                    }



//                    JSONObject obj=j.getJSONObject(0);
//                    String mes=obj.getString("message");
//                    String name=obj.getString("fromname");



                    Message msg=Message.obtain();
                    msg.getData().putString("info","s");
                    handler.sendMessage(msg);

                    //dfjlsd


//                    user u=new user();
//                    u.setUsername(username);
//                    u.setPassword(password);
//                    String jsonresult="";
//                    JSONObject jsonobject=new JSONObject();
//                    jsonobject.put("username",u.getUsername());
//                    jsonobject.put("password",u.getPassword());
//                    jsonobject.put("zhuangtai",zhuangtai);
//                    out.write((jsonobject+"\n").getBytes());
//
//                    String Line=reader.readLine();
//                    JSONObject object=new JSONObject(Line);
//                    int a=object.getInt("postion");
//                    Log.d("postion",a+"#################################################################################################3");



                    out.close();
                    sk.close();


                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }.start();
        edt_commit.setText("");
    }







        public void update(View view) {
        new Thread(){
            @Override
            public void run() {
                try{

                    InetAddress addr= InetAddress.getByName("47.100.222.47");
                    Socket sk=new Socket(addr,30001);
                    BufferedReader reader=new BufferedReader(new InputStreamReader(sk.getInputStream()));
                    String fromname=((ApplicationTrans) getApplication()).getFromname();
                    OutputStream out=sk.getOutputStream();
                    String toName=((ApplicationTrans) getApplication()).getToname();
                    JSONObject jsonobject=new JSONObject();
                    jsonobject.put("fromname",fromname);
                    jsonobject.put("toname",toName);
                    jsonobject.put("zhuangtai",1);
                    out.write((jsonobject+"\n").getBytes());
                    String Line=reader.readLine();


                    JSONArray j=new JSONArray(Line);
                    for(int i=0;i<j.length();i++){
                        JSONObject obj=j.getJSONObject(i);
                        ChatContent cc = new ChatContent();
                        String na=obj.getString("message");
                        cc.setContent(na);
                        if(fromname.equals(obj.getString("fromname"))){
                            cc.setMe(true);
                        } else{
                          cc.setMe(false);
                        }
                        contents.add(cc);
                    }

                    Message msg=Message.obtain();
                    msg.getData().putString("info","s");
                    handler.sendMessage(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }.start();
    }
}
