package com.example.wmma;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class TimeAwayActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ArrayList<String> startDates;
    ArrayList<String> endDates;
    ArrayList<String> approvalStatuses;
    ArrayList<String> requestIDs;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_away);

        //set side bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //get info from db
        String type = "initialize";
        String user_id = getIntent().getStringExtra("user_id");
        TimeAwayWorker timeAwayWorker = new TimeAwayWorker(this, this);
        timeAwayWorker.execute(type, user_id);
    }

    public void finishInitialization(String input){
        //check if empty
        TextView TitleTV = (TextView)findViewById(R.id.tvTimeAwayTitle);
        if(input.substring(0, input.indexOf(";")).equals("0")){
            TitleTV.setText("There are no current or upcoming time away requests to show. Submit a request with the button below.");
        }else{
            TitleTV.setText("All current or upcoming time away requests are listed below. You may need to scroll.");
        }
        //initialize arrays from input
        approvalStatuses = new ArrayList<String>();
        startDates = new ArrayList<String>();
        endDates = new ArrayList<String>();
        requestIDs = new ArrayList<String>();

        String inputSubstring = input.substring(0, input.indexOf(";"));
        input = input.substring(input.indexOf(";")+1);
        int numRequests = Integer.parseInt(inputSubstring);

        for(int i = 0; i<numRequests; i++){
            //request id
            inputSubstring = input.substring(0, input.indexOf(";"));
            input = input.substring(input.indexOf(";")+1);
            requestIDs.add(inputSubstring);

            //approval status
            inputSubstring = input.substring(0, input.indexOf(";"));
            input = input.substring(input.indexOf(";")+1);
            approvalStatuses.add(inputSubstring);

            //start date
            inputSubstring = input.substring(0, input.indexOf(";"));
            input = input.substring(input.indexOf(";")+1);
            startDates.add(inputSubstring);

            //end date
            inputSubstring = input.substring(0, input.indexOf(";"));
            if(input.length()>1)
                input = input.substring(input.indexOf(";")+1);
            endDates.add(inputSubstring);
        }

        //setup recycler view
        recyclerView = findViewById(R.id.rvTimeAway);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TimeAwayRecyclerViewAdapter(startDates, endDates, approvalStatuses, requestIDs, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawerLayout.closeDrawer(GravityCompat.START);

        Intent intent;

        switch (item.getItemId()){
            case R.id.nav_availability:

                break;
            case R.id.nav_shifts:
                intent = new Intent(this, ShiftsActivity.class);
                intent.putExtra("user_id", getIntent().getStringExtra("user_id"));
                startActivity(intent);
                break;
            case R.id.nav_earnings:
                intent = new Intent(this, EarningsActivity.class);
                intent.putExtra("user_id", getIntent().getStringExtra("user_id"));
                startActivity(intent);
                break;
            case R.id.nav_home:
                intent = new Intent(this, MainPageActivity.class);
                intent.putExtra("user_id", getIntent().getStringExtra("user_id"));
                startActivity(intent);
                break;
            case R.id.nav_messages:
                intent = new Intent(this, MessagesActivity.class);
                intent.putExtra("user_id", getIntent().getStringExtra("user_id"));
                startActivity(intent);
                break;
            case R.id.nav_settings:
                intent = new Intent(this, SettingsActivity.class);
                intent.putExtra("user_id", getIntent().getStringExtra("user_id"));
                startActivity(intent);
                break;
            case R.id.nav_shift_trade:
                intent = new Intent(this, TradeActivity.class);
                intent.putExtra("user_id", getIntent().getStringExtra("user_id"));
                startActivity(intent);
                break;
            case R.id.nav_time_away:
                //close drawer
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    public void onRequestTimeAway(android.view.View view){
        FragmentManager fm = getSupportFragmentManager();
        TimeAwayNewFragment fragment = new TimeAwayNewFragment(this);
        fm.beginTransaction().add(R.id.fragment_container,fragment).commit();

    }

}