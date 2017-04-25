package com.example.raych.nhu;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

/**
 * Created by donnale on 4/21/17.
 */

public class joined_rv_fragment extends android.support.v4.app.Fragment implements Event_Info_frag.OnFragmentInteractionListener{

    private static final String  param1 = "param1";
    private String mParam1;
    private OnFragmentInteractionListener mListener;
    DatabaseReference mRef_User;
    DatabaseReference mRef_Event;

    public joined_rv_fragment(){

    }

    public static joined_rv_fragment newInstance(String new1) {
        joined_rv_fragment fragment = new joined_rv_fragment();
        Bundle args = new Bundle();
        args.putString(param1, new1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(param1);
        }
        setHasOptionsMenu(true);
    }


    RecyclerView recyclerView;
    List data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View currentView = inflater.inflate(R.layout.joined_rv_fragment_layout, container, false);
        mRef_User = FirebaseDatabase.getInstance().getReference().child("userdata").getRef();
        mRef_Event = FirebaseDatabase.getInstance().getReference().child("eventdata").getRef();
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
                    data = (List) user.get("joined");
                    data.remove(0);
                    //data.add("hello");
                    recyclerView = (RecyclerView) currentView.findViewById(R.id.my_joined_recycler_view);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    final HostAdapter adapter = new HostAdapter(getActivity(), data);
                    recyclerView.setAdapter(adapter);

                    adapter.SetOnItemClickListener(new HostAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            String eventname = data.get(position).toString();
                            final DatabaseReference event = mRef_Event.child(eventname);

                            event.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    HashMap e = (HashMap) dataSnapshot.getValue();

                                    Event event2 = new Event();
                                    event2.setName(e.get("name").toString());
                                    event2.setLocation(e.get("location").toString());
                                    event2.setCost(e.get("cost").toString());
                                    event2.setDate(e.get("date").toString());
                                    event2.setTime(e.get("time").toString());
                                    event2.setDescription(e.get("description").toString());

                                    FragmentManager fm = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                    fragmentTransaction.replace(R.id.joined_rv_fragment_frame, Event_Info_frag.newInstance(event2));
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }

                    });
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }



        return currentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
