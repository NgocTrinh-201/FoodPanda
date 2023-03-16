package com.example.foodpanda.Model;

public class Oder {
    private Food food;
    private int num;
    private long pricesAtOrder;
    public Oder() {

    }
    public Oder(Food food, int num,long pricesAtOrder) {
        this.food = food;
        this.num = num;
        this.pricesAtOrder = pricesAtOrder;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public long getPricesAtOrder() {
        return pricesAtOrder;
    }

    public void setPricesAtOrder(long pricesAtOrder) {
        this.pricesAtOrder = pricesAtOrder;
    }
}
