package com.example.foodpanda.ui.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpanda.ui.home.FoodDetails;
import com.example.foodpanda.Model.Food;
import com.example.foodpanda.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CommentFoodAdapter extends RecyclerView.Adapter {

    ArrayList<Food> foods ;

    public  CommentFoodAdapter (ArrayList<Food>foods){
        this.foods=foods;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iyem_coment_food,parent,false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((ItemHolder)holder).bind(foods.get(position));
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    class  ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView,saleText,soldOut,id,id4;
        public  ItemHolder(View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.img_food);
            textView = itemView.findViewById(R.id.name_food);
            saleText = itemView.findViewById(R.id.sale);
            soldOut = itemView.findViewById(R.id.sold_Out_rcm);
            id = itemView.findViewById(R.id.id);
            id4 = itemView.findViewById(R.id.id4);
            itemView.setOnClickListener(this::onClick);

        }
        public  void  bind(Food food){
            Picasso.get()
                    .load(food.getImage()).into(imageView);
            textView.setText(food.getName());
            if (food.getSale_off() ==  -1.0){
                saleText.setVisibility(View.GONE);
                id.setVisibility(View.GONE);
                id4.setVisibility(View.VISIBLE);

            }else {
                saleText.setVisibility(View.VISIBLE);
                id.setVisibility(View.GONE);
                saleText.setText("Discount "+food.getSale_off() + "%");
            }
            if (food.getSoldOut() == 0){
                soldOut.setVisibility(View.GONE);
                id.setVisibility(View.VISIBLE);
            }else {
                soldOut.setVisibility(View.VISIBLE);
                id.setVisibility(View.GONE);
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
