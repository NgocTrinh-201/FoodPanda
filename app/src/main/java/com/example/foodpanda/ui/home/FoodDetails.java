package com.example.foodpanda.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodpanda.Model.Food;
import com.example.foodpanda.Model.Oder;
import com.example.foodpanda.Utils.Constants;
import com.example.foodpanda.Utils.Global;
import com.example.foodpanda.Utils.Utils;
import com.squareup.picasso.Picasso;

public class FoodDetails extends AppCompatActivity {
    private com.example.foodpanda.databinding.ActivityFoodDetailsBinding binding;

    private  int numOfFoods = 1;
    private Button div,add;
    private TextView num,toalCost,saleText;
    private FrameLayout cart;
    private Food f;
    private long prices = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.foodpanda.databinding.ActivityFoodDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ///lay dta
        Constants.DATABASE.getReference("FOODS").child(getIntent().getStringExtra("id"))
                .get().addOnSuccessListener(dataSnapshot -> {
                     f = dataSnapshot.getValue(Food.class);
                    binding.nameDeail.setText(f.getName());

                    binding.rateDeail.setRating(f.getRate().getRate());
                    binding.numrateDeail.setText("("+f.getRate().getNumRate()+")");
                    binding.priceDeail.setText(Utils.doubleToVND(f.getPrices()));
                    binding.descriptionDeail.setText(f.getDecription());
                    Picasso.get().load(f.getImage()).into(binding.imgDeail);
                    if (f.getSale_off() == -1.0){
                        binding.sale.setVisibility(View.GONE);
                        prices = f.getPrices();
                    }else {
                        binding.sale.setVisibility(View.VISIBLE);
                        saleText.setText(Utils.doubleToVND((long) (f.getPrices()*(1-f.getSale_off()/100))));
                        prices = (long) (f.getPrices()*(1-f.getSale_off()/100));
                    }
                    binding.toalCost.setText(Utils.doubleToVND(prices));

                })
                        .addOnCompleteListener(task -> {
                            if(task.isComplete()){
                                if (f.getSoldOut() == 0){
                                    binding.navDeail.setVisibility(View.VISIBLE);
                                }
                                binding.cmtCtn.setVisibility(View.VISIBLE);
                            }
                        });


        unit();


        run();

        buttonEven();

    }

    private void buttonEven() {

        binding.cmtCtn.setOnClickListener(v -> {
           RateAndComent.food = f;
           startActivity(new Intent(FoodDetails.this,RateAndComent.class));
        });
    }

    private void unit() {
        div = binding.desc;
        add = binding.incr;
        num = binding.number;
        toalCost = binding.toalCost;
        saleText =binding.sale;
        cart = binding.cart;
        div.setEnabled(false);
    }




    private void run() {

        add.setOnClickListener(v -> {
            numOfFoods++;
            if(numOfFoods>1)
            {
                div.setEnabled(true);
            }
            num.setText(numOfFoods+"");
            toalCost.setText((Utils.doubleToVND(prices*numOfFoods))+"");
        });

        div.setOnClickListener(v -> {
            numOfFoods--;
            if(numOfFoods==1){
                div.setEnabled(false);
            }else {
                div.setEnabled(true);
            }
            num.setText(numOfFoods+"");
            toalCost.setText((Utils.doubleToVND(prices*numOfFoods))+"");
        });
        ///them vao gio hanga

        cart.setOnClickListener(v -> {
            Oder oder = new Oder(f,numOfFoods,prices);
           Constants.DATABASE_USER.child(Global.users.getUid())
                   .child("ODER")
                   .child(String.valueOf(f.getId()))
                   .setValue(oder)
                   .addOnSuccessListener(unused -> {
                       Toast.makeText(this, "Add to cart", Toast.LENGTH_SHORT).show();

                   })
                   .addOnFailureListener(e -> {
                       Toast.makeText(this, "Fail add to cart", Toast.LENGTH_SHORT).show();

                   });




        });


    }



}