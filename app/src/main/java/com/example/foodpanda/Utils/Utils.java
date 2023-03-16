package com.example.foodpanda.Utils;

public class Utils {
    public static  String doubleToVND(long x){
        String result = "";
        int az=(int) x;

        while (az/1000>0){
            result = "."+String.format("%03d",az%100)+result;
            az/=1000;
        }
        result=String.format("%d",az%1000)+result;
        return result+"$";
    }
}
