package com.example.raych.nhu;

/**
 * Created by raych on 4/24/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by donnale on 4/20/17.
 */

public class StoreAdapter extends FirebaseRecyclerAdapter<Event, StoreAdapter.ViewHolder> {

    static List<Map<String, ?>> mItems;
    static FireBaseListerner fireBaseListerner;
    Context mcontext;

    public StoreAdapter(Class<Event> eventClass, int joined_card, Class<ViewHolder> viewHolderClass, DatabaseReference childRef, Context context) {
        super(eventClass,joined_card,viewHolderClass,childRef);
        this.mcontext = context;
    }

    @Override
    protected void populateViewHolder(ViewHolder viewHolder, Event e, int position) {
        String name2 =  e.getName();
        String Youtube2 = e.getYoutubeLink();
        Log.v("CHECK", Youtube2);
        viewHolder.name.setText(name2);


    }

    public interface FireBaseListerner {
        public void cardClick(View view, int position);
    }

    public void SetOnItemClickListerner(FireBaseListerner listerner2) {
        this.fireBaseListerner = listerner2;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public  TextView name;
        public TextView location;
        public TextView num_guests;
        public TextView date;
        public ImageView icon;



        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.textView3);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fireBaseListerner != null){
                        fireBaseListerner.cardClick(v, getAdapterPosition());
                    }
                }
            });


        }


    }


}

