package com.example.administrator.haochat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FriendAdapter extends ArrayAdapter<Friend> {
    private List<Friend> friends;
    private int res;

    public FriendAdapter(Context context, int resource, List<Friend> frends) {
        super(context, resource,frends);
        this.friends=frends;
        this.res=resource;
    }


    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Friend food=friends.get(position);
        //将资源ID变成View
        View view=LayoutInflater
                .from(getContext())
                .inflate(res,parent,false);

        TextView name_tv=view.findViewById(R.id.name);
//        name_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        name_tv.setText(food.getFname());
        return view;
    }
}
