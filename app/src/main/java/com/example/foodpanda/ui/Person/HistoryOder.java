package com.example.foodpanda.ui.Person;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.foodpanda.Model.Bills;
import com.example.foodpanda.Utils.Constants;
import com.example.foodpanda.Utils.Global;
import com.example.foodpanda.databinding.ActivityHistoryOderBinding;
import com.example.foodpanda.databinding.FragmentPersonBinding;
import com.example.foodpanda.ui.Adapter.HistoryOderAdapter;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class HistoryOder extends AppCompatActivity {

    private ActivityHistoryOderBinding binding;
    private HistoryOderAdapter adapter;
    private ArrayList<Bills> bills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryOderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bills = new ArrayList<>();
        adapter=new HistoryOderAdapter(bills);
        binding.rcv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.rcv.setAdapter(adapter);
        binding.back.setOnClickListener(v -> finish());

        maincontent();

    }

    private void maincontent() {
        Constants.DATABASE_USER.child(Global.users.getUid()).child("HISTORY_ODER").get()
                .addOnSuccessListener(dataSnapshot -> {
                    for(DataSnapshot data : dataSnapshot.getChildren()){
                        bills.add(0,data.getValue(Bills.class));
                    }

                })
                .addOnFailureListener(e -> Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show())
                .addOnCompleteListener(task -> {
                    if(task.isComplete())
                    {
                        adapter.notifyDataSetChanged();
                    }
                });
    }




}