package com.example.wmma;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MessagesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        MessagesActivity activity = this;
        thread = new Thread(() -> {
            try {
                while (!thread.isInterrupted()) {
                    Thread.sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //get info from db
                            String type = "initialize";
                            String user_id = getIntent().getStringExtra("user_id");
                            MessagesWorker messagesWorker = new MessagesWorker(activity, activity);
                            messagesWorker.execute(type, user_id);
                        }
                    });
                }
            } catch (InterruptedException e) {
            }
        });

        thread.start();

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
                //close nav
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

        //check if empty
        TextView TitleTV = (TextView)findViewById(R.id.tvMessagesTitle);
        if(input.substring(0, input.indexOf(";")).equals("0")){
            TitleTV.setText("You do not have any chat history. Start a chat with the button below.");
        }else{
            TitleTV.setText("All chats available to you are listed below.");
        }
        //initialize arrays from input
        ArrayList<String> recipients = new ArrayList<String>();
        ArrayList<String> unreadMessageCount = new ArrayList<String>();
        ArrayList<String> chatIDs = new ArrayList<String>();

        String inputSubstring = input.substring(0, input.indexOf(";"));
        input = input.substring(input.indexOf(";")+1);
        int numChats = Integer.parseInt(inputSubstring);

        for(int i = 0; i<numChats; i++){
            inputSubstring = input.substring(0, input.indexOf(";"));
            input = input.substring(input.indexOf(";")+1);
            chatIDs.add(inputSubstring);

            inputSubstring = input.substring(0, input.indexOf(";"));
            input = input.substring(input.indexOf(";")+1);
            recipients.add(inputSubstring);

            inputSubstring = input.substring(0, input.indexOf(";"));
            input = input.substring(input.indexOf(";")+1);
            unreadMessageCount.add(inputSubstring);
        }

        //setup recycler view
        recyclerView = findViewById(R.id.rvShifts);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MessagesRecyclerViewAdapter(chatIDs, recipients, unreadMessageCount, this);
        recyclerView.setAdapter(adapter);
    }

    public void onNewMessage(View view){
        //start fragment
        FragmentManager fm = getSupportFragmentManager();
        MessagesNewFragment fragment = new MessagesNewFragment(this);
        fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
    }

    public void finishNewChat(String input){
        //close MessagesNew fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = (Fragment)fragmentManager.findFragmentById(R.id.fragment_container);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment).commit();

        //open messagesView fragment
        MessagesViewFragment messagesViewFragment = new MessagesViewFragment(this,input);
        fragmentManager.beginTransaction().add(R.id.fragment_container,messagesViewFragment).commit();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        thread.stop();
    }
}