package com.example.raych.nhu;

import android.content.Context;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public EventData() {
        mRef = FirebaseDatabase.getInstance().getReference().child("eventdata").getRef();
        mRef_User = FirebaseDatabase.getInstance().getReference().child("userdata").getRef();
        mContext = null;
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


    public void addItemToServer(final Event event){
        if(event!=null){

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
                        user2.AddtoHosting(event.getName());
                        UserData userData=  new UserData();
                        userData.addUserToServer(user2);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

            Map<String, Event> event2 = new HashMap<String ,Event>();
            event2.put(event.getName(), event);
            mRef.child(event.getName()).setValue(event);
        }
    }

}
