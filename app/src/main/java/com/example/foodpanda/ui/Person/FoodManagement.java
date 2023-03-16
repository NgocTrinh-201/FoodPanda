package com.example.foodpanda.ui.Person;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.foodpanda.Model.Food;
import com.example.foodpanda.Utils.Constants;
import com.example.foodpanda.databinding.ActivityFoodManagementBinding;
import com.example.foodpanda.ui.Adapter.FoodManagementAdapter;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class FoodManagement extends AppCompatActivity {
    private ActivityFoodManagementBinding binding;

    private FoodManagementAdapter adapter;
    private ArrayList<Food> foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodManagementBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        foods = new ArrayList<>();
        adapter = new FoodManagementAdapter(foods);
        binding.rcv.setLayoutManager(new LinearLayoutManager(this));
        binding.rcv.setAdapter(adapter);
        binding.addFood.setOnClickListener(v -> startActivity(new Intent(this, AddFood.class)));
        binding.close.setOnClickListener(v -> finish());



    }

    //ham tu dong cap nhat thuc an khi them moi

    @Override   /*@Override la ghi đề*/
    protected void onPostResume() {
        super.onPostResume();
        //lay data
        Constants.DATABASE.getReference("FOODS") .get()
                .addOnSuccessListener(dataSnapshot -> addToArray(dataSnapshot.getChildren()))
                .addOnCompleteListener(task -> {
                    if(task.isComplete())
                        adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show());


    }

    private void addToArray(Iterable<DataSnapshot> children) {
        for(DataSnapshot data : children){
            foods.add(0,data.getValue(Food.class));
        }
    }


}