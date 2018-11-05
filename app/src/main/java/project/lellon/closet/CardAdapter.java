package project.lellon.closet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    ArrayList<closet_item> items;
    Context context;

    public CardAdapter(Context context, ArrayList<closet_item> mArrayList) {

        this.context = context;
        this.items = mArrayList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_layout, null);
        CardViewHolder viewHolder = new CardViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CardAdapter.CardViewHolder cardViewHolder, int i) {
        cardViewHolder.Date_textView.setText(items.get(i).getDate());
        cardViewHolder.Name_textView.setText(items.get(i).getName());
        final CardAdapter.CardViewHolder holder = cardViewHolder;

        if (items.get(holder.getAdapterPosition()).getexist()) {

            holder.linearLayout.setBackgroundColor(Color.parseColor("#E91E63"));
            Log.d("TAG", String.valueOf(items.get(holder.getAdapterPosition()).getexist()));
        }
        else{
            holder.linearLayout.setBackgroundColor(Color.parseColor("#9CCC65"));
            Log.d("TAG", String.valueOf(items.get(holder.getAdapterPosition()).getexist()));
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setPressed(true);
                Intent intent = new Intent(context, InfActivity.class);
                intent.putExtra("name", items.get(holder.getAdapterPosition()).getName());
                intent.putExtra("date", items.get(holder.getAdapterPosition()).getDate());
                intent.putExtra("rfid", items.get(holder.getAdapterPosition()).getRFID_type());
                intent.putExtra("memo", items.get(holder.getAdapterPosition()).getmemo());
                intent.putExtra("exist", items.get(holder.getAdapterPosition()).getexist());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        CardView cardView;
        TextView Name_textView;
        TextView Date_textView;


        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linear);
            cardView = itemView.findViewById(R.id.cardview);
            Name_textView = itemView.findViewById(R.id.textview1);
            Date_textView = itemView.findViewById(R.id.textview2);


        }
    }
}

