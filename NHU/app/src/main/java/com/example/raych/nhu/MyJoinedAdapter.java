package com.example.raych.nhu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by donnale on 4/21/17.
 */

public class MyJoinedAdapter extends RecyclerView.Adapter<MyJoinedAdapter.ViewHolder> {
    static List<Map<String, ?>> joined_Items;
    static MyJoinedAdapter.OnItemClickListener join_ItemClickListener;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        public TextView name;
        public TextView location;
        public TextView cost;
        public TextView date;
        public ImageView icon;
        public CheckBox checkBox;


        public ViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.joincard_eventName);
            location = (TextView) view.findViewById(R.id.joincard_eventLocation);
            cost = (TextView) view.findViewById(R.id.joincard_cost);
            date = (TextView) view.findViewById(R.id.joincard_eventDate);
            icon = (ImageView) view.findViewById(R.id.joincard_event_icon);
            checkBox = (CheckBox) view.findViewById(R.id.joincard_checkBox);

            checkBox.setOnCheckedChangeListener(this);

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (join_ItemClickListener != null) {
                        if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                            join_ItemClickListener.onItemClick(v, getAdapterPosition());
                        }
                    }
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (join_ItemClickListener != null) {
                        if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                            join_ItemClickListener.onItemLongClick(v, getAdapterPosition());
                        }
                    }
                    return true;
                }
            });
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            HashMap movie = (HashMap) joined_Items.get(getAdapterPosition());
            if (isChecked) {
                movie.put("selection", true);

            } else {
                movie.put("selection", false);
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);

        public void onItemLongClick(View view, int position);
        //public void onOverflowMenuClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener joined_ItemClickListener) {
        this.join_ItemClickListener = joined_ItemClickListener;
    }

    public MyJoinedAdapter(Context context, List<Map<String, ?>> items) {
        joined_Items = items;
        this.context = context;
    }

    @Override
    public MyJoinedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.joined_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyJoinedAdapter.ViewHolder holder, int position) {
        HashMap<String, ?> movie = (HashMap) joined_Items.get(position);
            holder.icon.setImageResource((Integer) movie.get("image"));
            holder.name.setText((String) movie.get("name"));
            holder.location.setText((String) movie.get("director"));
            holder.cost.setText(String.valueOf(movie.get("rating")));
            holder.date.setText((String) movie.get("stars"));
            holder.checkBox.setChecked((Boolean) movie.get("selection"));
    }

    @Override
    public int getItemCount() {
        return joined_Items.size();
    }
//
//    @Override
//    public int getItemViewType(int position){
//
//        int num = 0;
//        if (sorted){
//            if (position < 5){
//                num = 1;
//            }
//
//            if (position >= 5 && position < 25){
//                num = 2;
//            }
//
//            if (position >= 25){
//                num = 3;
//            }
//        }
//        else
//            num = 0;
//
//        return num;
//    }
}





//    static List<Map<String, ?>> mItems;
//    static MyJoinedAdapter.OnItemClickListener mItemClickListener;
//    Context context;
//
//    public MyJoinedAdapter(Context context, List<Map<String,?>> items){
//        mItems = items;
//        this.context = context;
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
//        public TextView name;
//        public TextView location;
//        public TextView cost;
//        public TextView date;
//        public ImageView icon;
//        public CheckBox checkBox;
//
//        public ViewHolder(View view) {
//            super(view);
//            name = (TextView) view.findViewById(R.id.joincard_eventName);
//            location = (TextView) view.findViewById(R.id.joincard_eventLocation);
//            cost = (TextView) view.findViewById(R.id.joincard_cost);
//            date = (TextView) view.findViewById(R.id.joincard_eventDate);
//            icon = (ImageView) view.findViewById(R.id.joincard_event_icon);
//            checkBox = (CheckBox) view.findViewById(R.id.joincard_checkBox);
//            checkBox.setOnCheckedChangeListener(this);
//            view.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    if (mItemClickListener != null) {
//                        if (getAdapterPosition() != RecyclerView.NO_POSITION) {
//                            mItemClickListener.onItemClick(v, getAdapterPosition());
//                        }
//                    }
//                }
//            });
//
//            view.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    if (mItemClickListener != null) {
//                        if (getAdapterPosition() != RecyclerView.NO_POSITION) {
//                            mItemClickListener.onItemLongClick(v, getAdapterPosition());
//                        }
//                    }
//                    return true;
//                }
//            });
//        }
//
//        @Override
//        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
//            HashMap movie = (HashMap) mItems.get(getAdapterPosition());
//            if (isChecked) {
//                movie.put("selection", true);
//
//            } else {
//                movie.put("selection", false);
//            }
//        }
//    }
//
//    public interface OnItemClickListener {
//        public void onItemClick(View view, int position);
//        public void onItemLongClick(View view, int position);
//        //public void onOverflowMenuClick(View view, int position);
//    }
//
//    public void SetOnItemClickListener(final MyJoinedAdapter.OnItemClickListener mItemClickListener) {
//        this.mItemClickListener = mItemClickListener;
//    }
//
//    @Override
//    public MyJoinedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.joined_card, parent, false);
//        return new ViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(MyJoinedAdapter.ViewHolder holder, int position) {
//        HashMap<String, ?> movie = (HashMap) mItems.get(position);
//        holder.icon.setImageResource((Integer) movie.get("image"));
//        holder.name.setText((String) movie.get("name"));
//        holder.location.setText((String) movie.get("director"));
//        holder.cost.setText(String.valueOf(movie.get("rating")));
//        holder.date.setText((String) movie.get("stars"));
//        holder.checkBox.setChecked((Boolean) movie.get("selection"));
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//}
