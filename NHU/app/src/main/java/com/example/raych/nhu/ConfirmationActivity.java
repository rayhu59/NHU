package com.example.raych.nhu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfirmationActivity extends AppCompatActivity {
    EventData eventData = new EventData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        Intent intent = getIntent();
        Button returnhome = (Button)findViewById(R.id.return_home);
        returnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), Home.class);  //fix?
                startActivity(intent1);
            }
        });

        String event_add = intent.getStringExtra("Event_Name");
        TextView name = (TextView) findViewById(R.id.event_joined_name);
        name.setText(event_add);
        eventData.AddItemToJoin(event_add);
        try {
            JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));

            //Displaying payment details
            showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Set fields with the proper data.
    private void showDetails(JSONObject jsonDetails, String paymentAmount) throws JSONException {
        //Views
        TextView textViewId = (TextView) findViewById(R.id.paymentId);
        TextView textViewStatus= (TextView) findViewById(R.id.paymentStatus);
        TextView textViewAmount = (TextView) findViewById(R.id.paymentAmount);

        //Showing the details from json object
        textViewId.setText(jsonDetails.getString("id"));
        textViewStatus.setText(jsonDetails.getString("state"));
        textViewAmount.setText(paymentAmount+" USD");
    }


    @Override
    public void onBackPressed() {
    }


}
