package com.example.foodpanda.ui.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpanda.Model.Rate;
import com.example.foodpanda.Model.RateDetail;
import com.example.foodpanda.databinding.ItemRateBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class RateAdapter extends RecyclerView.Adapter <RateAdapter.ItemHolder >{
    private ArrayList<RateDetail> rateDetails;

    public RateAdapter(ArrayList<RateDetail> rateDetails) {
        this.rateDetails = rateDetails;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(ItemRateBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {

        holder.bind(rateDetails.get(position));
    }

    @Override
    public int getItemCount() {
        return rateDetails.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        private ItemRateBinding binding;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemRateBinding.bind(itemView);
        }

        public void bind(RateDetail rate){

                binding.name.setText(rate.getUser().getName());
            binding.rate.setRating(rate.getRate());
            binding.coment.setText(rate.getCommnet());
                Picasso.get().load(rate.getUser().getUrlImg())
                        .into(binding.img);
            Date d = new Date();
            d.setTime(rate.getRate());

        }
    }
}
