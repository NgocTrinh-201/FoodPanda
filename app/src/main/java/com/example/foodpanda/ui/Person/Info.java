package com.example.foodpanda.ui.Person;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.foodpanda.databinding.ActivityInfoBinding;

public class Info extends AppCompatActivity {


    private ActivityInfoBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.back.setOnClickListener(view -> finish());


        binding.map.setOnClickListener(view -> {
            startActivity(new Intent(this, Mappp.class));
        });
    }
}