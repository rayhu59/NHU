package com.example.raych.nhu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

/**
 * Created by donnale on 4/21/17.
 */

public class joined_rv_fragment extends android.support.v4.app.Fragment implements Event_Info_frag.OnFragmentInteractionListener{

    private static final String  param1 = "param1";
    private String mParam1;
    private OnFragmentInteractionListener mListener;
    //
    RecyclerView rv_joined;
    MyJoinedAdapter myJoinedAdapter;
    RecyclerView.LayoutManager jlayoutManager;
    //MovieData movieData = new MovieData();
    CustomOnClickListener myListener;

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

    DatabaseReference childRef;
    MyHostAdapter myHostAdapter;
    RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         final View currentView = inflater.inflate(R.layout.joined_rv_fragment_layout, container, false);

        childRef = FirebaseDatabase.getInstance().getReference().child("eventdata").getRef();
        myHostAdapter = new MyHostAdapter(Event.class, R.layout.hosting_card,
                MyHostAdapter.ViewHolder.class,childRef,getContext());

        rv_joined = (RecyclerView) currentView.findViewById(R.id.my_joined_recycler_view);
        jlayoutManager = new LinearLayoutManager(currentView.getContext());
        rv_joined.setLayoutManager(jlayoutManager);
        rv_joined.setAdapter(myHostAdapter);
        // myListener = (CustomOnClickListener) currentView.getContext();


        //rv_joined.setHasFixedSize(true);
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
