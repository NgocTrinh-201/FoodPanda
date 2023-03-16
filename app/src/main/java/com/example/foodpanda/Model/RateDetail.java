package com.example.foodpanda.Model;

public class RateDetail {
    private  int rate;
    private  User user;
    private String commnet;
    private  long date;

    public RateDetail() {
    }

    public RateDetail(int rate, User user, String commnet, long date) {
        this.rate = rate;
        this.user = user;
        this.commnet = commnet;
        this.date = date;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCommnet() {
        return commnet;
    }

    public void setCommnet(String commnet) {
        this.commnet = commnet;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
