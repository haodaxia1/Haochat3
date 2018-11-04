package com.example.administrator.haochat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class DengluActivity extends AppCompatActivity {

    private EditText edt_username;
    private EditText edt_password;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denglu);
        edt_password=findViewById(R.id.edt_password2);
        edt_username=findViewById(R.id.edt_username2);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //更新
                if (msg.getData().get("info1").equals("-99")) {
                    Toast.makeText(DengluActivity.this,"密码或用户名错误",Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    public void commit(View view) {
        new Thread(){

            @Override
            public void run() {
                try{
                    int zhuangtai=4;
                    InetAddress addr= InetAddress.getByName("47.100.222.47");
                    Socket sk=new Socket(addr,30001);
                    OutputStream out=sk.getOutputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(sk.getInputStream()));


                    String username=edt_username.getText().toString();
                    ((ApplicationTrans) getApplication()).setFromname(username);
                    String password=edt_password.getText().toString();
                    user u=new user();
                    u.setUsername(username);
                    u.setPassword(password);
                    String jsonresult="";
                    JSONObject jsonobject=new JSONObject();
                    jsonobject.put("username",u.getUsername());
                    jsonobject.put("password",u.getPassword());
                    jsonobject.put("zhuangtai",zhuangtai);
                    out.write((jsonobject+"\n").getBytes());

                    String Line=reader.readLine();
                    JSONObject object=new JSONObject(Line);
                    int a=object.getInt("postion");

                    Log.d("postion",a+"#################################################################################################3");
                    if(a==99){
                        Intent i=new Intent(DengluActivity.this,FreindActivity.class);
                        startActivity(i);
                    }
                    Message msg = Message.obtain();
                    msg.getData().putString("info1", a+"");
                    handler.sendMessage(msg);

                    out.close();
                    sk.close();


                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public void zhuce(View view) {
        Intent intent =new Intent(DengluActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
