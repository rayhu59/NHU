package com.example.raych.nhu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by donnale on 4/25/17.
 */

public class PaidAdapter extends RecyclerView.Adapter<PaidAdapter.ViewHolder>{

    List eventdata;
    Context cont;
    static PaidAdapter.OnItemClickListener pfItemClickListener;

    public PaidAdapter(Context context,List data) {
        eventdata = data;
        cont = context;
    }

    @Override
    public PaidAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item;
        item = LayoutInflater.from(parent.getContext()).inflate(R.layout.hosting_card, parent, false);
        PaidAdapter.ViewHolder itemholder = new PaidAdapter.ViewHolder(item);
        return itemholder;
    }

    @Override
    public void onBindViewHolder(PaidAdapter.ViewHolder holder, int position) {
        //String name = eventdata.get("name");
        //            HashMap<String, ?> movie = (HashMap) mItems.get(position);
        HashMap e = (HashMap) eventdata.get(position);
        String name = e.get("name").toString();

        holder.title.setText(name);

    }


    public void SetOnItemClickListener(final PaidAdapter.OnItemClickListener pfItemClickListener){
        this.pfItemClickListener = pfItemClickListener;
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
                    if (pfItemClickListener != null){
                        if (getAdapterPosition() != RecyclerView.NO_POSITION){
                            pfItemClickListener.onItemClick(v, getAdapterPosition());
                        }
                    }
                }
            });

        }
    }


}