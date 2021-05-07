package com.example.wmma;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessagesRecyclerViewAdapter extends RecyclerView.Adapter<MessagesRecyclerViewAdapter.ViewHolder> {

    private MessagesActivity messagesActivity;
    private ArrayList<String> recipients;
    private ArrayList<String> unreadMessageCount;
    private ArrayList<String> chatIDs;

    public MessagesRecyclerViewAdapter(ArrayList<String> c, ArrayList<String> r, ArrayList<String> u, MessagesActivity m){
        messagesActivity = m;
        recipients = r;
        unreadMessageCount = u;
        chatIDs = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.messages_card_view_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.RecipientsTV.setText(recipients.get(position));
        if(unreadMessageCount.get(position).equals("0")){
            holder.UnreadMessagesTV.setText("No Unread Messages");
        }else {
            holder.UnreadMessagesTV.setText(unreadMessageCount.get(position) + " Unread Messages");
        }
        holder.ViewB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open fragment
                FragmentManager fm = messagesActivity.getSupportFragmentManager();
                MessagesViewFragment fragment = new MessagesViewFragment(messagesActivity, chatIDs.get(position));
                fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public Button ViewB;
        public TextView RecipientsTV, UnreadMessagesTV;

        public ViewHolder(View itemView){
            super(itemView);
            RecipientsTV = (TextView)itemView.findViewById(R.id.tvMessagesRecipients);
            UnreadMessagesTV = (TextView)itemView.findViewById(R.id.tvMessagesUnreadMessages);
            ViewB = (Button)itemView.findViewById(R.id.bMessagesView);
        }
    }
}
