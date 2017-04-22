package com.example.raych.nhu;;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class JoinedEvents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_events);

        Toolbar tb = (Toolbar) findViewById(R.id.joined_event_toolbar);
        //setSupportActionBar(rvToolbar);
        tb.setTitle("Events I Joined");

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.joined_rv_fragment_frame, new joined_rv_fragment());
        fragmentTransaction.commit();
    }
}
