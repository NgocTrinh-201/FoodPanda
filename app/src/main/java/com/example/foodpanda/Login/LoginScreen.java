package com.example.foodpanda.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.foodpanda.databinding.ActivityLoginBinding;
import com.example.foodpanda.ui.home.MainActivity;
import com.example.foodpanda.Utils.Constants;
import com.example.foodpanda.Utils.Global;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;

public class LoginScreen extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       binding.prgLoginnn.setVisibility(View.VISIBLE);



        buttonEven();
    }

    private void buttonEven() {
        binding.register.setOnClickListener(v -> {
            startActivity(new Intent(LoginScreen.this, Register.class));

        });
        binding.forgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(LoginScreen.this, ForgotPass.class));

        });
        binding.img.setOnClickListener(view -> finish());
        binding.loginBt.setOnClickListener(v -> login());
    }

    private void login() {
        binding.prgLogin.setVisibility(View.VISIBLE);
        if(binding.emailInput.getText().toString().trim().isEmpty())
        {
            binding.emailInput.setError("Can't be left blank");
            return;
        }
        if(binding.passwordInput.getText().toString().trim().isEmpty())
        {
            binding.passwordInput.setError("Can't be left blank");
            return;
        }
        Constants.AUTH.signInWithEmailAndPassword(binding.emailInput.getText().toString().trim(),binding.passwordInput.getText().toString().trim())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Global.updateUser();

                        startActivity(new Intent(LoginScreen.this, MainActivity.class));
                       finishAffinity();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginScreen.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        binding.prgLogin.setVisibility(View.GONE);
                    }
                });


    }
}