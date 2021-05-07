package com.example.wmma;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class MessagesNewFragment extends Fragment {

    MessagesActivity activity;
    ChipGroup chipGroup;
    EditText MessageET;

    public MessagesNewFragment(MessagesActivity m) {
        activity = m;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_messages_new, container, false);

        chipGroup = (ChipGroup)rootView.findViewById(R.id.cgMessagesNewChipGroup);
        MessageET = (EditText)rootView.findViewById(R.id.etMessagesNewMessage);
        Button SendB = (Button)rootView.findViewById(R.id.bMessagesNewSend);
        Button CancelB = (Button)rootView.findViewById(R.id.bMessagesNewCancel);

        SendB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get checked chips as recipients
                String IDs = "";
                ChipGroup chipGroup = rootView.findViewById(R.id.cgMessagesNewChipGroup);
                Chip chip;

                for (int i=0; i<chipGroup.getChildCount();i++){
                    chip = (Chip)chipGroup.getChildAt(i);
                    if (chip.isChecked()){
                        IDs += ""+chip.getId() + ";";
                    }
                }
                if(IDs.length()==0){
                    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                    alertDialog.setTitle("Send New Message");
                    alertDialog.setMessage("Please select a recipient.");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.cancel();
                        }
                    });
                    alertDialog.show();
                    return;
                }

                EditText MessageET = (EditText)rootView.findViewById(R.id.etMessagesNewMessage);
                String message = MessageET.getText().toString();

                if(message.length()==0){

                    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                    alertDialog.setTitle("Send New Message");
                    alertDialog.setMessage("Please enter a message.");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.cancel();
                        }
                    });
                    alertDialog.show();
                    return;
                }

                //log in database
                String type = "newChat";
                String user_id = activity.getIntent().getStringExtra("user_id");
                MessagesWorker messagesWorker = new MessagesWorker(activity, activity);
                messagesWorker.execute(type, user_id, IDs, message);

            }
        });

        CancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close fragment
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                Fragment fragment = (Fragment)fragmentManager.findFragmentById(R.id.fragment_container);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(fragment).commit();
            }
        });


        //initialize chip group info
        String type = "getRecipients";
        String user_id = activity.getIntent().getStringExtra("user_id");
        MessagesWorker messagesWorker = new MessagesWorker(activity, activity, this);
        messagesWorker.execute(type, user_id);

        return rootView;
    }

    public void finishInitialization(String input){


        Chip chip;

        while(input.length()>0){
            chip = new Chip(activity);
            chip.setCheckable(true);
            chip.setText(input.substring(0,input.indexOf(";")));
            input = input.substring(input.indexOf(";")+1);
            chip.setId(Integer.parseInt(input.substring(0,input.indexOf(";"))));
            chipGroup.addView(chip);

            if(input.indexOf(";")+1==input.length())
                break;
            input = input.substring(input.indexOf(";")+1);

        }

    }
}