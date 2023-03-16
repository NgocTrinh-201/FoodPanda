package com.example.foodpanda.ui.Cart;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodpanda.Model.Bills;
import com.example.foodpanda.Model.Food;
import com.example.foodpanda.Model.Oder;
import com.example.foodpanda.Utils.Constants;
import com.example.foodpanda.Utils.Global;
import com.example.foodpanda.Utils.Utils;
import com.example.foodpanda.databinding.ActivityBillDetailBinding;
import com.example.foodpanda.ui.Adapter.FoodsAdapter;

import java.util.ArrayList;
import java.util.Date;

public class BillDeatail extends AppCompatActivity {

    private ActivityBillDetailBinding binding;
    public static  final  String KEY="TIME";
    ArrayList<Oder> oders ;
    FoodsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityBillDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        oders = new ArrayList<>();
        adapter= new FoodsAdapter();
        adapter.SetFood(0);
        adapter.setOder(oders);
        binding.rcv.setLayoutManager(new LinearLayoutManager(this));
        binding.rcv.setAdapter(adapter);


        naimConten();
    }

    private void naimConten() {
        Constants.DATABASE_USER.child(Global.users.getUid()).child("HISTORY_ODER")
                .child(getIntent().getStringExtra(KEY))
                .get().addOnSuccessListener(dataSnapshot -> disPlay(dataSnapshot.getValue(Bills.class)) )
                .addOnFailureListener(e -> Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show())
                .addOnCompleteListener(task ->{
                    if(task.isComplete()){
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void disPlay(Bills value) {
        binding.cosName.setText(value.getUser().getName());
        binding.cosPhone.setText(String.valueOf(value.getUser().getPhone()));
        binding.cosEmail.setText(value.getUser().getEmail());
        Date date = new Date();
        date.setTime(value.getTime());
        binding.cosDate.setText(date.toLocaleString());

        adapter.setOder(value.getOders());
        adapter.notifyDataSetChanged();


        binding.totalBillBill.setText(Utils.doubleToVND(value.getTotalPayment()));



    }
}
