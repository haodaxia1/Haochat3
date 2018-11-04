package com.example.administrator.haochat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class gerenziliao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenziliao);
    }

    public void friendquan(View view) {
        Intent ii=new Intent(gerenziliao.this,friendquan.class);
        startActivity(ii);
    }

    public void sendmsg(View view) {
        Intent ii=new Intent(gerenziliao.this,chat.class);
        startActivity(ii);
    }
}
