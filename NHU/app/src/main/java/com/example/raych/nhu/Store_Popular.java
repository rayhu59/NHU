package com.example.raych.nhu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Store_Popular.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Store_Popular#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Store_Popular extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    RecyclerView popular_recycler_view;
    RecyclerView.LayoutManager layoutManager;
    StoreAdapter storeAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Store_Popular() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Store_Popular.
     */
    // TODO: Rename and change types and number of parameters
    public static Store_Popular newInstance(String param1, String param2) {
        Store_Popular fragment = new Store_Popular();
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
        View view = inflater.inflate(R.layout.fragment_store__popular, container, false);

        popular_recycler_view = (RecyclerView) view.findViewById(R.id.my_popular_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        popular_recycler_view.setLayoutManager(layoutManager);
        DatabaseReference childRef = FirebaseDatabase.getInstance().getReference().child("eventdata").getRef();
        storeAdapter = new StoreAdapter(Event.class, R.layout.tab_card, StoreAdapter.ViewHolder.class, childRef, getContext());
        popular_recycler_view.setAdapter(storeAdapter);
        storeAdapter.SetOnItemClickListerner(new StoreAdapter.FireBaseListerner() {
            @Override
            public void cardClick(View view, int position) {
                Event event = storeAdapter.getItem(position);
                getActivity().getSupportFragmentManager()
                        .beginTransaction().replace(R.id.findhitup_frame, Event_Info_frag.newInstance(event))
                        .addToBackStack(null).commit();
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
