package com.example.administrator.haochat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class friendquanadapter extends ArrayAdapter<friendquanclass> {
    private  int rid;
    public  friendquanadapter(Context context, int textViewResourceId, List<friendquanclass> objects){
        super(context,textViewResourceId,objects);
        rid=textViewResourceId;
    }


    @Override
    public friendquanclass getItem(int position) {
        return super.getItem(position);
    }

    public View getView(final int position, View converView, ViewGroup parent)
    {
        friendquanclass p=getItem(position);
        View view=LayoutInflater.from(getContext()).inflate(rid,parent,false);
      TextView n=(TextView)view.findViewById(R.id.nr);
      TextView m=(TextView)view.findViewById(R.id.nm);
        Button btn=(Button)view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friendquanclass f=getItem(position);
                String message=f.getNeirong();
                try {
                    InetAddress addr = InetAddress.getByName("192.168.31.140");
//                            String addr=((ApplicationTrans) getApplication()).getToname();
                    Socket sk = new Socket(addr, 30001);
                    OutputStream out = sk.getOutputStream();
                    JSONObject jsonobject = new JSONObject();
                    jsonobject.put("message", message);
                    jsonobject.put("zhuangtai", 7);
                    out.write((jsonobject + "\n").getBytes());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
       n.setText(p.getNeirong());
        m.setText(p.getZannumber());
        return  view;
    }
}
