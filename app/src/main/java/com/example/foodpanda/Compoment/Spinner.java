package com.example.foodpanda.Compoment;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.AppCompatSpinner;

import com.example.foodpanda.R;

import java.util.ArrayList;
import java.util.List;

public class Spinner {

    private AppCompatSpinner spinner;
    private Context context;
    private SpinnerItemSelected spinnerItemSelected;

    public Spinner(AppCompatSpinner spinner, Context context) {
        this.spinner = spinner;
        this.context = context;
    }

    public void show() {
        List<String> spinner = new ArrayList<>();
        spinner.add("Drink"); //0
        spinner.add("Fast food");//1
        spinner.add("Main dishes");//2
        spinner.add("Side dishes");//3


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, spinner);

        this.spinner.setAdapter(adapter);

        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (1){
                    case  0:
                    spinnerItemSelected.onItemSelected("Drink");
                    break;
                    case  1:
                        spinnerItemSelected.onItemSelected("Fastfood");
                        break;
                    case  2:
                        spinnerItemSelected.onItemSelected("Deal");
                        break;
                    case  3:
                        spinnerItemSelected.onItemSelected("An");
                        break;


                    default:
                        spinnerItemSelected.onItemSelected("Drink");
                        break;




                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                spinnerItemSelected.onNothingSelected(adapterView);

            }
        });


    }


    public void setSpinnerItemSelected(SpinnerItemSelected spinnerItemSelected){
        this.spinnerItemSelected = spinnerItemSelected;
    }
   public interface SpinnerItemSelected{
        public void onItemSelected(String value);
        public void onNothingSelected(AdapterView<?> adapterView);

    }

}


