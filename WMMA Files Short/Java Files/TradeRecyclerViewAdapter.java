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

public class TradeRecyclerViewAdapter extends RecyclerView.Adapter<TradeRecyclerViewAdapter.ViewHolder> {

    private TradeActivity tradeActivity;
    private ArrayList<String> dates;
    private ArrayList<String> startTimes;
    private ArrayList<String> endTimes;
    private ArrayList<String> names;
    private ArrayList<String> IDs;

    public TradeRecyclerViewAdapter(ArrayList<String> i, ArrayList<String> n, ArrayList<String> d, ArrayList<String> s, ArrayList<String> e, TradeActivity a){
        tradeActivity = a;
        dates = d;
        startTimes = s;
        endTimes = e;
        names = n;
        IDs = i;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trade_card_view_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TradeRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.DateTV.setText(dates.get(position));
        holder.StartTimeTV.setText(startTimes.get(position));
        holder.EndTimeTV.setText(endTimes.get(position));
        holder.NameTV.setText(names.get(position));
        holder.TakeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(tradeActivity).create();
                alertDialog.setTitle("Take Shift");
                alertDialog.setMessage("Would you like to take the selected shift? This cannot be undone.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //query db
                        String type = "takeShift";
                        String user_id = tradeActivity.getIntent().getStringExtra("user_id");
                        TradeWorker tradeWorker = new TradeWorker(tradeActivity, tradeActivity);
                        tradeWorker.execute(type, user_id, IDs.get(position));

                        //query db
                        type = "offeredShifts";
                        user_id = tradeActivity.getIntent().getStringExtra("user_id");
                        tradeWorker = new TradeWorker(tradeActivity, tradeActivity);
                        tradeWorker.execute(type, user_id, IDs.get(position));
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
        TextView NameTV;
        Button TakeB;

        public ViewHolder(View itemView){
            super(itemView);
            DateTV = (TextView)itemView.findViewById(R.id.tvTradeDate);
            StartTimeTV = (TextView)itemView.findViewById(R.id.tvTradeStartTime);
            EndTimeTV = (TextView) itemView.findViewById(R.id.tvTradeEndTime);
            TakeB = (Button)itemView.findViewById(R.id.bTradeTake);
            NameTV = (TextView)itemView.findViewById(R.id.tvTradeName);
        }
    }
}
