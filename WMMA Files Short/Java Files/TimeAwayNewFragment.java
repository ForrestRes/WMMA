package com.example.wmma;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class TimeAwayNewFragment extends Fragment implements View.OnClickListener {

    TimeAwayActivity timeAwayActivity;
    Button CancelB, SubmitB;
    EditText StartDateET, EndDateET;

    DatePickerDialog StartDatePickerDialog;
    DatePickerDialog EndDatePickerDialog;

    SimpleDateFormat dateFormat;

    public TimeAwayNewFragment(TimeAwayActivity ta) {
        timeAwayActivity = ta;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View rootView = inflater.inflate(R.layout.fragment_time_away_new, container, false);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        //tie layout to variables
        CancelB = (Button)rootView.findViewById(R.id.bTimeAwayNewCancel);
        SubmitB = (Button)rootView.findViewById(R.id.bTimeAwayNewSubmitRequest);
        StartDateET = (EditText)rootView.findViewById(R.id.etTimeAwayNewStartDate);
        StartDateET.setInputType(InputType.TYPE_NULL);
        StartDateET.requestFocus();
        EndDateET = (EditText)rootView.findViewById(R.id.etTimeAwayNewEndDate);
        EndDateET.setInputType(InputType.TYPE_NULL);

        CancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close fragment
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = (Fragment)fragmentManager.findFragmentById(R.id.fragment_container);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(fragment).commit();
            }
        });

        SubmitB.setOnClickListener(this);

        //setdatetimefield
        Calendar calendar = Calendar.getInstance();
        StartDatePickerDialog = new DatePickerDialog(timeAwayActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                StartDateET.setText(dateFormat.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        EndDatePickerDialog = new DatePickerDialog(timeAwayActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                EndDateET.setText(dateFormat.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        StartDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartDatePickerDialog.show();
            }
        });

        EndDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EndDatePickerDialog.show();
            }
        });

        return rootView;
    }

    @Override
    public void onClick(View v) {
        AlertDialog alertDialog = new AlertDialog.Builder(timeAwayActivity).create();
        alertDialog.setTitle("Submit Time Away Request");
        //check inputs
        Date startDate, endDate;
        Date currentDate = new Date();

        try{
            startDate = dateFormat.parse(StartDateET.getText().toString());
            endDate = dateFormat.parse(EndDateET.getText().toString());
        }catch (ParseException e){
            alertDialog.setMessage("Dates are invalid.");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.cancel();
                }
            });
            alertDialog.show();
            return;
        }

        //make sure end is after start
        if(startDate.after(endDate)) {
            alertDialog.setMessage("Start date cannot occur after end date.");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.cancel();
                }
            });
            alertDialog.show();
            return;
        }
        //make sure both are current dates
        if(currentDate.after(startDate)){
            alertDialog.setMessage("Please pick dates that are current(today or later).");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.cancel();
                }
            });
            alertDialog.show();
            return;
        }
        //call worker
        String type = "insert";
        String user_id = timeAwayActivity.getIntent().getStringExtra("user_id");
        TimeAwayWorker timeAwayWorker = new TimeAwayWorker(timeAwayActivity, timeAwayActivity, this);
        timeAwayWorker.execute(type, user_id, dateFormat.format(startDate), dateFormat.format(endDate));
    }

    public void finishInsert(String result){
        //inform user of results and maybe close fragment
        AlertDialog alertDialog = new AlertDialog.Builder(timeAwayActivity).create();
        alertDialog.setTitle("Submit Time Away Request");

        if(result.contains("Error")){
            alertDialog.setMessage("Error with server, please try again.");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.cancel();
                }
            });
            alertDialog.show();
            return;
        }
        else if(result.contains("Overlap")){
            alertDialog.setMessage("The dates requested overlap with another of your requests.");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.cancel();
                }
            });
            alertDialog.show();
            return;
        }

        alertDialog.setMessage("Request successfully submitted.");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
                //reload requests
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
        alertDialog.show();
    }

}