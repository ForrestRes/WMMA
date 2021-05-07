package com.example.wmma;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TimeAwayRecyclerViewAdapter extends RecyclerView.Adapter<TimeAwayRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> startDates;
    private ArrayList<String> endDates;
    private ArrayList<String> approvalStatuses;
    private ArrayList<String> requestIDs;
    private TimeAwayActivity timeAwayActivity;

    public TimeAwayRecyclerViewAdapter(ArrayList<String> sd, ArrayList<String> ed, ArrayList<String> a, ArrayList<String> r, TimeAwayActivity ta){
        startDates = sd;
        endDates = ed;
        approvalStatuses = a;
        requestIDs = r;
        timeAwayActivity = ta;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.time_away_card_view_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TimeAwayRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.StartDateTV.setText(startDates.get(position));
        holder.EndDateTV.setText(endDates.get(position));
        holder.ApprovedTV.setText(approvalStatuses.get(position));
        holder.EditB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //open fragment by sending requestID
                FragmentManager fm = timeAwayActivity.getSupportFragmentManager();
                TimeAwayEditFragment fragment = new TimeAwayEditFragment(requestIDs.get(position), timeAwayActivity);
                fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
            }
        });
        if(approvalStatuses.get(position).equals("Approved")){
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#90EE90"));
        }else{
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#FFCCCB"));
        }
    }

    @Override
    public int getItemCount() {
        return approvalStatuses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView ApprovedTV,StartDateTV, EndDateTV;
        public Button EditB;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView){
            super(itemView);
            ApprovedTV = (TextView)itemView.findViewById(R.id.tvTimeAwayApproved);
            StartDateTV = (TextView)itemView.findViewById(R.id.tvTimeAwayStartDate);
            EndDateTV = (TextView)itemView.findViewById(R.id.tvTimeAwayEndDate);
            EditB = (Button)itemView.findViewById(R.id.bTimeAwayEdit);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.rlTimeAway);
        }
    }
}
