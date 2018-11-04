package com.example.administrator.haochat;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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


public class tongxinActivity extends AppCompatActivity {

    private ListView edt_accept;
    private EditText edt_commit;
    private Handler handler;
    private Context con;
    private MessageAdapter fadapter;
//    private ListView message_lv;
    private List<Mymessage> Mymessages=new ArrayList<Mymessage>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tongxin);
        edt_accept=findViewById(R.id.lv_accept);
        edt_commit=findViewById(R.id.edt_commit);
        con=this;
        fadapter=new MessageAdapter(con,R.layout.tongxin_adpter,Mymessages);
        edt_accept.setAdapter(fadapter);


        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                //更新
                if(msg.getData().get("info").equals("s")){
                    fadapter.notifyDataSetChanged();
                }
            }
        };
    }

    public void commit(View view) {
        new Thread(){

            @Override
            public void run() {
                try{
                    InetAddress addr= InetAddress.getByName("192.168.31.140");
                    Socket sk=new Socket(addr,30001);
                    OutputStream out=sk.getOutputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(sk.getInputStream()));


//                    String username=edt_username.getText().toString();
//                    String password=edt_password.getText().toString();
                    String fromname=((ApplicationTrans) getApplication()).getFromname();
                    Log.d("fromname",fromname+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2");
                    String message=edt_commit.getText().toString();
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



                    JSONArray j=new JSONArray(Line);
                    for(int i=0;i<j.length();i++){
                        JSONObject obj=j.getJSONObject(i);
                        Mymessages.add(new Mymessage(obj.getString("fromname"),obj.getString("message")));
                        Log.d("$$$$$$$$$$$$$$$$$",obj.getString("fromname")+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                    }



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
    }

    public void update(View view) {
        new Thread(){
            @Override
            public void run() {
                try{

//                    InetAddress addr= InetAddress.getByName("192.168.31.140");
                    String addr=((ApplicationTrans) getApplication()).getToname();
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
                        Mymessages.add(new Mymessage(obj.getString("fromname"),obj.getString("message")));
                        Log.d("$$$$$$$$$$$$$$$$$",obj.getString("fromname")+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
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
