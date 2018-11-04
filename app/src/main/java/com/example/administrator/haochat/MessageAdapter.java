package com.example.administrator.haochat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Mymessage> {
    private List<Mymessage> messages;
    private int res;
    public MessageAdapter( Context context, int resource, List<Mymessage>messages) {
        super(context, resource, messages);
        this.messages=messages;
        this.res=resource;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Mymessage food=messages.get(position);
        //将资源ID变成View
        View view=LayoutInflater
                .from(getContext())
                .inflate(res,parent,false);

        TextView name_tv=view.findViewById(R.id.name);
        TextView message_tv=view.findViewById(R.id.message);
        name_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("john",food.getName());
            }
        });

        message_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("john",food.getMessage()+"");
            }
        });

        name_tv.setText(food.getName());
        message_tv.setText(food.getMessage()+"");

        return view;
    }
}
