package com.example.raych.nhu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raych on 4/24/2017.
 */


public class HostAdapter extends RecyclerView.Adapter<HostAdapter.ViewHolder>  {
    List eventdata;
    Context cont;
    static OnItemClickListener mItemClickListener;

    public HostAdapter(Context context,List data) {
        eventdata = data;
        cont = context;
    }

    @Override
    public HostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item;
        item = LayoutInflater.from(parent.getContext()).inflate(R.layout.hosting_card, parent, false);
        ViewHolder itemholder = new ViewHolder(item);
        return itemholder;
    }

    @Override
    public void onBindViewHolder(HostAdapter.ViewHolder holder, int position) {
        String name = eventdata.get(position).toString();
        holder.title.setTransitionName(eventdata.get(position).toString());
        holder.title.setText(name);

    }


    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener{
        public void onItemClick(View view, int position);
    }

    @Override
    public int getItemCount() {
        return eventdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.event_title);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v){
                    if (mItemClickListener != null){
                        if (getAdapterPosition() != RecyclerView.NO_POSITION){
                            mItemClickListener.onItemClick(v, getAdapterPosition());
                        }
                    }
                }
            });

        }
    }
}
