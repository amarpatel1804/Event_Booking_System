package com.amarpatel.eventmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amarpatel.eventmanagement.R;
import com.amarpatel.eventmanagement.city;

import java.util.ArrayList;

public class city_adapter extends RecyclerView.Adapter<city_adapter.viewholder> {
    ArrayList<city> arrayList = new ArrayList<>();
    Context context;
    private RecyclerviewClickListener listener;

    public city_adapter(Context context, ArrayList<city> arrayList, RecyclerviewClickListener listener) {
        this.arrayList = arrayList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewholder(LayoutInflater.from(context).inflate(R.layout.cell, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.textView.setText(arrayList.get(position).getCity());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface RecyclerviewClickListener{
        void onClick(View v,int position);
    }

    public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textView;
        LinearLayout cell;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.txtcity);
            cell = itemView.findViewById(R.id.cell);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());
        }
    }
}
