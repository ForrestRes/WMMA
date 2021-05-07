package com.example.wmma;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EarningsRecyclerViewAdapter extends RecyclerView.Adapter<EarningsRecyclerViewAdapter.ViewHolder> {

    private EarningsActivity earningsActivity;
    private ArrayList<String> hours;
    private ArrayList<String> totalShifts;
    private ArrayList<String> checks;
    private ArrayList<String> startDates;
    private ArrayList<String> endDates;

    public EarningsRecyclerViewAdapter(ArrayList<String> h, ArrayList<String> t, ArrayList<String> c, ArrayList<String> s, ArrayList<String> e, EarningsActivity a){
        earningsActivity = a;
        hours = h;
        totalShifts = t;
        checks = c;
        startDates = s;
        endDates = e;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.earnings_card_view_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EarningsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.CheckTV.setText("$"+checks.get(position));
        holder.StartDateTV.setText(startDates.get(position));
        holder.EndDateTV.setText(endDates.get(position));
        holder.TotalShiftsTV.setText(totalShifts.get(position));
        holder.HoursTV.setText(hours.get(position));
    }

    @Override
    public int getItemCount() {
        return checks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView CheckTV, StartDateTV, EndDateTV;
        TextView TotalShiftsTV, HoursTV;

        public ViewHolder(View itemView){
            super(itemView);
            CheckTV = (TextView)itemView.findViewById(R.id.tvEarningsCheck);
            StartDateTV = (TextView)itemView.findViewById(R.id.tvEarningsStartDate);
            EndDateTV = (TextView)itemView.findViewById(R.id.tvEarningsEndDate);
            TotalShiftsTV = (TextView)itemView.findViewById(R.id.tvEarningsNumShifts);
            HoursTV = (TextView)itemView.findViewById(R.id.tvEarningsNumHours);
        }
    }
}
