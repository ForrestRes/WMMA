package com.example.wmma;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SettingsFAQFragment extends Fragment {

    SettingsActivity settingsActivity;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SettingsFAQRecyclerViewAdapter adapter;

    public SettingsFAQFragment(SettingsActivity activity) {
        settingsActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_settings_f_a_q, container, false);

        //get info from db
        FAQWorker faqWorker = new FAQWorker(settingsActivity, this);
        faqWorker.execute();

        return rootView;
    }

    public void setupFAQ(String input){
        ArrayList<String> questions = new ArrayList<String>(), answers = new ArrayList<String>();
        String inputSubstring = "";

        int i = 0;
        while(!input.equals("")){
            //request id
            inputSubstring = input.substring(0, input.indexOf(";"));
            input = input.substring(input.indexOf(";")+1);
            questions.add(inputSubstring);

            //approval status
            inputSubstring = input.substring(0, input.indexOf(";"));
            input = input.substring(input.indexOf(";")+1);
            answers.add(inputSubstring);
        }

        //setup recycler view
        recyclerView = settingsActivity.findViewById(R.id.rvSettingsFAQ);
        layoutManager = new LinearLayoutManager(settingsActivity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SettingsFAQRecyclerViewAdapter(questions, answers, this);
        recyclerView.setAdapter(adapter);
    }
}