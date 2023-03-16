package com.example.foodpanda.Model;

import java.util.Date;

public class Food {
    private long id;
    private String name;
    private long prices;

    private String decription;
    private String image;

    private String kindOfFooad;
    private  Rate rate;
    private double sale_off = -1.0;
    private int soldOut =0; //còn hàng
 /*   1/Drink
    2/Dastfood
    3/Deal
    4/An  */

    public Food() {
    }


    public Food(long id, String name, long prices, String decription, String image, String kindOfFooad) {
        this.id = id;
        this.name = name;
        this.prices = prices;
        this.decription = decription;
        this.image = image;
        this.kindOfFooad = kindOfFooad;

    }



    public Food( String name, long prices, String decription, String image,String kindOfFooad,Rate rate) {
        this.id = (new Date()).getTime();
        this.name = name;
        this.prices = prices;
        this.decription = decription;
        this.image = image;
        this.rate = rate;

        this.kindOfFooad = kindOfFooad;

    }

    public int getSoldOut() {
        return soldOut;
    }

    public void setSoldOut(int soldOut) {
        this.soldOut = soldOut;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrices() {
        return prices;
    }

    public void setPrices(long prices) {
        this.prices = prices;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKindOfFooad() {
        return kindOfFooad;
    }

    public void setKindOfFooad(String kindOfFooad) {
        this.kindOfFooad = kindOfFooad;
    }

    public double getSale_off() {
        return sale_off;
    }

    public void setSale_off(double sale_off) {
        this.sale_off = sale_off;
    }

    /*  public void setKindOfFooad(String kindOfFooad) {
        this.kindOfFooad = kindOfFooad;
    }*/
}
