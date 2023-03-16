package com.example.foodpanda.ui.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpanda.ui.home.FoodDetails;
import com.example.foodpanda.Model.Food;
import com.example.foodpanda.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BreakfastRecomAdapter  extends RecyclerView.Adapter {

    ArrayList<Food> foods ;

    public  BreakfastRecomAdapter (ArrayList<Food>foods){
        this.foods=foods;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brealfast_recom,parent,false);

        return new BreakfastRecomAdapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((BreakfastRecomAdapter.ItemHolder)holder).bind(foods.get(position));
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    class  ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView name,rateText,saleText,soldOut,id1;
        RatingBar ratingBar;

        public  ItemHolder(View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.breafast_img);
            name = itemView.findViewById(R.id.breafast_name);
            ratingBar = itemView.findViewById(R.id.breafast_rate);
            rateText = itemView.findViewById(R.id.breafast_rete_text);
            saleText = itemView.findViewById(R.id.sale);
            soldOut = itemView.findViewById(R.id.sold_Out_rcm);
            id1 = itemView.findViewById(R.id.id1);
                itemView.setOnClickListener(this::onClick);
        }
        public  void  bind(Food food){
            Picasso.get()
                    .load(food.getImage()).into(imageView);
            name.setText(food.getName());
            rateText.setText("("+food.getRate().getNumRate()+")");
            ratingBar.setRating(food.getRate().getRate());
            if (food.getSale_off() ==  -1.0){
                saleText.setVisibility(View.GONE);
                id1.setVisibility(View.VISIBLE);
            }else {
                saleText.setVisibility(View.VISIBLE);
                id1.setVisibility(View.GONE);
                saleText.setText("Discount "+food.getSale_off() + "%");
            }
            if (food.getSoldOut() == 0){
                soldOut.setVisibility(View.GONE);
                id1.setVisibility(View.VISIBLE);
            }else {
                soldOut.setVisibility(View.VISIBLE);
                id1.setVisibility(View.GONE);
            }

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), FoodDetails.class);
            intent.putExtra("id",foods.get(getAdapterPosition()).getId()+"");
            v.getContext().startActivity(intent);
        }
    }
}
