package com.example.wmma;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SettingsFAQRecyclerViewAdapter extends RecyclerView.Adapter<SettingsFAQRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> questions;
    private ArrayList<String> answers;
    private SettingsFAQFragment fragment;

    public SettingsFAQRecyclerViewAdapter(ArrayList<String> q, ArrayList<String> a, SettingsFAQFragment f){
        questions = q;
        answers = a;
        fragment = f;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.settings_faq_card_view_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsFAQRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.QuestionTV.setText(questions.get(position));
        holder.AnswerTV.setText(answers.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.AnswerTV.getVisibility()==View.GONE){
                    holder.AnswerTV.setVisibility(View.VISIBLE);
                    holder.Icon.setImageResource(R.drawable.ic_down);
                }
                else{
                    holder.AnswerTV.setVisibility(View.GONE);
                    holder.Icon.setImageResource(R.drawable.ic_right);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView QuestionTV, AnswerTV;
        public CardView cardView;
        public ImageView Icon;

        public ViewHolder(View itemView){
            super(itemView);
            QuestionTV = (TextView)itemView.findViewById(R.id.tvSettingsFAQQuestion);
            AnswerTV = (TextView)itemView.findViewById(R.id.tvSettingsFAQAnswer) ;
            cardView = (CardView) itemView.findViewById(R.id.cvSettingsFAQQuestion);
            Icon = (ImageView) itemView.findViewById(R.id.ivSettingsFAQIcon);
        }
    }
}
