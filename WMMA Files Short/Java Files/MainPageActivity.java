package com.example.wmma;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Date;

public class MainPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    TextView NameTV, UnreadTV, ShiftDateTV, ShiftStartTV, ShiftEndTV, AwayStartTV, AwayEndTV;
    CardView ShiftCV, AwayCV, NoShiftCV, NoAwayCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E MMM d, yyyy");
        TextView DateTV = (TextView)findViewById(R.id.tvMainDate);
        DateTV.setText(simpleDateFormat.format(new Date()));

        NameTV = (TextView)findViewById(R.id.tvMainName);
        UnreadTV = (TextView)findViewById(R.id.tvMainUnread);
        ShiftDateTV = (TextView)findViewById(R.id.tvMainShiftDate);
        ShiftStartTV = (TextView)findViewById(R.id.tvMainShiftStart);
        ShiftEndTV = (TextView)findViewById(R.id.tvMainShiftEnd);
        AwayStartTV = (TextView)findViewById(R.id.tvMainTimeAwayStart);
        AwayEndTV = (TextView)findViewById(R.id.tvMainTimeAwayEnd);

        ShiftCV = (CardView)findViewById(R.id.cvMainShift);
        AwayCV = (CardView)findViewById(R.id.cvMainTimeAway);
        NoShiftCV = (CardView)findViewById(R.id.cvMainNoShift);
        NoAwayCV = (CardView)findViewById(R.id.cvMainNoTimeAway);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //call db
        String type = "initialize";
        String user_id = getIntent().getStringExtra("user_id");
        MainPageWorker mainWorker = new MainPageWorker(this, this);
        mainWorker.execute(type, user_id);
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
                //close drawer
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

        String substring = input.substring(0, input.indexOf(";"));
        input = input.substring(input.indexOf(";")+1);
        NameTV.setText(substring);

        substring = input.substring(0, input.indexOf(";"));
        input = input.substring(input.indexOf(";")+1);
        UnreadTV.setText("You have "+substring+"unread messages waiting.");

        substring = input.substring(0, input.indexOf(";"));
        input = input.substring(input.indexOf(";")+1);
        if(substring.equals("No Shift")){
            ShiftCV.setVisibility(View.GONE);
            NoShiftCV.setVisibility(View.VISIBLE);
        }else{
            ShiftCV.setVisibility(View.VISIBLE);
            NoShiftCV.setVisibility(View.GONE);

            ShiftDateTV.setText(substring);

            substring = input.substring(0, input.indexOf(";"));
            input = input.substring(input.indexOf(";")+1);
            ShiftStartTV.setText(substring);

            substring = input.substring(0, input.indexOf(";"));
            input = input.substring(input.indexOf(";")+1);
            ShiftEndTV.setText(substring);
        }

        substring = input.substring(0, input.indexOf(";"));
        input = input.substring(input.indexOf(";")+1);
        if(substring.equals("No Time Away")){
            AwayCV.setVisibility(View.GONE);
            NoAwayCV.setVisibility(View.VISIBLE);
        }else{
            AwayCV.setVisibility(View.VISIBLE);
            NoAwayCV.setVisibility(View.GONE);

            AwayStartTV.setText(substring);

            substring = input.substring(0, input.indexOf(";"));
            input = input.substring(input.indexOf(";")+1);
            AwayEndTV.setText(substring);
        }
    }
}