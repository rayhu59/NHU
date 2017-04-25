package com.example.raych.nhu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Event_Info_frag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Event_Info_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Event_Info_frag extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    static Event event = new Event();

    TextView detail_name;
    TextView detail_location;
    TextView detail_cost;
    TextView detail_date;
    TextView detail_time;
    TextView detail_description;

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Event_Info_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     //     * @param param1 Parameter 1.
     //     * @param param2 Parameter 2.
     * @return A new instance of fragment Event_Info_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static Event_Info_frag newInstance(Event e) {
        Event_Info_frag fragment = new Event_Info_frag();
        Bundle args = new Bundle();
        args.putSerializable("event",e);
        event = e;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_event__info_frag, container, false);
        detail_name = (TextView) view.findViewById(R.id.detail_name);
        detail_location = (TextView) view.findViewById(R.id.detail_location);
        detail_cost = (TextView) view.findViewById(R.id.detail_cost);
        detail_time = (TextView) view.findViewById(R.id.detail_time);
        detail_date = (TextView) view.findViewById(R.id.detail_date);
        detail_description = (TextView) view.findViewById(R.id.detail_description);

        if (getArguments()!=null){

            event = (Event) getArguments().getSerializable("event");
        }

        detail_name.setText(event.getName());
        detail_description.setText(event.getDescription());
        detail_cost.setText(event.getCost());
        detail_date.setText(event.getDate());
        detail_time.setText(event.getTime());
        detail_location.setText(event.getLocation());

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