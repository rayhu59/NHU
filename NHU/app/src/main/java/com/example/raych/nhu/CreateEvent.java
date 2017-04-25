package com.example.raych.nhu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class CreateEvent extends AppCompatActivity {
    String eventName;
    String eventLocation;
    String eventDate;
    String eventTime;
    String eventCost;
    String eventDescription;
    String eventYoutubeLink;
    EventData eventData = new EventData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        tb.setTitle("Create Event");
        setSupportActionBar(tb);

        Button createEvent = (Button)findViewById(R.id.make_event_button);

    }

    //This method is called through layout - onclick
    public void getNewEventInfo(View v){
        EditText name_input = (EditText) findViewById(R.id.e_title);
        EditText location_input = (EditText) findViewById(R.id.e_location);
        EditText time_input = (EditText) findViewById(R.id.e_time);
        EditText date_input = (EditText) findViewById(R.id.e_date);
        EditText cost_input = (EditText) findViewById(R.id.e_cost);
        EditText description_input = (EditText) findViewById(R.id.e_description);
        EditText youtubelink = (EditText)findViewById(R.id.youtube_link);

        eventName =  name_input.getText().toString().trim();
        eventLocation  = location_input.getText().toString().trim();
        eventDate = date_input.getText().toString().trim();
        eventTime = time_input.getText().toString().trim();
        eventCost = cost_input.getText().toString().trim();
        eventDescription = description_input.getText().toString().trim();
        eventYoutubeLink = youtubelink.getText().toString().trim();

        Event newEvent = new Event();
        newEvent.setName(eventName);
        newEvent.setLocation(eventLocation);
        newEvent.setDescription(eventDescription);
        newEvent.setDate(eventDate);
        newEvent.setTime(eventTime);
        newEvent.setCost(eventCost);
        newEvent.setGuests("0");
        newEvent.setYoutubeLink(eventYoutubeLink);

        eventData.addItemToServer(newEvent);

        Intent gohome = new Intent(this,Home.class);
        startActivity(gohome);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.toolbar_home:
                Intent zero = new Intent(this,Home.class);
                startActivity(zero);
                return true;
            case R.id.toolbar_create:
                Intent one = new Intent(this,CreateEvent.class);
                startActivity(one);
                return true;
            case R.id.toolbar_host:
                Intent two = new Intent(this,HostingEvent.class);
                startActivity(two);
                return true;
            case R.id.toolbar_join:
                Intent three = new Intent(this,JoinedEvents.class);
                startActivity(three);
                return true;
            case R.id.toolbar_hitup:
                Intent four = new Intent(this,FindHitUp.class);
                startActivity(four);
                return true;
            case R.id.toolbar_logout:
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                Intent logout = new Intent(this,login.class);
                startActivity(logout);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
