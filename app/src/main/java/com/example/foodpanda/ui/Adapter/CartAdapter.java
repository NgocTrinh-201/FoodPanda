package com.example.foodpanda.ui.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.foodpanda.Model.Oder;
import com.example.foodpanda.Utils.Utils;
import com.example.foodpanda.databinding.ItemCartBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter {

    private ArrayList<Oder> oders;
    private ItemCartBinding binding;
    private itemCartClick itemCartClick;

    public CartAdapter(ArrayList<Oder> oders) {
        this.oders = oders;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ItemHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Oder oder = oders.get(position);
        ((ItemHolder) holder).bind(oder);
    }

    public void itemOnclick(itemCartClick itemCartClick){
        this.itemCartClick = itemCartClick;
    }

    @Override
    public int getItemCount() {
        return oders.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        private ItemCartBinding bin;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            bin = ItemCartBinding.bind(itemView);

        }
        public void bind(Oder oder){
            bin.name.setText(oder.getFood().getName());
            bin.price.setText(Utils.doubleToVND(oder.getPricesAtOrder()));
            bin.rate.setRating(oder.getFood().getRate().getRate());
            bin.numrate.setText("("+oder.getFood().getRate().getNumRate()+")");
            bin.num.setText(String.valueOf(oder.getNum()));
            Picasso.get()
                    .load(oder.getFood().getImage())
                    .into(bin.img);
            bin.remove.setOnClickListener(view -> itemCartClick.onClickClear(getAdapterPosition()));
            bin.incr.setOnClickListener(view -> itemCartClick.onClickIncr(getAdapterPosition()));
            bin.desc.setOnClickListener(view -> itemCartClick.onClickDesc(getAdapterPosition()));
        }
    }

}