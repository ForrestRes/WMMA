package com.example.wmma;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessagesViewRecyclerViewAdapter extends RecyclerView.Adapter<MessagesViewRecyclerViewAdapter.ViewHolder> {

    private MessagesActivity messagesActivity;
    private ArrayList<String> messages;
    private ArrayList<String> names;
    private ArrayList<String> dateTimes;

    public MessagesViewRecyclerViewAdapter(ArrayList<String> d, ArrayList<String> m, ArrayList<String> n, MessagesActivity a){
        messagesActivity = a;
        messages = m;
        names = n;
        dateTimes = d;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.messages_view_card_view_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.MessageTV.setText(messages.get(position));
        holder.NameTV.setText(names.get(position));
        holder.TimeTV.setText(dateTimes.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView MessageTV, NameTV, TimeTV;

        public ViewHolder(View itemView){
            super(itemView);
            MessageTV = (TextView)itemView.findViewById(R.id.tvMessagesViewMessage);
            NameTV = (TextView)itemView.findViewById(R.id.tvMessagesViewName);
            TimeTV = (TextView)itemView.findViewById(R.id.tvMessagesViewTime);
        }
    }
}
