package com.example.foodpanda.Utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.nio.file.FileStore;

public class Constants {
    public static final FirebaseDatabase DATABASE = FirebaseDatabase.getInstance("https://eatfood-3debd-default-rtdb.firebaseio.com");
    public static final DatabaseReference DATABASE_USER = FirebaseDatabase.getInstance("https://eatfood-3debd-default-rtdb.firebaseio.com")
            .getReference("USER");


    public   static final FirebaseAuth AUTH = FirebaseAuth.getInstance();
    public static final FirebaseStorage STORE =  FirebaseStorage.getInstance("gs://eatfood-3debd.appspot.com");
}
