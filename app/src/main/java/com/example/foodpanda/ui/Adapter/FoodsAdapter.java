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

import com.example.foodpanda.databinding.ItemFoodBillBinding;
import com.example.foodpanda.ui.home.FoodDetails;
import com.example.foodpanda.Model.Food;
import com.example.foodpanda.Model.Oder;
import com.example.foodpanda.R;
import com.example.foodpanda.Utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodsAdapter  extends RecyclerView.Adapter {

    ArrayList<Food> foods;
    private ArrayList<Oder> oders;
    private int food;


    public FoodsAdapter(ArrayList<Food> foods) {
        this.foods = foods;
        this.food = 1;
    }

    public FoodsAdapter() {
        this.oders= new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 1)


            return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false));

        return new ItemBillHolder( LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_bill, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(getItemViewType(position)==1)
            ((ItemHolder)holder).bind(foods.get(position));
        else
            ((ItemBillHolder)holder).bind(oders.get(position));
    }

    @Override
    public int getItemCount() {
     return (oders ==null ? foods.size() :  oders.size());
    }
    @Override
    public int getItemViewType(int position) {
        if(food==1)
            return 1;
        return 0;
    }

    /// V tinh bill

    public void SetFood(int food){
        this.food=food;
    }

    public void setOder(ArrayList<Oder> oders){
        this.oders.clear();
        this.oders.addAll(oders);

    }



    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView name, rateText, decription,saleText,soldStatus;
        RatingBar ratingBar;

        public ItemHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.food_img_al);
            name = itemView.findViewById(R.id.food_name_all);
            ratingBar = itemView.findViewById(R.id.food_rate_all);
            rateText = itemView.findViewById(R.id.food_rate_text_all);
            saleText = itemView.findViewById(R.id.sale);
            decription = itemView.findViewById(R.id.food_decr_all);
            soldStatus = itemView.findViewById(R.id.soldOutall);
            itemView.setOnClickListener(this::onClick);
        }

        public void bind(Food food) {
            Picasso.get()
                    .load(food.getImage()).into(imageView);
            name.setText(food.getName());
            rateText.setText("(" + food.getRate().getNumRate() + ")");
            ratingBar.setRating(food.getRate().getRate());
            decription.setText(food.getDecription());
            if (food.getSale_off() ==  -1.0){
                saleText.setVisibility(View.GONE);
            }else {
                saleText.setVisibility(View.VISIBLE);
                saleText.setText("Discount "+food.getSale_off() + "%");
            }

            if (food.getSoldOut()==1)
                soldStatus.setVisibility(View.VISIBLE);

        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), FoodDetails.class);
            intent.putExtra("id", foods.get(getAdapterPosition()).getId() + "");
            v.getContext().startActivity(intent);


        }


        }

        ///tinh bill

        class  ItemBillHolder extends RecyclerView.ViewHolder {
            private ItemFoodBillBinding binding;
            public ItemBillHolder(@NonNull View itemView) {
                super(itemView);
                binding = ItemFoodBillBinding.bind(itemView);
            }

            public void bind(Oder oder) {
                Picasso.get().load(oder.getFood().getImage())
                        .into(binding.img);

                binding.name.setText(oder.getFood().getName());
                binding.amount.setText(String.valueOf(oder.getNum()));
                binding.prices.setText(Utils.doubleToVND(oder.getFood().getPrices()));
                if (oder.getFood().getSale_off() ==  -1.0){
                    binding.sale.setVisibility(View.GONE);

                }else {
                    binding.sale.setVisibility(View.VISIBLE);

                    binding.sale.setText("Discount "+oder.getFood().getSale_off() + "%");
                }
            }

        }


    }



