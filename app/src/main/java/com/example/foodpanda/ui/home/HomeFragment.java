package com.example.foodpanda.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodpanda.ui.Person.ListFoods;
import com.example.foodpanda.Model.Food;

import com.example.foodpanda.Utils.Constants;
import com.example.foodpanda.Utils.Global;
import com.example.foodpanda.databinding.FragmentHomeBinding;
import com.example.foodpanda.ui.Adapter.BreakfastRecomAdapter;
import com.example.foodpanda.ui.Adapter.CommentFoodAdapter;
import com.example.foodpanda.ui.Adapter.FoodsAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private FoodsAdapter foodsAdapter;
    private CommentFoodAdapter recomAdapter;
    private BreakfastRecomAdapter breakfastRecomAdapter;
    private BreakfastRecomAdapter lunchAdapter;
    private ArrayList<Food> recomFoods;
    private ArrayList<Food> breakfastFood;
    private ArrayList<Food> lundFood;
    private ArrayList<Food> foods;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();




        ///recomen cách 1 đẩy lên data


        /*  recomFoods = new ArrayList<>();
        recomAdapter = new CommentFoodAdapter(recomFoods);
        RecyclerView recyclerView = binding.recyView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(recomAdapter);
       getComten();

       ArrayList<Food> test = new ArrayList<>();
        test.add(new Food (1, "Pizza", 100000, "Pizza, món ăn có nguồn gốc từ Ý bao gồm một đĩa dẹt kết hợp bột nhào với một ít dầu ô liu, lá oregano, cà chua, ô liu, phô mai mozzarella hoặc phô mai khác, và nhiều loại các nguyên liệu khác, được nướng nhanh — thông thường, trong bối cảnh thương mại, sử dụng lò đốt củi được làm nóng ở nhiệt độ rất cao — và phục vụ nóng ... "," https://cdn.daylambanh.edu.vn/wp- content /uploads/2020/05/cach-lam-pizza-600x400.jpg","Deal"));
        //Food.add (
        Constants.DATABASE.getReference("FOOD").setValue(test);*/


        ///recomen cách 2
        recomFoods = new ArrayList<>();

        recomAdapter = new CommentFoodAdapter(recomFoods);

        RecyclerView recyclerView = binding.recyView;

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(recomAdapter);
        getRecomFood();

        buttonEven();
        seachEven();


        //// breakfast
        breakfastFood = new ArrayList<>();


        breakfastRecomAdapter = new BreakfastRecomAdapter(breakfastFood);
        RecyclerView breakfastRcv = binding.recyBreakfast;
        breakfastRcv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        breakfastRcv.setAdapter(breakfastRecomAdapter);
        getbreakfastFood();


        //// lund
        lundFood = new ArrayList<>();


        lunchAdapter = new BreakfastRecomAdapter(lundFood);
        RecyclerView lundRcv = binding.recyLund;
        lundRcv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        lundRcv.setAdapter(lunchAdapter);
        getlunchFood();

       //all
        foods = new ArrayList<>();


        foodsAdapter = new FoodsAdapter(foods);
        RecyclerView foodRcv = binding.rcvAllFood;
        foodRcv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        foodRcv.setAdapter(foodsAdapter);
        getFood();

        ///dusplay

        binding.nameHome.setText("Hello " + Global.users.getName() + "!");
        Picasso.get()
                .load(Global.users.getUrlImg()).into(binding.imgHome);

        binding.prgHome.setVisibility(View.VISIBLE);



        return binding.getRoot();





    }

    private void seachEven() {
        binding.seach.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(),ListFoods.class);
            intent.putExtra(ListFoods.KEY,ListFoods.SEARCH_RESULT);
            intent.putExtra(ListFoods.VALUES,binding.seachtxt.getText().toString().trim());
            startActivity(intent);
        });
    }

    private void buttonEven() {
        binding.moreRcv.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ListFoods.class);
            intent.putExtra(ListFoods.KEY,ListFoods.RECOMENDED_FOODS);
            startActivity(intent);
        });
        binding.moreBf.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ListFoods.class);
            intent.putExtra(ListFoods.KEY,ListFoods.BREAKFAST);
            startActivity(intent);
        });
        binding.moreLund.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ListFoods.class);
            intent.putExtra(ListFoods.KEY,ListFoods.LUNCH);
            startActivity(intent);
        });

    }


    private void getFood() {
        foods.clear();

        Constants.DATABASE.getReference("FOODS")
                .get().addOnCompleteListener(task -> {
                    if(task.isComplete()){
                        for(DataSnapshot data : task.getResult().getChildren()){
                            Food f =data.getValue(Food.class);
                            foods.add(f);
                        }
                        foodsAdapter.notifyDataSetChanged();
                        binding.prg4.setVisibility(View.GONE);
                    }
                });

    }

    private void getlunchFood() {
        lundFood.clear();

        Constants.DATABASE.getReference("FOODS")
                .get().addOnCompleteListener(task -> {
                    if(task.isComplete()){
                        for(DataSnapshot data : task.getResult().getChildren()){
                            Food f = data.getValue(Food.class);
                            if(f.getKindOfFooad().equals("Drink")|| f.getKindOfFooad().equals("Deal"))
                                lundFood.add(f);
                        }
                        lunchAdapter.notifyDataSetChanged();
                        prg((binding.prg3));
                    }
                });
    }

    private void getbreakfastFood() {
        breakfastFood.clear();

        Constants.DATABASE.getReference("FOODS")
                .get().addOnCompleteListener(task -> {
                    if(task.isComplete()){
                        for(DataSnapshot data : task.getResult().getChildren()){
                          Food f = data.getValue(Food.class);
                          if(f.getKindOfFooad().equals("Fastfood")|| f.getKindOfFooad().equals("Drink"))
                              breakfastFood.add(f);
                        }
                        breakfastRecomAdapter.notifyDataSetChanged();
                        prg((binding.prg2));
                    }
                });
    }


    private void getRecomFood() {
        recomFoods.clear();

        Constants.DATABASE.getReference("FOODS")
                .get().addOnCompleteListener(task -> {
                    if(task.isComplete()){

                        for(DataSnapshot data : task.getResult().getChildren()){
                            Food f =data.getValue(Food.class);
                            recomFoods.add(f);
                        }
                        Toast.makeText(getContext(),""+recomFoods.size(),Toast.LENGTH_SHORT).show();
                        recomAdapter.notifyDataSetChanged();
                        prg((binding.prg));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void prg(LottieAnimationView view){
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.GONE);
            }
        },1000);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}