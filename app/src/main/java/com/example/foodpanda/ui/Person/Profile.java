package com.example.foodpanda.ui.Person;

import static com.example.foodpanda.Utils.Global.users;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodpanda.Model.User;
import com.example.foodpanda.Utils.Constants;
import com.example.foodpanda.Utils.Global;
import com.example.foodpanda.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Profile extends AppCompatActivity {
    private ActivityProfileBinding binding;


    private static int CHOOSE_IMG =1;

    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        Picasso.get().load(users.getUrlImg())
                .into(binding.img);
        binding.name.setText(users.getName());
        binding.phone.setText(users.getPhone());


        butonEven();
    }

    ///chon ảnh

    private void butonEven() {
        binding.camera.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityIfNeeded(Intent.createChooser(intent,"Choose img"),CHOOSE_IMG);
        });

        binding.ok.setOnClickListener(v -> updateProfile());
        binding.dele.setOnClickListener(v -> finish());

    }

    private void updateProfile() {
        updateView(false);
      if(uri != null){
            Constants.STORE.getReference("USER").child(users.getUid()).child("Avata")
                    .putFile(uri)

                    .addOnCompleteListener(task -> getLink(task.isComplete()))

                    .addOnFailureListener(e -> Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show());

        }


    }

    private void updateView(boolean b) {
        binding.ok.setEnabled(false);
        binding.dele.setEnabled(false);
        binding.name.setEnabled(b);
       binding.phone.setEnabled(b);
        if(!b){
            binding.prgBox.setVisibility(View.VISIBLE);

        }else {
            binding.prgBox.setVisibility(View.GONE);
        }
    }

    private void getLink(boolean complete) {
        if(complete){
            Constants.STORE.getReference("USER").child(users.getUid()).child("Avata")
                    .getDownloadUrl().addOnCompleteListener(task -> {
                        String imgUrl = task.getResult().toString();
                        users.setUrlImg(imgUrl);
                        users.setName(binding.name.getText().toString().trim());
                        users.setPhone(binding.phone.getText().toString().trim());
                        Map<String, Object> map = new HashMap<>();
                        map.put("urlImg",users.getUrlImg());
                        map.put("name",users.getName());
                        map.put("phone",users.getPhone());
                        Constants.DATABASE_USER.child(users.getUid()).updateChildren(map)
                                .addOnCompleteListener(task1 -> {
                                    if(task.isComplete()){
                                        updateView(true);
                                        Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
                                        finish();

                                    }

                                });

                    });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode ==RESULT_OK && requestCode ==CHOOSE_IMG){
            uri=data.getData();
            Picasso.get().load(data.getData())
                    .into(binding.img);


        }
    }
}