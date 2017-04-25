package com.example.raych.nhu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by raych on 4/24/2017.
 */

public class DeveloperNotes {
    /*
    package com.example.raych.nhu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



    public class host_rv_fragment extends android.support.v4.app.Fragment implements Event_Info_frag.OnFragmentInteractionListener {

        private static final String  param1 = "param1";
        private String mParam1;
        private com.example.raych.nhu.host_rv_fragment.OnFragmentInteractionListener mListener;

        RecyclerView rv_hosting;
        RecyclerView.LayoutManager layoutManager;
        EventData eventData = new EventData();
        CustomOnClickListener myListener;

        public host_rv_fragment() {
            // Required empty public constructor
        }


        // TODO: Rename and change types and number of parameters
        public static com.example.raych.nhu.host_rv_fragment newInstance(String instance) {
            com.example.raych.nhu.host_rv_fragment fragment = new com.example.raych.nhu.host_rv_fragment();
            Bundle args = new Bundle();
            args.putString(param1, instance);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                mParam1 = getArguments().getString(param1);

            }
        }

        DatabaseReference childRef;
        //MyHostAdapter myHostAdapter;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            final View currentView = inflater.inflate(R.layout.fragment_host_rv_fragment, container, false);
        /*
        childRef = FirebaseDatabase.getInstance().getReference().child("eventdata").getRef();
        myHostAdapter = new MyHostAdapter(Event.class, R.layout.hosting_card,
                MyHostAdapter.ViewHolder.class,childRef,getContext());


        rv_hosting = (RecyclerView) currentView.findViewById(R.id.my_hosting_recycler_view);
        //rv_hosting.setHasFixedSize(true); //lags the firebase
        layoutManager = new LinearLayoutManager(currentView.getContext());
        rv_hosting.setLayoutManager(layoutManager);
        rv_hosting.setAdapter(myHostAdapter);
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
            if (context instanceof com.example.raych.nhu.host_rv_fragment.OnFragmentInteractionListener) {
                mListener = (com.example.raych.nhu.host_rv_fragment.OnFragmentInteractionListener) context;
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

        /**
         * This interface must be implemented by activities that contain this
         * fragment to allow an interaction in this fragment to be communicated
         * to the activity and potentially other fragments contained in that
         * activity.
         * <p>
         * See the Android Training lesson <a href=
         * "http://developer.android.com/training/basics/fragments/communicating.html"
         * >Communicating with Other Fragments</a> for more information.

        public interface OnFragmentInteractionListener {
            // TODO: Update argument type and name
            void onFragmentInteraction(Uri uri);
        }
    }


     */
}
