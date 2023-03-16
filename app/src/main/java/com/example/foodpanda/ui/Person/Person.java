package com.example.foodpanda.ui.Person;

import static com.example.foodpanda.Utils.Global.users;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodpanda.R;
import com.example.foodpanda.Utils.Constants;
import com.example.foodpanda.Utils.Global;
import com.example.foodpanda.Login.Welcom;

import com.example.foodpanda.databinding.FragmentPersonBinding;
import com.squareup.picasso.Picasso;

public class Person extends Fragment{
    private FragmentPersonBinding binding;
    public Person() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     binding = FragmentPersonBinding.inflate(inflater,container,false);
     displayUser();

     buttonEvem();

        return binding.getRoot();
    }

    private void buttonEvem() {
        binding.locaPs.setOnClickListener(v -> createAddressbox());
        binding.logout.setOnClickListener(v -> {
            Constants.AUTH.signOut();
            startActivity(new Intent(getContext(), Welcom.class));
            getActivity().finishAffinity();
        });
        binding.historyPs.setOnClickListener(v -> startActivity(new Intent(getContext(), HistoryOder.class)));
        binding.addFood.setOnClickListener(v -> startActivity(new Intent(getContext(), FoodManagement.class)));
        binding.accPs.setOnClickListener(v -> startActivity(new Intent(getContext(), Profile.class)));
        binding.infoPs.setOnClickListener(v -> startActivity(new Intent(getContext(), Info.class)));


    }

    private void createAddressbox() {

        Dialog dialog= new Dialog(getContext());
        dialog.setContentView(R.layout.change_address_dialog);
        EditText address= dialog.findViewById(R.id.address);

        if(users.getAddress()!=null){
            address.setText(users.getAddress());
        }else {
            address.setHint("No Adsrres");
        }
        dialog.findViewById(R.id.cancel).setOnClickListener(v -> dialog.cancel());

        dialog.findViewById(R.id.ok).setOnClickListener(v ->
                Constants.DATABASE_USER.child(users.getUid()).child("address")
                .setValue(address.getText().toString().trim())
                .addOnSuccessListener(unused -> {
                    Toast.makeText(getContext(), "Update address succesful", Toast.LENGTH_SHORT).show();
                    Global.updateUser();
                    dialog.dismiss();
                })

        );


        dialog.show();

    }



    private void displayUser() {

                   binding.namePs.setText(users.getName());
                   binding.emailPs.setText(users.getEmail());
                   Picasso.get().load(users.getUrlImg()).into(binding.imgPs);

                   if(users.getKinAc()==1)
                       binding.addFood.setVisibility(View.VISIBLE);


    }


}