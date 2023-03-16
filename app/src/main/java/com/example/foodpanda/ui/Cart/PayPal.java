package com.example.foodpanda.ui.Cart;

import static com.example.foodpanda.Utils.Global.users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.foodpanda.Model.Bills;
import com.example.foodpanda.Model.Oder;
import com.example.foodpanda.Utils.Constants;
import com.example.foodpanda.databinding.ActivityPayPalBinding;
import com.example.foodpanda.ui.Cart.Delivery;
import com.example.foodpanda.ui.Cart.MoMo;

import java.util.ArrayList;
import java.util.Date;

public class PayPal extends AppCompatActivity {
    private ActivityPayPalBinding binding;
    private ArrayList<Oder> oders;
    private static long prince = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayPalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.okStatus.setOnClickListener(view -> {
            startActivity(new Intent(this, Delivery.class));

            finish();

        });
        binding.cancelStatus.setOnClickListener(view -> {
            startActivity(new Intent(this, MoMo.class));

        });


    }

}