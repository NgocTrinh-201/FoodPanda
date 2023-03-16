package com.example.foodpanda.ui.Cart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.foodpanda.databinding.ActivityDeliveryBinding;
import com.example.foodpanda.ui.home.HomeFragment;

public class Delivery extends AppCompatActivity {

    private ActivityDeliveryBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeliveryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.back.setOnClickListener(v -> finish());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //startActivity(new Intent(Delivery.this, HomeFragment.class));
           finish();
            }
        },3000);

    }
}