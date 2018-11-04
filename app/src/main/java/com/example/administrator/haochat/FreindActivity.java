package com.example.administrator.haochat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import java.util.List;


public class FreindActivity extends AppCompatActivity {

    private ListView friend_list;
    private EditText edt_commit;
    private Handler handler;
    private Context con;
    private FriendAdapter fadapter;
    private Button btn;
    //    private ListView message_lv;
    private List<Friend> Friends = new ArrayList<Friend>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freind_layout);
        con = this;
        Friends.add(new Friend("111", "好友列表"));
        friend_list = findViewById(R.id.lv_freind);
        fadapter = new FriendAdapter(con, R.layout.freind_adpter, Friends);
        friend_list.setAdapter(fadapter);
        friend_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friend ff = Friends.get(position);
                String toName=ff.getFname();
                ((ApplicationTrans) getApplication()).setToname(toName);
                Intent i=new Intent(FreindActivity.this,gerenziliao.class);
                startActivity(i);
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //更新
                if (msg.getData().get("info1").equals("s1")) {
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

    public void updatelist(){
        try{
            InetAddress addr = InetAddress.getByName("47.100.222.47");
//                            String addr=((ApplicationTrans) getApplication()).getToname();
            Socket sk = new Socket(addr, 30001);
            BufferedReader reader = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            String fromname = ((ApplicationTrans) getApplication()).getFromname();

            OutputStream out = sk.getOutputStream();
            JSONObject jsonobject = new JSONObject();
            jsonobject.put("fromname", fromname);
            jsonobject.put("zhuangtai", 2);
            out.write((jsonobject + "\n").getBytes());
            String Line = reader.readLine();


            JSONArray j = new JSONArray(Line);
            for (int i = 0; i < j.length(); i++) {
                JSONObject obj = j.getJSONObject(i);
                Friends.add(new Friend(fromname, obj.getString("fname")));
                Log.d("$$$$$$$$$$$$$$$$$", obj.getString("fname") + "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            }
//                            Intent ddd=new Intent(FreindActivity.this,MainActivity.class);
//                            startActivity(ddd);
            Message msg = Message.obtain();
            msg.getData().putString("info1", "s1");
            handler.sendMessage(msg);
            out.close();
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    public void update(View view) {
//                new Thread() {
//                    @Override
//                    public void run() {
//                        try {
//                            Log.d("###!!!", "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$RRR");
//                            InetAddress addr = InetAddress.getByName("192.168.31.140");
////                            String addr=((ApplicationTrans) getApplication()).getToname();
//                            Socket sk = new Socket(addr, 30001);
//                            BufferedReader reader = new BufferedReader(new InputStreamReader(sk.getInputStream()));
//                            String fromname = ((ApplicationTrans) getApplication()).getFromname();
//
//                            OutputStream out = sk.getOutputStream();
//                            JSONObject jsonobject = new JSONObject();
//                            jsonobject.put("fromname", fromname);
//                            jsonobject.put("zhuangtai", 2);
//                            out.write((jsonobject + "\n").getBytes());
//                            String Line = reader.readLine();
//
//
//                            JSONArray j = new JSONArray(Line);
//                            for (int i = 0; i < j.length(); i++) {
//                                JSONObject obj = j.getJSONObject(i);
//                                Friends.add(new Friend(fromname, obj.getString("fname")));
//                                Log.d("$$$$$$$$$$$$$$$$$", obj.getString("fname") + "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//                            }
////                            Intent ddd=new Intent(FreindActivity.this,MainActivity.class);
////                            startActivity(ddd);
//                            Message msg = Message.obtain();
//                            msg.getData().putString("info1", "s1");
//                            handler.sendMessage(msg);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }.start();
//    }

    public void ex(View view) {
        Runtime.getRuntime().exit(0);
    }

    public void quan(View view) {
        Intent i=new Intent(FreindActivity.this,sendfriendquan.class);
        startActivity(i);
    }
}



