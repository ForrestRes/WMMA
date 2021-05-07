package com.example.wmma;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class TimeAwayEditFragment extends Fragment {

    String requestID;
    TimeAwayActivity timeAwayActivity;
    TextView StartDateTV, EndDateTV, ApprovedTV;

    public TimeAwayEditFragment(){

    }

    public TimeAwayEditFragment(String r, TimeAwayActivity ta){
        requestID = r;
        timeAwayActivity = ta;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View rootView = inflater.inflate(R.layout.fragment_time_away_edit, container, false);
        final Button DeleteB = rootView.findViewById(R.id.bTimeAwayRequestDelete);
        final Button BackB = rootView.findViewById(R.id.bTimeAwayRequestBack);

        StartDateTV = (TextView)rootView.findViewById(R.id.tvTimeAwayRequestStartDate);
        EndDateTV = (TextView)rootView.findViewById(R.id.tvTimeAwaysRequestEndDate);
        ApprovedTV = (TextView)rootView.findViewById(R.id.tvTimeAwayRequestApproval);

        DeleteB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Delete Time Away Request");
                alertDialog.setMessage("Would you like to delete this Time Away Request? This action cannot be undone.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Delete Request", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //delete the request and return to activity
                        //call worker to delete entry with
                        TimeAwayActivity activity = (TimeAwayActivity)getActivity();
                        TimeAwayWorker worker = new TimeAwayWorker(getContext(),activity);
                        worker.execute("delete", requestID);

                        //get info from db
                        String type = "initialize";
                        String user_id = timeAwayActivity.getIntent().getStringExtra("user_id");
                        TimeAwayWorker timeAwayWorker = new TimeAwayWorker(timeAwayActivity, timeAwayActivity);
                        timeAwayWorker.execute(type, user_id);

                        //close fragment
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        Fragment fragment = (Fragment)fragmentManager.findFragmentById(R.id.fragment_container);
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.remove(fragment).commit();
                    }
                });

                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Go Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close alert dialog and dont delete
                        alertDialog.cancel();
                    }
                });
                alertDialog.show();



            }
        });

        BackB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = (Fragment)fragmentManager.findFragmentById(R.id.fragment_container);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(fragment).commit();
            }
        });

        //
        String type = "edit";
        TimeAwayWorker timeAwayWorker = new TimeAwayWorker(timeAwayActivity, timeAwayActivity, this);
        timeAwayWorker.execute(type, requestID);

        return rootView;
    }

    public void finishSetup(String result){
        String temp;
        temp = result.substring(0, result.indexOf(";"));
        result = result.substring(result.indexOf(";")+1);
        if(temp.equals("0"))
            ApprovedTV.setText("Not Approved");
        else
            ApprovedTV.setText("Approved");
        temp = result.substring(0, result.indexOf(";"));
        result = result.substring(result.indexOf(";")+1);
        StartDateTV.setText(temp);
        temp = result.substring(0, result.indexOf(";"));
        result = result.substring(result.indexOf(";")+1);
        EndDateTV.setText(temp);

    }
}