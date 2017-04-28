package com.example.raych.nhu;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Locale;

public class CreateEvent extends AppCompatActivity {
    String eventName;
    String eventLocation;
    String eventDate;
    String eventTime;
    String eventCost;
    String eventDescription;
    String eventYoutubeLink;
    EventData eventData = new EventData();
    private ImageButton mic;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    EditText name_input,location_input,time_input,date_input,cost_input,description_input,youtubelink;

    //voice to speech source code taken from
    //http://www.androidhive.info/2014/07/android-speech-to-text-tutorial/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        name_input = (EditText) findViewById(R.id.e_title);
         location_input = (EditText) findViewById(R.id.e_location);
         time_input = (EditText) findViewById(R.id.e_time);
         date_input = (EditText) findViewById(R.id.e_date);
         cost_input = (EditText) findViewById(R.id.e_cost);
         description_input = (EditText) findViewById(R.id.e_description);
         youtubelink = (EditText)findViewById(R.id.youtube_link);

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        tb.setTitle("Create Event");
        setSupportActionBar(tb);
        mic = (ImageButton)findViewById(R.id.imageView2) ;
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
        Button createEvent = (Button)findViewById(R.id.make_event_button);
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    name_input.setText(result.get(0));
                }
                break;
            }
        }
    }




    //This method is called through layout - onclick
    public void getNewEventInfo(View v){


        eventName =  name_input.getText().toString().trim();
        eventLocation  = location_input.getText().toString().trim();
        eventDate = date_input.getText().toString().trim();
        eventTime = time_input.getText().toString().trim();
        eventCost = cost_input.getText().toString().trim();
        eventDescription = description_input.getText().toString().trim();
        eventYoutubeLink = youtubelink.getText().toString().trim();

        if (eventName == null || eventName.isEmpty()){
            Toast.makeText(getApplicationContext(),"Missing inputs", Toast.LENGTH_LONG).show();
        }
        else
        {
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

            Intent clear1 = new Intent(this,CreateEvent.class);
            startActivity(clear1);
            Toast.makeText(getApplicationContext(),"Event Created", Toast.LENGTH_LONG).show();

        }

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
