package com.example.foodpanda.Login;

import static com.example.foodpanda.Utils.Constants.AUTH;
import static com.example.foodpanda.Utils.Constants.DATABASE_USER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.foodpanda.databinding.ActivityRegisterBinding;
import com.example.foodpanda.ui.home.MainActivity;
import com.example.foodpanda.Model.User;
import com.example.foodpanda.Utils.Constants;
import com.example.foodpanda.Utils.Global;

import com.google.android.gms.tasks.OnSuccessListener;

public class Register extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        buttonEvent();

        binding.prgRegisterr.setVisibility(View.VISIBLE);



    }

    private void buttonEvent() {
        binding.registerBt.setOnClickListener(v -> regiter());
        binding.login.setOnClickListener(view -> startActivity(new Intent(this,LoginScreen.class)));
        binding.img.setOnClickListener(view -> finish());
    }

    private void regiter() {
        binding.prgRegister.setVisibility(View.VISIBLE);
        Constants.AUTH.createUserWithEmailAndPassword(binding.emailRegiter.getText().toString().trim(),binding.passRegiter.getText().toString().trim())
                .addOnSuccessListener(authResult -> {
                    User user = new User(binding.emailRegiter.getText().toString().trim(),binding.nameRegiter.getText().toString().trim(),binding.phoneRegiter.getText().toString().trim(),authResult.getUser().getUid());
                    DATABASE_USER.child(user.getUid())
                            .setValue(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Global.updateUser();
                                }
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                AUTH.getCurrentUser().delete();
                                binding.prgRegister.setVisibility(View.GONE);

                            })
                            .addOnCompleteListener(task -> {
                                startActivity(new Intent(Register.this, MainActivity.class));
                                finishAffinity();
                            });


                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    binding.prgRegister.setVisibility(View.GONE);

                });

    }
}