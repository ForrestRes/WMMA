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

public class TradeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    TextView TitleTV;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);

        TitleTV = (TextView)findViewById(R.id.tvTradeTitle);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //get info from db
        String type = "offeredShifts";
        String user_id = getIntent().getStringExtra("user_id");
        TradeWorker tradeWorker = new TradeWorker(this, this);
        tradeWorker.execute(type, user_id);
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
                //close drawer
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
            TitleTV.setText("There are no available shifts to take.");
        }
        TitleTV.setText("All available shifts to take are listed below.");

        ArrayList<String> dates = new ArrayList<String>();
        ArrayList<String> startTimes = new ArrayList<String>();
        ArrayList<String> endTimes = new ArrayList<String>();
        ArrayList<String> IDs = new ArrayList<String>();
        ArrayList<String> names = new ArrayList<String>();

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
            names.add(substring);

            substring = input.substring(0, input.indexOf(";"));
            if(input.indexOf(";")+1!=input.length()){
                input = input.substring(input.indexOf(";")+1);
            }
            IDs.add(substring);
        }

        //setup recycler view
        recyclerView = findViewById(R.id.rvTrade);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TradeRecyclerViewAdapter(IDs, names, dates, startTimes, endTimes, this);
        recyclerView.setAdapter(adapter);


    }
}