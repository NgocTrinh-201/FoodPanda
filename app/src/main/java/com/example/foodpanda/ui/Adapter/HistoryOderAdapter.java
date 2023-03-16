package com.example.foodpanda.ui.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpanda.ui.Cart.BillDeatail;
import com.example.foodpanda.Model.Bills;
import com.example.foodpanda.Model.Oder;
import com.example.foodpanda.Utils.Utils;
import com.example.foodpanda.databinding.ItemHistoryOdersBinding;

import java.util.ArrayList;
import java.util.Date;

public class HistoryOderAdapter extends RecyclerView.Adapter<HistoryOderAdapter.ItemHolder> {

    private ArrayList<Bills> bills;

    public HistoryOderAdapter(ArrayList<Bills> bills) {
        this.bills = bills;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //cach1
        return new ItemHolder(ItemHistoryOdersBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false).getRoot());

        //cach2
        //return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_oders,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
            holder.bind(bills.get(position));
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    protected class ItemHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        private ItemHistoryOdersBinding binding;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemHistoryOdersBinding.bind(itemView);
            //cach1
            binding.getRoot().setOnClickListener(this::onClick);

            /*//cach2
            itemView.setOnClickListener(this::onClick);*/

        }

        public  void bind(Bills bills){
            binding.addressHis.setText("Delivered to :  "+bills.getUser().getAddress());

            //tinh tien
            long cost = 0;
            for (Oder oder : bills.getOders()){
                cost+=oder.getPricesAtOrder() * oder.getNum();
            }
            binding.pricesHis.setText(Utils.doubleToVND(cost));
            //ngay gio
            Date date = new Date();
            date.setTime(bills.getTime());
            binding.dateHis.setText(date.toLocaleString());

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), BillDeatail.class);
            intent.putExtra(BillDeatail.KEY,String.valueOf(bills.get(getAdapterPosition()).getTime()));
            v.getContext().startActivity(intent);

        }
    }
}
