package com.example.foodpanda.ui.Cart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodpanda.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetail extends AppCompatActivity {
    TextView id,amount,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);

        id =findViewById(R.id.id_momo);
        amount =findViewById(R.id.amont_momo);
        status=findViewById(R.id.status_momo);

        Intent intent = getIntent();
        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("Paymentdeail"));
            showDatail(jsonObject.getJSONObject("respone"),intent.getStringExtra("payamount"));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void showDatail(JSONObject respone, String payamount) {
        try {
            id.setText(respone.getString("id"));
            status.setText(respone.getString("status"));
            amount.setText(respone.getString(String.valueOf("$"+payamount)));
        }catch (JSONException e){
        e.printStackTrace();
    }
    }
}