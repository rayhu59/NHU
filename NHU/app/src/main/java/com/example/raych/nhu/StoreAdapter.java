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
    static OnItemClickListener mItemClickListener;
    Context mcontext;

    public StoreAdapter(Class<Event> eventClass, int joined_card, Class<ViewHolder> viewHolderClass, DatabaseReference childRef, Context context) {
        super(eventClass,joined_card,viewHolderClass,childRef);
        this.mcontext = context;
    }

    @Override
    protected void populateViewHolder(ViewHolder viewHolder, Event e, int position) {
        String name2 =  e.getName();
        String description2 = e.getDescription();
        String location2 =  e.getLocation();
        String guestsNumber2 =  e.getGuests();
        String time2 =  e.getTime();
        String date2 =  e.getDate();
        String cost2 =  e.getCost();
        String Youtube2 = e.getYoutubeLink();
        Log.v("CHECK", Youtube2);
        viewHolder.name.setText(name2);
//        viewHolder.location.setText(location2);
//        viewHolder.num_guests.setText(guestsNumber2);
//        viewHolder.date.setText(date2);



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
            //  location = (TextView) view.findViewById(R.id.hostcard_eventLocation);
            //  num_guests = (TextView) view.findViewById(R.id.hostcard_num_guests);
            //  date = (TextView) view.findViewById(R.id.hostcard_eventDate);
            //  icon = (ImageView) view.findViewById(R.id.hostcard_event_icon);


            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                            mItemClickListener.onItemClick(v, getAdapterPosition());
                        }
                    }
                }
            });


        }


    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

        /*
        @Override
        public MyHostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hosting_card, parent, false);
            return new ViewHolder(v);
        }
        */



}

