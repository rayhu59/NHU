package com.example.raych.nhu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

import java.util.HashMap;

/**
 * Created by donnale on 4/20/17.
 */

public class host_rv_fragment extends android.support.v4.app.Fragment {

    RecyclerView rv_hosting;
    MyHostAdapter myHostAdapter;
    RecyclerView.LayoutManager layoutManager;
    //MovieData movieData = new MovieData();
    CustomOnClickListener myListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View currentView = inflater.inflate(R.layout.host_rv_fragment_layout, container, false);

       // myListener = (CustomOnClickListener) currentView.getContext();
        rv_hosting = (RecyclerView) currentView.findViewById(R.id.my_hosting_recycler_view);
        rv_hosting.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(currentView.getContext());
        rv_hosting.setLayoutManager(layoutManager);
//        myHostAdapter = new MyHostAdapter(getActivity(), movieData.getMoviesList());
//        rv_hosting.setAdapter(new SlideInBottomAnimationAdapter(myHostAdapter));



//        myHostAdapter.SetOnItemClickListener(new MyHostAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                myListener.onRVclicked(position);
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//                //movieData.add
//                HashMap movie = (HashMap)((HashMap) movieData.getItem(position)).clone();
//                movieData.getMoviesList().add(position+1, movie);
//                //notify ad
//                myHostAdapter.notifyItemInserted(position+1);
//            }
//        });

        return currentView;
    }

}
