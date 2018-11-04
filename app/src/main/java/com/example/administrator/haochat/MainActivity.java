package com.example.administrator.haochat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private BufferedReader in=null;
    private PrintWriter out=null;
    private Handler handler;
    private String content="";
    private Socket socket=null;
    private EditText edt_password;
    private EditText edt_username;
    public static  int zhuangtai=0;


//    private ImageView mImage;
//    private Button mAddImage;
//    private Bitmap mBitmap;
//    protected static final int CHOOSE_PICTURE = 0;
//    protected static final int TAKE_PICTURE = 1;
//    protected static Uri tempUri;
//    private static final int CROP_SMALL_PICTURE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.btn);
        edt_username=findViewById(R.id.edt_username);
        edt_password=findViewById(R.id.edt_password);
        Button btn=findViewById(R.id.btn);

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =new Intent(MainActivity.this,DengluActivity.class);
//                startActivity(intent);
//            }
//        });



//        initUI();
//        initListeners();
//       handler=new Handler(){
//           @Override
//           public void handleMessage(Message msg) {
//               edt_username.setText(msg.getData().getString("info"));
//           }
//       };
//        new Thread(MainActivity.this).start();
    }



    public void commit(View view) {
        new Thread(){
            @Override
            public void run() {
                try {
                    zhuangtai=3;
                    InetAddress addr= InetAddress.getByName("192.168.43.65");
//                    String addr=((ApplicationTrans) getApplication()).getToname();
                    Socket sk=new Socket(addr,30001);
                    OutputStream out=sk.getOutputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(sk.getInputStream()));
                    String username=edt_username.getText().toString();
                    String password=edt_password.getText().toString();

//                  user u=new user();
//                  u.setUsername(username);
//                  u.setPassword(password);
                    String jsonresult="";
                    JSONObject jsonobject=new JSONObject();
                    jsonobject.put("username",username);
                    jsonobject.put("password",password);
                    jsonobject.put("zhuangtai",zhuangtai);


                    out.write((jsonobject+"\n").getBytes());


//                  String line=reader.readLine();
//                  Message msg=Message.obtain();
//                  msg.getData().putString("info",line);
//                  handler.sendMessage(msg);
                    out.close();
                    sk.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();

        Intent i=new Intent(MainActivity.this,DengluActivity.class);
        startActivity(i);
    }

}

