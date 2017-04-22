package com.example.raych.nhu;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class HostingEvent extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosting_event);


        Toolbar tb = (Toolbar) findViewById(R.id.host_event_toolbar);
        //setSupportActionBar(rvToolbar);
        tb.setTitle("Events I Host");

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.host_rv_fragment_frame, new host_rv_fragment());
        fragmentTransaction.commit();

    }

    public void fabClick(View v){
        switch (v.getId()) {
            case R.id.create_new_event:
                Intent intent2 = new Intent(HostingEvent.this, CreateEvent.class);
                startActivity(intent2);
                break;
        }
    }

}
