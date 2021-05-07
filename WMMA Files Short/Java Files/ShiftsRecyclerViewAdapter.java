package com.example.wmma;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShiftsRecyclerViewAdapter extends RecyclerView.Adapter<ShiftsRecyclerViewAdapter.ViewHolder> {

    private ShiftsActivity shiftsActivity;
    private ArrayList<String> dates;
    private ArrayList<String> startTimes;
    private ArrayList<String> endTimes;
    private ArrayList<String> offers;
    private ArrayList<String> IDs;

    public ShiftsRecyclerViewAdapter(ArrayList<String> i, ArrayList<String> o, ArrayList<String> d, ArrayList<String> s, ArrayList<String> e, ShiftsActivity a){
        shiftsActivity = a;
        dates = d;
        startTimes = s;
        endTimes = e;
        offers = o;
        IDs = i;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shifts_card_view_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShiftsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.DateTV.setText(dates.get(position));
        holder.StartTimeTV.setText(startTimes.get(position));
        holder.EndTimeTV.setText(endTimes.get(position));
        if(offers.get(position).equals("Offered")){
            holder.OfferTV.setVisibility(View.VISIBLE);
            holder.TradeB.setVisibility(View.GONE);
        }else{
            holder.OfferTV.setVisibility(View.GONE);
            holder.TradeB.setVisibility(View.VISIBLE);
        }
        holder.TradeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(shiftsActivity).create();
                alertDialog.setTitle("Offer Shift");
                alertDialog.setMessage("Would you like to offer this shift for trade? This cannot be undone.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //offer shift for trade
                        holder.OfferTV.setVisibility(View.VISIBLE);
                        holder.TradeB.setVisibility(View.GONE);
                        //query db
                        String type = "offerShift";
                        String user_id = shiftsActivity.getIntent().getStringExtra("user_id");
                        ShiftsWorker shiftsWorker = new ShiftsWorker(shiftsActivity, shiftsActivity);
                        shiftsWorker.execute(type, user_id, IDs.get(position));
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close alert
                        alertDialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView DateTV, StartTimeTV, EndTimeTV;
        TextView OfferTV;
        Button TradeB;

        public ViewHolder(View itemView){
            super(itemView);
            DateTV = (TextView)itemView.findViewById(R.id.tvShiftsDate);
            StartTimeTV = (TextView)itemView.findViewById(R.id.tvShiftsStartTime);
            EndTimeTV = (TextView) itemView.findViewById(R.id.tvShiftsEndTime);
            TradeB = (Button)itemView.findViewById(R.id.bShiftsTrade);
            OfferTV = (TextView)itemView.findViewById(R.id.tvShiftsTrade);
        }
    }
}
