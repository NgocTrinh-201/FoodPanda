package com.example.foodpanda.ui.Cart;
import static com.example.foodpanda.Utils.Global.users;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.foodpanda.Model.Bills;
import com.example.foodpanda.Model.Food;
import com.example.foodpanda.Model.Oder;
import com.example.foodpanda.R;
import com.example.foodpanda.Utils.Constants;
import com.example.foodpanda.Utils.Utils;

import com.example.foodpanda.databinding.FragmentCartBinding;
import com.example.foodpanda.ui.Adapter.CartAdapter;
import com.example.foodpanda.ui.Adapter.CommentFoodAdapter;
import com.example.foodpanda.ui.Adapter.itemCartClick;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Date;


public class Cart extends Fragment {

    private FragmentCartBinding binding;
    private ArrayList<Oder> oders;
    private CartAdapter adapter;
    private long prices =0;

    public Cart() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater,container,false);

        //display cart
        oders = new ArrayList<>();
        adapter = new CartAdapter(oders);
        binding.rcv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        binding.rcv.setAdapter(adapter);
        return binding.getRoot();

    }



    private void buttonEven() {

        binding.checkoutBt.setOnClickListener(v -> {
          createDialog();



        });


    }

    private void createDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.nofi_dialog);
        TextView address, totalCost;
        Button ok, cancel;
        RecyclerView rcv;
        ImageButton img;

        address = dialog.findViewById(R.id.address_nofi);
        totalCost = dialog.findViewById(R.id.toal_cost_nofi);
        ok = dialog.findViewById(R.id.ok_nofi);
        cancel = dialog.findViewById(R.id.cancel_nofi);
        rcv = dialog.findViewById(R.id.rcv_nofi);
        img = dialog.findViewById(R.id.img);
        /////





        ///////
        ArrayList<Food> foods = new ArrayList<>();

        for (Oder oder : oders) {
            foods.add(oder.getFood());
        }

        CommentFoodAdapter adapter = new CommentFoodAdapter(foods);
        rcv.setLayoutManager(new LinearLayoutManager(dialog.getContext(), LinearLayoutManager.HORIZONTAL, false));
        rcv.setAdapter(adapter);
        totalCost.setText(binding.totalPrices.getText().toString());

        cancel.setOnClickListener(v -> dialog.cancel());



        if (users.getAddress() != null && !users.getAddress().isEmpty()) {
            ok.setEnabled(true);
            address.setText(users.getAddress());


        }

        else {
            ok.setEnabled(false);
            address.setHint("No Address!");
        }

        ok.setOnClickListener(v -> {


            updateBill();
            dialog.cancel();

        });


        dialog.show();

    }




    private void updateBill() {
        Bills bills = new Bills(oders,new Date().getTime(),users,prices);
        Constants.DATABASE_USER.child(users.getUid()).child("HISTORY_ODER")
                .child(String.valueOf(bills.getTime()))
                .setValue(bills).addOnSuccessListener(unused -> removeOder())
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Can't creat bill", Toast.LENGTH_SHORT).show() );


    }

    private void removeOder() {
        Constants.DATABASE_USER.child(users.getUid()).child("ODER").removeValue()
                .addOnSuccessListener(unused -> {
                    startActivity(new Intent(getContext(), PayPal.class));


                });
                }










    private void itemEvent() {

        adapter.itemOnclick(new itemCartClick() {
            @Override
            public void onClickIncr(int position) {
                oders.get(position).setNum(oders.get(position).getNum()+1);
                //oders.get(position).setToalCost(oders.get(position).getNum()*oders.get(position).getFood().getPrices());
                adapter.notifyItemChanged(position);
                updateCheckoutBox();
                updateDB(oders.get(position));

            }

            @Override
            public void onClickDesc(int position) {
                if (oders.get(position).getNum()>1){
                    oders.get(position).setNum(oders.get(position).getNum()-1);
                    //oders.get(position).setToalCost(oders.get(position).getNum()*oders.get(position).getFood().getPrices());
                    adapter.notifyItemChanged(position);
                    updateDB(oders.get(position));
                }
                updateCheckoutBox();

            }

            @Override
            public void onClickClear(int position) {
                removeDB(oders.get(position));
                oders.remove(position);
                adapter.notifyDataSetChanged();
                updateCheckoutBox();



            }
        });
    }

    private void removeDB(Oder oder) {
        Constants.DATABASE_USER.child(users.getUid()).child("ODER")
                .child(String.valueOf(oder.getFood().getId())).removeValue();





    }

    private void updateDB(Oder oder) {

        Constants.DATABASE_USER.child(users.getUid()).child("ODER")
                .child(String.valueOf(oder.getFood().getId()))
                .setValue(oder);



    }

    private void updateCheckoutBox() {
        prices =0;
        binding.totalFoods.setText(String.valueOf(oders.size()));
        if (oders.size()>0){
            for (Oder oder : oders){
                prices+=(oder.getNum()*oder.getPricesAtOrder());
            }
            binding.checkoutBt.setEnabled(true);
        }else {
            binding.checkoutBt.setEnabled(false);
        }
        binding.totalPrices.setText(Utils.doubleToVND(prices));
    }

    private void getCarts() {
        Constants.DATABASE_USER.child(users.getUid()).child("ODER")
                .get()
                .addOnCompleteListener(task -> displayCarts(task))
                .addOnFailureListener(e -> Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void displayCarts(Task<DataSnapshot> task) {
        oders.clear();

        if (task.isComplete()){
            for (DataSnapshot data : task.getResult().getChildren()){
                oders.add(0,data.getValue(Oder.class));
            }
            adapter.notifyDataSetChanged();
            updateCheckoutBox();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getCarts();
        updateCheckoutBox();
        itemEvent();
        buttonEven();


    }
}
