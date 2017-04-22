package com.example.raych.nhu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        //eventData.setContext(this);
        eventData.addItemToServer(newEvent);

        Intent gohome = new Intent(this,Home.class);
        startActivity(gohome);


    }
}
