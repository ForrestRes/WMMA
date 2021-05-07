package com.example.wmma;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessagesViewFragment extends Fragment {

    MessagesActivity activity;
    EditText MessageET;
    String chatID;
    RecyclerView recyclerView;
    ScrollView scrollView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    Thread thread;

    public MessagesViewFragment(MessagesActivity m, String i) {
        activity = m;
        chatID = i;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_messages_view, container, false);

        //setup main view
        Button BackB = (Button)rootView.findViewById(R.id.bMessagesViewBack);
        Button SendB = (Button)rootView.findViewById(R.id.bMessagesViewSend);
        MessageET = (EditText)rootView.findViewById(R.id.etMessagesViewMessage);

        MessagesViewFragment messagesViewFragment = this;

        BackB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //close MessagesView fragment
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(messagesViewFragment).commit();
            }
        });

        SendB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MessageET.getText().toString().equals("")){
                    return;
                }
                InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
                //start worker
                String type = "sendMessage";
                String user_id = activity.getIntent().getStringExtra("user_id");
                MessagesWorker messagesWorker = new MessagesWorker(activity, activity, messagesViewFragment);
                messagesWorker.execute(type, user_id, chatID, MessageET.getText().toString());
            }
        });

        //thread
        thread = new Thread(() -> {
            try {
                while (!thread.isInterrupted()) {
                    Thread.sleep(1000);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //get info from db
                            String type = "refreshMessages";
                            String user_id = activity.getIntent().getStringExtra("user_id");
                            MessagesWorker messagesWorker = new MessagesWorker(activity, activity, messagesViewFragment);
                            messagesWorker.execute(type, user_id, chatID);
                        }
                    });
                }
            } catch (InterruptedException e) {
            }
        });

        thread.start();

        return rootView;
    }

    public void refreshMessages(String input){

        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> messages = new ArrayList<String>();
        ArrayList<String> dateTimes = new ArrayList<String>();
        int count = Integer.parseInt(input.substring(0, input.indexOf(";")));

        for(int i = 0; i<count; i++){
            input = input.substring(input.indexOf(";")+1);
            names.add(input.substring(0, input.indexOf(";")));
            input = input.substring(input.indexOf(";")+1);
            messages.add(input.substring(0, input.indexOf(";")));
            input = input.substring(input.indexOf(";")+1);
            dateTimes.add(input.substring(0, input.indexOf(";")));
        }
        //setup recycler view
        recyclerView = activity.findViewById(R.id.rvMessagesView);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MessagesViewRecyclerViewAdapter(dateTimes, messages, names, activity);
        recyclerView.setAdapter(adapter);
    }

    public void messageSent(String input){
        MessageET.setText("");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.stop();
    }
}