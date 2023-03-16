package com.example.foodpanda.Model;

public class User {
    private String email;
    private String name;
    private String phone;
    private String address;
    private String urlImg;
    private String uid;
    private int kinAc;

    /*1= admin
        0=0*/


    public User()
    {

    }



    public User(String email, String name, String phone, String uid) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.uid = uid;
        this.kinAc = 0;
    }

    public User( String uid,String email, String name, String phone, String urlImg) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.urlImg = urlImg;
        this.uid = uid;
        this.kinAc = 0;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getKinAc() {
        return kinAc;
    }

    public void setKinAc(int kinAc) {
        this.kinAc = kinAc;
    }
}
