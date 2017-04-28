package com.example.raych.nhu;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by raych on 4/22/2017.
 */

public class EventData {

    DatabaseReference mRef;
    DatabaseReference mRef_User;
    Context mContext;
    List hosted = new ArrayList<String>();
    List joined = new ArrayList<String>();
    String lat;
    String longt;
    Event currentEvent;

    public EventData() {
        mRef = FirebaseDatabase.getInstance().getReference().child("eventdata").getRef();
        mRef_User = FirebaseDatabase.getInstance().getReference().child("userdata").getRef();
        mContext = null;
    }

    public List getHostList() {
        hosting_joined();
        return hosted;
    }
    public List getJoinedList(){
        hosting_joined();
        return joined;
    }

    public DatabaseReference getFireBaseRef(){
        return mRef;
    }
    public void setContext(Context context){mContext = context;}

    public void removeItemFromServer(Event event){
        /*
        if(movie!=null){
            String id = (String)movie.get("id");
            mRef.child(id).removeValue();
        } */
    }

    public void hosting_joined() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            String parse = (email.split("@"))[0];
            parse = parse.replaceAll("[^A-Za-z0-9]", "");
            DatabaseReference host  =mRef_User.child(parse);
            final String finalParse = parse;
            host.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String,User> user = (Map<String, User>) dataSnapshot.getValue();
                    hosted = (List) user.get("hosting");
                    joined = (List) user.get("joined");

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void AddItemToJoin (final String name) {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String email = user.getEmail();
                String parse = (email.split("@"))[0];
                parse = parse.replaceAll("[^A-Za-z0-9]", "");

                DatabaseReference host = mRef_User.child(parse);
                final String finalParse = parse;
                host.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, User> user = (Map<String, User>) dataSnapshot.getValue();
                        List CurrentList = new ArrayList<String>();
                        CurrentList = (List) user.get("hosting");
                        List CurrentJoin = (List) user.get("joined");

                        User user2 = new User(finalParse);
                        user2.newJoinList(CurrentJoin);
                        user2.newHostList(CurrentList);

                        if ( CurrentJoin.contains(name)){

                        } else {
                            user2.AddtoJoined(name);
                        }

                        UserData userData = new UserData();
                        userData.addUserToServer(user2);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }


    }


    public void addItemToServer(final Event event){
        if(event!=null){
            currentEvent = event;
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String email = user.getEmail();
                String parse = (email.split("@"))[0];
                parse = parse.replaceAll("[^A-Za-z0-9]","");

                DatabaseReference host  =mRef_User.child(parse);
                final String finalParse = parse;
                host.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                       Map<String,User> user = (Map<String, User>) dataSnapshot.getValue();
                        List CurrentList = new ArrayList<String>();
                        CurrentList = (List) user.get("hosting");
                        List CurrentJoin = (List) user.get("joined");

                        User user2 = new User(finalParse);
                        user2.newJoinList(CurrentJoin);
                        user2.newHostList(CurrentList);

                        if ( CurrentList.contains(event.getName())){

                        }else {
                            user2.AddtoHosting(event.getName());
                        }

                        UserData userData=  new UserData();
                        userData.addUserToServer(user2);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
            new GetCoordinates().execute(event.getLocation().toString().replace(" ","+"));
            //add this in post execution of Asnyc task
           // mRef.child(event.getName()).setValue(event);  //Adds event to eventdata database
        }
    }

    //Source Code for GetCoordinates obtain from https://github.com/eddydn/GetCoordinatesGeocode
    private class GetCoordinates extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String response;
            try{
                String address = strings[0];
                HttpDataHandler http = new HttpDataHandler();
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s",address);
                response = http.getHTTPData(url);
                return response;
            }
            catch (Exception ex)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                JSONObject jsonObject = new JSONObject(s);

                String lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lat").toString();
                String lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lng").toString();

                Log.d("LATLNG", "lat: " + lat + " long: "+ lng);
                currentEvent.setLat(lat);
                currentEvent.setLng(lng);
                mRef.child(currentEvent.getName()).setValue(currentEvent);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
