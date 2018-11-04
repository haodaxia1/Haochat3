package com.example.administrator.haochat;

import android.app.Application;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ApplicationTrans extends Application {
    private String fromname;
    private String toname;
    private String addresss="192.168.43.65";

    public String getAddresss() {
        return addresss;
    }

    public String getToname() {
        return toname;
    }

    public void setToname(String toname) {
        this.toname = toname;
    }

    public String getFromname() {
        return fromname;
    }

    public void setFromname(String fromname) {
        this.fromname = fromname;
    }
}
