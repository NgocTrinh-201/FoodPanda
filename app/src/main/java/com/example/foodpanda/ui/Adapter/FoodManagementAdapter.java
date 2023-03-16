package com.example.foodpanda.ui.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpanda.Utils.Utils;
import com.example.foodpanda.ui.Person.AddFood;
import com.example.foodpanda.Model.Food;
import com.example.foodpanda.R;
import com.example.foodpanda.Utils.Constants;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class FoodManagementAdapter extends FoodsAdapter{

    public FoodManagementAdapter(ArrayList<Food> foods) {
        super(foods);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoodManagementAdapter.ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((FoodManagementAdapter.ItemHolder) holder).bind(foods.get(position));
    }

    class  ItemHolder extends FoodsAdapter.ItemHolder {
        Context context;

        public ItemHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
        }

        @Override
        public void onClick(View v) {
            PopupMenu menu = new PopupMenu(v.getContext(),v);
            menu.getMenuInflater().inflate(R.menu.food_management_menu,menu.getMenu());
            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case  R.id.Edit:
                            AddFood.food = foods.get(getAdapterPosition());
                            v.getContext().startActivity(new Intent(v.getContext(), AddFood.class));
                            break;

                        case  R.id.Delete:
                            deleteFood(getAdapterPosition());
                            break;
                        case R.id.sale_off:
                            showDialogSaleOff(foods.get(getAdapterPosition()));
                            break;
                        case R.id.SoldOutMenu:
                            showDialogFoodStatus(foods.get(getAdapterPosition()));
                            break;
                        case  R.id.DeleteSale:
                            deleteSale(getAdapterPosition());
                            break;


                    }
                    return true;
                }
            });
            menu.show();
        }

        private void deleteSale(int adapterPosition) {
            Constants.DATABASE.getReference("FOODS").child(String.valueOf(foods.get(adapterPosition).getId())).child("sale_off")
                    .removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if(error==null){
                                foods.remove(adapterPosition);
                                FoodManagementAdapter.this.notifyDataSetChanged();

                                Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();


                            }
                        }
                    });
        }

        private void showDialogFoodStatus(Food food) {
            Dialog foodStatus = new Dialog(context);
            foodStatus.setContentView(R.layout.food_status);
            foodStatus.show();
            RadioGroup radioGroup = foodStatus.findViewById(R.id.radio_bt);
            Button ok = foodStatus.findViewById(R.id.ok_status);
            Button cancel = foodStatus.findViewById(R.id.cancel_status);
            cancel.setOnClickListener(view -> foodStatus.dismiss());
            ok.setOnClickListener(view -> {
                int status = 0;
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.rdb1:
                        status = 0;
                        break;
                    case R.id.rdb2:
                        status = 1;
                        break;
                }
                Constants.DATABASE.getReference("FOODS").child(String.valueOf(food.getId())).child("soldOut").setValue(status)
                        .addOnCompleteListener(task -> Toast.makeText(context, "Succeful", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show());

            });
        }

        private void showDialogSaleOff(Food food) {
            Dialog saleOffDialg = new Dialog(context);
            saleOffDialg.setContentView(R.layout.sale_off);
            saleOffDialg.show();
            Button okButton, cancelButton;
            TextView oldPrices, newPrices;
            EditText saleOff;
            // Anh xa
            okButton = saleOffDialg.findViewById(R.id.ok_sale_off_dialog);
            cancelButton = saleOffDialg.findViewById(R.id.cancel_sale_of_dialog);
            oldPrices = saleOffDialg.findViewById(R.id.old_price);
            newPrices = saleOffDialg.findViewById(R.id.new_price);
            saleOff = saleOffDialg.findViewById(R.id.sale_off_edt);
            ///
            oldPrices.setText(Utils.doubleToVND(food.getPrices()));
            saleOff.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {



                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                   if (!editable.toString().isEmpty()) {
                       double percentSaleOff = Double.parseDouble(editable.toString());
                       newPrices.setText(Utils.doubleToVND((long) (food.getPrices() * (1 - (percentSaleOff/100)))));
                   }


                }
            });
            cancelButton.setOnClickListener(view -> saleOffDialg.cancel());//cach viet gon lambda
            okButton.setOnClickListener(view -> {
               Constants.DATABASE.getReference("FOODS").child(String.valueOf(food.getId())).child("sale_off").setValue(Double.parseDouble(saleOff.getText().toString()))
                       .addOnCompleteListener(task -> Toast.makeText(context, "Succeful", Toast.LENGTH_SHORT).show())
                       .addOnFailureListener(e -> Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show());
            });
        }

        private void deleteFood(int adapterPosition) {
            Constants.DATABASE.getReference("FOODS").child(String.valueOf(foods.get(adapterPosition).getId()))
                    .removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if(error==null){
                                foods.remove(adapterPosition);
                               FoodManagementAdapter.this.notifyDataSetChanged();

                                Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();


                            }
                        }
                    });

        }
    }

}
