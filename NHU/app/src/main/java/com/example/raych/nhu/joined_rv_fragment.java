package com.example.raych.nhu;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

/**
 * Created by donnale on 4/21/17.
 */

public class joined_rv_fragment extends android.support.v4.app.Fragment{
    RecyclerView rv_joined;
    MyJoinedAdapter myJoinedAdapter;
    RecyclerView.LayoutManager jlayoutManager;
    //MovieData movieData = new MovieData();
    CustomOnClickListener myListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View currentView = inflater.inflate(R.layout.joined_rv_fragment_layout, container, false);

        // myListener = (CustomOnClickListener) currentView.getContext();

        rv_joined = (RecyclerView) currentView.findViewById(R.id.my_joined_recycler_view);
        rv_joined.setHasFixedSize(true);
        jlayoutManager = new LinearLayoutManager(currentView.getContext());
        rv_joined.setLayoutManager(jlayoutManager);
      //  myJoinedAdapter = new MyJoinedAdapter(getActivity(), movieData.getMoviesList());
      //  rv_joined.setAdapter(new SlideInBottomAnimationAdapter(myJoinedAdapter));


        return currentView;
    }
}
