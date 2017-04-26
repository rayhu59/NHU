package com.example.raych.nhu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Store_Top_Free.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Store_Top_Free#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Store_Top_Free extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    RecyclerView top_free_recycler_view;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference mRef_Event;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Store_Top_Free() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Store_Top_Free.
     */
    // TODO: Rename and change types and number of parameters
    public static Store_Top_Free newInstance(String param1, String param2) {
        Store_Top_Free fragment = new Store_Top_Free();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_store__top__free, container, false);
        mRef_Event = FirebaseDatabase.getInstance().getReference().child("eventdata").getRef();
        final List eventList = new ArrayList<Event>();

        mRef_Event.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap e = (HashMap) snapshot.getValue();
                    String cost = e.get("cost").toString();
                    if (Double.valueOf(cost) == 0){
                        eventList.add(e);
                    }
                }

                top_free_recycler_view= (RecyclerView) view.findViewById(R.id.my_topfree_recycler_view);
                layoutManager = new LinearLayoutManager(getActivity());
                top_free_recycler_view.setLayoutManager(layoutManager);
                final FreeAdapter adapter = new FreeAdapter(getActivity(), eventList);
                top_free_recycler_view.setAdapter(adapter);

                adapter.SetOnItemClickListener(new FreeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        HashMap e = (HashMap) eventList.get(position);
                        Event event2 = new Event();
                        event2.setName(e.get("name").toString());
                        event2.setLocation(e.get("location").toString());
                        event2.setCost(e.get("cost").toString());
                        event2.setDate(e.get("date").toString());
                        event2.setTime(e.get("time").toString());
                        event2.setDescription(e.get("description").toString());

                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.findhitup_frame, Event_Info_frag.newInstance(event2));
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}