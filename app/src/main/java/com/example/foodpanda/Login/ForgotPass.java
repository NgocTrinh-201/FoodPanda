package com.example.foodpanda.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.foodpanda.Utils.Constants;
import com.example.foodpanda.databinding.ActivityForgotPassBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class ForgotPass extends AppCompatActivity {


    private ActivityForgotPassBinding binding;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("\n" + "Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }
    private String email ="";


    private void validateData() {
        email = binding.email.getText().toString().trim();

        if(email.isEmpty()){
            Toast.makeText(this, "\n" + "Enter your email address", Toast.LENGTH_SHORT).show();
        }
        else  if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Do not receive Email", Toast.LENGTH_SHORT).show();
        }
        else {
            recoberPassword();
        }
    }

    private void recoberPassword() {
        progressDialog.setMessage("\n" + "Receive Email"+email);
        progressDialog.show();

        Constants.AUTH.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(ForgotPass.this, "Successful", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(ForgotPass.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}