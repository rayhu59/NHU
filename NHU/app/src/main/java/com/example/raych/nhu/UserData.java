package com.example.raych.nhu;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by raych on 4/22/2017.
 */

public class UserData {
    DatabaseReference mRef;
    Context mContext;

    public UserData() {
        mRef = FirebaseDatabase.getInstance().getReference().child("userdata").getRef();
        mContext = null;
    }

    public DatabaseReference getFireBaseRef(){
        return mRef;
    }
    public void setContext(Context context){mContext = context;}

    public void addUserToServer(User CreatedUser){
        Map<String, User> newUser = new HashMap<String ,User>();
        newUser.put(CreatedUser.getName(), CreatedUser);
        mRef.child(CreatedUser.getName()).setValue(CreatedUser);
    }
}
