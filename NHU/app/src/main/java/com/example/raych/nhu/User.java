package com.example.raych.nhu;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by raych on 4/22/2017.
 */

public class User {
    String id;
    List hosting = new ArrayList<String>();
    List joined = new ArrayList<String>();

    public User(String email) {
        id=email;
    }

    public String getName() {
        return id;
    }

    public void AddtoHosting(String event){
        hosting.add(event);
    }
    public void AddtoJoined(String event){
        joined.add(event);
    }
    public void newHostList(List data) {
        hosting= data;
    }
    public void newJoinList(List data){
        joined = data;
    }
}
