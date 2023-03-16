package com.example.foodpanda.ui.Person;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import com.example.foodpanda.Model.Food;
import com.example.foodpanda.Utils.Constants;
import com.example.foodpanda.databinding.ActivityListFoodsBinding;
import com.example.foodpanda.ui.Adapter.FoodsAdapter;
import com.google.firebase.database.DataSnapshot;
import java.util.ArrayList;

public class ListFoods extends AppCompatActivity {

    private ActivityListFoodsBinding binding;
    public static final String KEY = "ACTI_KEY";
    public static final String VALUES = "VALUES";
    public static final int RECOMENDED_FOODS = 1; //"RECOMENDED_FOODS";
    public static final int BREAKFAST = 2; //"BREAKFAST";
    public static final int LUNCH = 3; //"LUNCH";
    public static final int SEARCH_RESULT = 4; //"SEARCH_RESULT";
    private FoodsAdapter adapter;
    private ArrayList<Food> foods;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListFoodsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mainContent();
        buttonEvent();
    }

    private void buttonEvent() {
        binding.back.setOnClickListener(view -> finish());
    }

    private void mainContent() {
        foods = new ArrayList<>();
        adapter = new FoodsAdapter(foods);
        RecyclerView allfood = binding.rcv;
        allfood.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        allfood.setAdapter(adapter);
        switch (getIntent().getIntExtra(KEY,0)){
            case RECOMENDED_FOODS:
                binding.title.setText("RECOMENDED FOODS");
                Constants.DATABASE.getReference("FOODS")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isComplete()){
                                for (DataSnapshot data : task.getResult().getChildren()){
                                    Food f=data.getValue(Food.class);
                                    foods.add(f);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
                break;
            case BREAKFAST:
                binding.title.setText("BREAKFAST");
                Constants.DATABASE.getReference("FOODS")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isComplete()){
                                for (DataSnapshot data : task.getResult().getChildren()){
                                    Food f = data.getValue(Food.class);
                                    if (f.getKindOfFooad().equals("Fastfood")||f.getKindOfFooad().equals("Drink"))
                                        foods.add(f);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
                break;
            case LUNCH:
                binding.title.setText("LUNCH");
                Constants.DATABASE.getReference("FOODS")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isComplete()){
                                for (DataSnapshot data : task.getResult().getChildren()){
                                    Food f = data.getValue(Food.class);
                                    if (f.getKindOfFooad().equals("Drink") || f.getKindOfFooad().equals("Deal"))
                                        foods.add(f);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
                break;
            case SEARCH_RESULT:
                binding.title.setText("RESULT");
                Constants.DATABASE.getReference("FOODS")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isComplete()){
                                String val = getIntent().getStringExtra(VALUES);
                                for (DataSnapshot data : task.getResult().getChildren()){
                                    Food f = data.getValue(Food.class);
                                    if (f.getName().toUpperCase().matches(val.toUpperCase()+"(.*)"))
                                        foods.add(f);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
                break;

        }
    }
}