package com.example.administrator.haochat;

public class Friend {
    private String mname;
    private String fname;

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public Friend(String mname, String fname) {
        this.mname = mname;
        this.fname = fname;
    }

    public Friend() {
    }

    public Friend(String fname) {
        this.fname = fname;
    }
}
