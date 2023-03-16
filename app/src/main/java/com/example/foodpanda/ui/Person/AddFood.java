package com.example.foodpanda.ui.Person;

import static com.example.foodpanda.Utils.Global.users;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodpanda.Compoment.Spinner;
import com.example.foodpanda.Model.Food;
import com.example.foodpanda.Model.Rate;
import com.example.foodpanda.Utils.Constants;
import com.example.foodpanda.databinding.ActivityAddFoodBinding;
import com.example.foodpanda.ui.Cart.Delivery;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AddFood extends AppCompatActivity {
    private ActivityAddFoodBinding binding;
    private String kindOfFooad;
    private ArrayList<Food> foods;
    private Uri uri;
    public static Food food;
    private final  int CHOOSE_IMG_REQUES_CODE =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(food !=null){
            binding.newaddfood.setText("Edit Food");
            Picasso.get().load(food.getImage())
                    .into(binding.img);

            binding.name.setText(food.getName());
            binding. prices.setText(String.valueOf(food.getPrices()));
            binding.decrption.setText(food.getDecription());
        }

        mainContent();



    }



    private void mainContent() {


        Spinner spinner = new Spinner(binding.spinner, this);
        spinner.show();
        spinner.setSpinnerItemSelected(new Spinner.SpinnerItemSelected() {
            @Override
            public void onItemSelected(String value) {
                             kindOfFooad = value;

                             if(food !=null){
                                 food.setKindOfFooad(value);
                             }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                kindOfFooad = "Drink";

            }
        });
        binding.close.setOnClickListener(v -> finish());
           binding.changeImg.setOnClickListener(v -> {
               Intent intent = new Intent();
               intent.setType("image/*");
               intent.setAction(Intent.ACTION_GET_CONTENT);
               startActivityIfNeeded(Intent.createChooser(intent,"Choose img"),CHOOSE_IMG_REQUES_CODE);
           });
        binding.ok.setOnClickListener(v -> {
            if (!checkError()) {
                    updateView(false);
                creatFood();
            }

        });

    }

    private boolean checkError() {
        boolean error = false;
        if(binding.name.getText().toString().trim().isEmpty()){
            binding.name.setError("Can't be left blank");
            error = true;}
        else error=false;

        if(binding.prices.getText().toString().trim().isEmpty()){
            binding.prices.setError("Can't be left blank");
            error=true;}
        else error = false;
        if(binding.decrption.getText().toString().trim().isEmpty()){
            binding.decrption.setError("Can't be left blank");
                      error=true;}
                  else error = false;
        return  error;


    }

    private void creatFood() {
       // foods.clear();
        if(food!=null) //edit
        {
            food.setName(binding.name.getText().toString().trim());
            food.setPrices(Long.valueOf(binding.prices.getText().toString().trim()));
            food.setDecription(binding.decrption.getText().toString().trim());
            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
            finish();



        }else {
            food = new Food(binding.name.getText().toString().trim(),Long.valueOf(binding.prices.getText().toString().trim()),binding.decrption.getText().toString().trim(),"none",kindOfFooad,new Rate());

        }

           if(uri!=null) {

               StorageTask<UploadTask.TaskSnapshot> taskSnapshotStorageTask = Constants.STORE.getReference("FOODS").child(String.valueOf(food.getId()))
                       .child("img").putFile(uri)

                       .addOnCompleteListener(task -> {
                           if (task.isComplete())
                               getImgLink(String.valueOf(food.getId()));
                           Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
                           finish();

                       })
                       .addOnFailureListener(e -> {
                           updateView(true);
                           Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                       } )  ;


           } else
             uploadToDB(); 


    }




    private void getImgLink(String value) {
           Constants.STORE.getReference("FOODS").child(value)
                   .child("img").getDownloadUrl()
                   .addOnCompleteListener(task -> {
                     food.setImage(task.getResult().toString());
                     uploadToDB();
                   }) ;

    }

    private void uploadToDB() {
        Constants.DATABASE.getReference("FOODS").child(String.valueOf(food.getId()))
                .setValue(food).addOnCompleteListener(task -> updateView(true));


    }

     private void updateView(boolean b) {
            binding.ok.setEnabled(false);
            binding.close.setEnabled(false);
            binding.name.setEnabled(b);
              binding.prices.setEnabled(b);
                binding.decrption.setEnabled(b);
                 binding.spinner.setEnabled(b);
                  binding.changeImg.setEnabled(b);
                  if(!b){
                      binding.prgBox.setVisibility(View.VISIBLE);
                  } else       binding.prgBox.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == CHOOSE_IMG_REQUES_CODE){
            uri = data.getData();
            Picasso.get().load(uri).into(binding.img);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        food = null;
    }
}