package com.example.foodpanda.Utils;

import com.example.foodpanda.Model.User;

public class Global {
    public static User users;
    public static void updateUser(){
        Constants.DATABASE_USER.child(Constants.AUTH.getCurrentUser().getUid())
                .get().addOnSuccessListener(dataSnapshot -> {
                    users = dataSnapshot.getValue(User.class);
                });
    }
}
