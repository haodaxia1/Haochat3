package com.example.administrator.haochat;

public class friendquanclass {
    private String neirong;
    private String zannumber;
    public static int p;
    private  String name;
    public friendquanclass(String neirong,String zannumber){
        this.neirong=neirong;
        this.zannumber=zannumber;}

//    public friendquanclass(String neirong, String zannumber, String name) {
//        this.neirong = neirong;
//        this.zannumber = zannumber;
//        this.name = name;
//    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String getNeirong() {
        return neirong;
    }

    public void setNeirong(String neirong) {
        this.neirong = neirong;
    }

    public String getZannumber() {
        return zannumber;
    }

    public void setZannumber(String zannumber) {
        this.zannumber = zannumber;
    }
}
