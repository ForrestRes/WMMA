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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ShiftsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    TextView TitleTV;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shifts);

        TitleTV = (TextView)findViewById(R.id.tvShiftsTitle);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //get info from db
        String type = "upcomingShifts";
        String user_id = getIntent().getStringExtra("user_id");
        ShiftsWorker shiftsWorker = new ShiftsWorker(this, this);
        shiftsWorker.execute(type, user_id);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawerLayout.closeDrawer(GravityCompat.START);

        Intent intent;

        switch (item.getItemId()){
            case R.id.nav_availability:

                break;
            case R.id.nav_shifts:
                //close drawer
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
                intent = new Intent(this, TimeAwayActivity.class);
                intent.putExtra("user_id", getIntent().getStringExtra("user_id"));
                startActivity(intent);
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

    public void finishInitialization(String input){
        if(input.length() == 0){
            TitleTV.setText("You have no upcoming shifts to show.");
            return;
        }
        TitleTV.setText("Your upcoming shifts are listed below.");

        ArrayList<String> dates = new ArrayList<String>();
        ArrayList<String> startTimes = new ArrayList<String>();
        ArrayList<String> endTimes = new ArrayList<String>();
        ArrayList<String> IDs = new ArrayList<String>();
        ArrayList<String> offers = new ArrayList<String>();

        String substring;
        while(input.indexOf(";")+1!=input.length()){
            substring = input.substring(0, input.indexOf(";"));
            input = input.substring(input.indexOf(";")+1);
            dates.add(substring);

            substring = input.substring(0, input.indexOf(";"));
            input = input.substring(input.indexOf(";")+1);
            startTimes.add(substring);

            substring = input.substring(0, input.indexOf(";"));
            input = input.substring(input.indexOf(";")+1);
            endTimes.add(substring);

            substring = input.substring(0, input.indexOf(";"));
            input = input.substring(input.indexOf(";")+1);
            IDs.add(substring);

            substring = input.substring(0, input.indexOf(";"));
            if(input.indexOf(";")+1!=input.length()){
                input = input.substring(input.indexOf(";")+1);
            }
            offers.add(substring);
        }

        //setup recycler view
        recyclerView = findViewById(R.id.rvShifts);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ShiftsRecyclerViewAdapter(IDs, offers, dates, startTimes, endTimes, this);
        recyclerView.setAdapter(adapter);
    }
}