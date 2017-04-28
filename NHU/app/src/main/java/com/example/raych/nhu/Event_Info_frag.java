package com.example.raych.nhu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


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

     Event event = new Event();

    TextView detail_name;
    TextView detail_location;
    TextView detail_cost;
    TextView detail_date;
    TextView detail_time;
    TextView detail_description;
    ImageButton Youtube;
    int level;

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
        detail_name = (TextView) view.findViewById(R.id.detail_name2);
        detail_location = (TextView) view.findViewById(R.id.detail_location2);
        detail_cost = (TextView) view.findViewById(R.id.detail_cost2);
        detail_time = (TextView) view.findViewById(R.id.detail_time2);
        detail_date = (TextView) view.findViewById(R.id.detail_date2);
        detail_description = (TextView) view.findViewById(R.id.detail_description);
        Youtube = (ImageButton)view.findViewById(R.id.youtube_button);

        getActivity().registerReceiver(mBatInfoReceiver, new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED));

        Bundle bundle = getArguments();
        event = (Event) bundle.getSerializable("event");

        detail_name.setText(event.getName());
        detail_name.setTransitionName(event.getName());

        detail_description.setText(event.getDescription());
        detail_cost.setText(event.getCost());
        detail_date.setText(event.getDate());
        detail_time.setText(event.getTime());
        detail_location.setText(event.getLocation());
        final String url_link_youtube = event.getYoutubeLink();
        final String youtube = "youtube";
        final String youtube2 = "youtu.be";
        Log.d("url2", url_link_youtube);

        Youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("url", url_link_youtube);
                if ( url_link_youtube.contains(youtube) || url_link_youtube.contains(youtube2)) {

                    if ( level < 15){
                        Toast.makeText(getActivity(),"Battery Level too low",Toast.LENGTH_SHORT).show();
                    }else {
                        Intent playvideo = new Intent(getActivity(), YoutubeTest.class);
                        playvideo.putExtra("Link",url_link_youtube);
                        startActivity(playvideo);
                    }
                }else {
                    Toast.makeText(getActivity(),"No Youtube Link", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button join_event = (Button)view.findViewById(R.id.join_event_button);
        join_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkout_out = new Intent(getActivity(), Checkout.class);
                checkout_out.putExtra("Event",event);
                startActivity(checkout_out);
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
    //http://programmerguru.com/android-tutorial/android-broadcast-receiver-example/
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        //When Event is published, onReceive method is called
        public void onReceive(Context c, Intent i) {
            //Get Battery %
             level = i.getIntExtra("level", 0);

        }

    };


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