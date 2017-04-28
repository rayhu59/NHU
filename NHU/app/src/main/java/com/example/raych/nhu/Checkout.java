package com.example.raych.nhu;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Checkout extends AppCompatActivity {
    public static final int PAYPAL_REQUEST_CODE = 123;
    String retVal;
    private String paymentAmount;
    private static PayPalConfiguration config = new PayPalConfiguration()

            //  (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)

            .clientId("ATG--DNg034gTxOzSV66Fk16qjYHv7cjdvtbZPJjIWUBhsIBP9TvkW2dyWBctX40wsgiMR9uZIxR3L3a");
    private Button pay;
    private Button cancel;
    private TextView checkout_name2;
    private TextView checkout_cost2;
    private TextView checkout_location2;
    private TextView checkout_description2;
    private Event checkout_event;
    private String cost;
    EventData eventData = new EventData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Intent intent = this.getIntent();
        checkout_event = (Event) intent.getSerializableExtra("Event");
        cost = checkout_event.getCost().toString();
        checkout_name2 = (TextView) findViewById(R.id.checkout_name);
        checkout_cost2 = (TextView) findViewById(R.id.checkout_cost);
        checkout_location2 = (TextView) findViewById(R.id.checkout_location);
        checkout_description2 = (TextView) findViewById(R.id.checkout_description);


        checkout_name2.setText("Event : " +checkout_event.getName());
        checkout_cost2.setText("Cost : $" +checkout_event.getCost());
        checkout_location2.setText("Location : " +checkout_event.getLocation());
        checkout_description2.setText("Description : "+checkout_event.getDescription());

        pay = (Button)findViewById(R.id.checkout_pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Double.valueOf(checkout_event.getCost()) == 0) {
                    eventData.AddItemToJoin(checkout_event.getName());
                    Intent home = new Intent(getApplicationContext(), Home.class);
                    Toast.makeText(getApplicationContext(),"Event Joined", Toast.LENGTH_LONG).show();
                    startActivity(home);
                }else {
                    getPayment();
                }

            }
        });
        cancel = (Button)findViewById(R.id.checkout_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent one = new Intent(getApplicationContext(), Home.class);
                startActivity(one);
            }
        });
    }


    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    //Method to give Paypal API parameters to run.
    private void getPayment() {
        //Getting the amount from editText
        //Change to Tour amount.
        paymentAmount = cost;

        //Creating a paypalpayment
        java.math.BigDecimal amount = new java.math.BigDecimal(String.valueOf(paymentAmount));
        PayPalPayment payment = new PayPalPayment(amount, "USD", "Fee",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);
        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    //Method that starts the transaction and returns result of transaction
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Checkout.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);
                        //Starting a new activity for the payment details and also putting the payment details with intent
                        startActivity(new Intent(this, ConfirmationActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("Event_Name", checkout_event.getName())
                                .putExtra("PaymentAmount", paymentAmount));

                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Checkout.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }





    @Override
    public void onBackPressed() {
    }


}
