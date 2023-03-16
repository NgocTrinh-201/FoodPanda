package com.example.foodpanda.ui.Cart;

import static com.example.foodpanda.Utils.Global.users;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.foodpanda.Model.Bills;
import com.example.foodpanda.Model.Oder;
import com.example.foodpanda.R;
import com.example.foodpanda.Utils.Config;
import com.example.foodpanda.Utils.Constants;
import com.example.foodpanda.databinding.ActivityMoMoBinding;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;


public class MoMo extends AppCompatActivity  {

    private ActivityMoMoBinding binding;
    private ArrayList<Oder> oders;
    private static long prince = 0;


    public static  final  int PayPay_CoDe = 7171;

    private static PayPalConfiguration confi = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PayPal_ID);



    String amount="";

    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityMoMoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,confi);
        startService(intent);


        binding.cancelMomo.setOnClickListener(view -> finish());
        binding.bntMomo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Payment();
            }
        });


    }

    private void Payment() {
        amount = binding.priceMomo.getText().toString();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"USD",
                "Dola",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,confi);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PayPay_CoDe);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PayPay_CoDe) {
            if (requestCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation !=null){
                    try {
                        String paymentDetail = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this,PaymentDetail.class)
                                .putExtra("PayDetail",paymentDetail)
                                .putExtra("PayAmoun",amount));


                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }

            }else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
        }else if(resultCode ==PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(this, "inval", Toast.LENGTH_SHORT).show();

    }

}