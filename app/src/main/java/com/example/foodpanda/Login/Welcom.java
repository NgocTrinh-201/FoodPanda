package com.example.foodpanda.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.foodpanda.ui.home.MainActivity;
import com.example.foodpanda.Model.Food;
import com.example.foodpanda.Model.Rate;
import com.example.foodpanda.Model.RateDetail;
import com.example.foodpanda.Model.User;
import com.example.foodpanda.R;
import com.example.foodpanda.Utils.Constants;
import com.example.foodpanda.Utils.Global;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Welcom extends AppCompatActivity {
    protected static final long Time_delay = 1000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);

    new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Constants.AUTH.getCurrentUser() == null) {
                    startActivity(new Intent(Welcom.this, LoginScreen.class));
                    finishAffinity();
                } else {
                    Constants.DATABASE_USER.child(Constants.AUTH.getCurrentUser().getUid())
                            .get()
                            .addOnSuccessListener(dataSnapshot -> {
                                Global.users = dataSnapshot.getValue(User.class);
                                startActivity(new Intent(Welcom.this, MainActivity.class));
                                finishAffinity();
                            })

                            .addOnFailureListener(e -> Toast.makeText(Welcom.this, "Can't get user data", Toast.LENGTH_SHORT).show());
                }
            }
        }, Time_delay);

/*
        ArrayList<Food> foods = new ArrayList<>();
        Constants.DATABASE.getReference("FOODS").get()
                .addOnCompleteListener(task -> {
                    if (task.isComplete()) {

                        for (DataSnapshot data : task.getResult().getChildren()) {
                            Food f = data.getValue(Food.class);
                            Rate rate = new Rate();
                            rate.abc(rand(),rand(),rand(),rand(),rand(),"COMMENT");
                            f.setRate(rate);
                            foods.add(f);
                        }
                        Constants.DATABASE.getReference("FOODS").setValue(foods);


                    }
                });*/
       // abc();


    }
    private void abc(){
        ArrayList<Food> foods = new ArrayList<>();
        Constants.DATABASE.getReference("FOODS").get()
                .addOnCompleteListener(task -> {

                    if (task.isComplete()) {
                        for (DataSnapshot data : task.getResult().getChildren()) {
                            Food f = data.getValue(Food.class);
                            Rate rate = new Rate();
                            rate.abc(rand(), rand(), rand(), rand(), rand());
                            for(int i=0;i<rate.getNumOneStar();i++){
                                RateDetail re = new RateDetail(1,randUser(),randCmt(),randRtae());
                                rate.addRate(re);
                            }
                            for(int i=0;i<rate.getNumTwoStar();i++){
                                RateDetail re = new RateDetail(2,randUser(),randCmt(),randRtae());
                                rate.addRate(re);
                            }
                            for(int i=0;i<rate.getNumThereStar();i++){
                                RateDetail re = new RateDetail(3,randUser(),randCmt(),randRtae());
                                rate.addRate(re);
                            }
                            for(int i=0;i<rate.getNumFourStar();i++){
                                RateDetail re = new RateDetail(4,randUser(),randCmt(),randRtae());
                                rate.addRate(re);
                            }
                            for(int i=0;i<rate.getNumFiveStar();i++){
                                RateDetail re = new RateDetail(5,randUser(),randCmt(),randRtae());
                                rate.addRate(re);
                            }
                            f.setRate(rate);
                            foods.add(f);

                        }
                        Constants.DATABASE.getReference("FOODS").setValue(foods);


                    }


                });
    }
    private int rand() {
        Random random = new Random();
        return random.nextInt(20) + 1;

    }
    private  long randRtae(){
        Random random = new Random();
        return  (random.nextInt(1000000)+(new Date()).getTime());

    }
    String [] name ={"Jone","Jeny","Map","Lissa","Tony","Elsa"};

    String [] url = {
            "https://lucdia2.vn/tai-hinh-nen-cute/imager_53766.jpg",
            "https://demoda.vn/wp-content/uploads/2022/01/hinh-nen-cute-co-chu.jpg",
            "https://img4.thuthuatphanmem.vn/uploads/2020/05/07/hinh-anh-cute-dep-nhat_093404024.jpg",
            "https://hinhnen123.com/wp-content/uploads/2021/10/Tong-hop-88-hinh-nen-xanh-duong-cute-dep-tuyet-voi-34.jpg",
            "https://phunugioi.com/wp-content/uploads/2020/02/hinh-anh-cute.jpg",
            "https://anhdepfree.com/wp-content/uploads/2021/01/hinh-anh-cute-dang-yeu.jpg"
    };



    String[] cmt ={
            "so delicious",
            "so great",
            "very delicious clean and fresh",
            "only eat here",
            "quite delicious",
            "cheap tasty tonic"
    };

    String randCmt(){
        return  cmt[(new Random()).nextInt(6)];


    }
    String randName(){
        int x = (new Random()).nextInt(6);
        return  name[x];
    }
    String randUrl(){
        int x = (new Random()).nextInt(6);
        return  url[x];
    }
    private  User randUser(){
        return  new User("testID","testEmail",randName(),"0123456789",randUrl());

    }
}

