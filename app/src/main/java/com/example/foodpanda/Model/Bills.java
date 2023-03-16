package com.example.foodpanda.Model;

import java.util.ArrayList;

public class Bills {
  private   ArrayList<Oder> oders;
  private  long time;
  private  User user;
  private long totalPayment;

    public Bills() {
    }



    public Bills(ArrayList<Oder> oders, long time, User user,long totalPayment) {
        this.oders = oders;
        this.time = time;
        this.user = user;
        this.totalPayment = totalPayment;
    }

    public long getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(long totalPayment) {
        this.totalPayment = totalPayment;
    }

    public ArrayList<Oder> getOders() {
        return oders;
    }

    public void setOders(ArrayList<Oder> oders) {
        this.oders = oders;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
