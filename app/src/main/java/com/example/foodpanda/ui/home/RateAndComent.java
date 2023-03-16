package com.example.foodpanda.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.foodpanda.Model.Food;
import com.example.foodpanda.Model.Rate;
import com.example.foodpanda.Model.RateDetail;
import com.example.foodpanda.Model.User;
import com.example.foodpanda.Utils.Constants;

import com.example.foodpanda.databinding.ActivityRateAndComentBinding;
import com.example.foodpanda.databinding.ActivityRegisterBinding;
import com.example.foodpanda.ui.Adapter.RateAdapter;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class RateAndComent extends AppCompatActivity {

    private ActivityRateAndComentBinding binding;
    public  static Food food =null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRateAndComentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainCintent();
        binding.back.setOnClickListener(view -> finish());



    }
    private void mainCintent() {
        binding.rate.setText(String.format("%.1f",food.getRate().getRate()));
        binding.rating.setRating(food.getRate().getRate());
        binding.numrate.setText(String.valueOf(food.getRate().getNumRate()));

        //thongke


        binding.rate5.setProgress(    (int)((food.getRate().getNumFiveStar()*1f/food.getRate().getNumRate())*100));
        binding.rate4.setProgress(    (int)((food.getRate().getNumFourStar()*1f/food.getRate().getNumRate())*100));
        binding.rate3.setProgress(    (int)((food.getRate().getNumThereStar()*1f/food.getRate().getNumRate())*100));
        binding.rate2.setProgress(    (int)((food.getRate().getNumTwoStar()*1f/food.getRate().getNumRate())*100));
        binding.rate1.setProgress(    (int)((food.getRate().getNumOneStar()*1f/food.getRate().getNumRate())*100));


        ArrayList<RateDetail> rateDetails = new ArrayList<>();
        RateAdapter adapter = new RateAdapter(rateDetails);
        binding.rcv.setLayoutManager(new LinearLayoutManager(this));
        binding.rcv.setAdapter(adapter);

        Constants.DATABASE.getReference("FOODS").child(String.valueOf(food.getId())).child("rate")
                .child("rateDetails").get()
                .addOnSuccessListener(dataSnapshot -> {
                    for(DataSnapshot data : dataSnapshot.getChildren()){
                        rateDetails.add(0,data.getValue(RateDetail.class));
                    }

                })
                .addOnCompleteListener(task -> adapter.notifyDataSetChanged())
                .addOnFailureListener(e -> Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show());

    }
}