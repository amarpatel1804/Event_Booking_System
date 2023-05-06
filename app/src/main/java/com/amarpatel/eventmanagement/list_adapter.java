package com.amarpatel.eventmanagement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class list_adapter extends RecyclerView.Adapter<list_adapter.viewholder> {

    Context context;
    ArrayList<Model> arrayList = new ArrayList<>();
    String city,text,name;
    public RecyclerviewClickListener list_listener;

    public list_adapter(String city, String text, String name) {
        this.city = city;
        this.text = text;
        this.name = name;
    }

    public list_adapter(Context context, ArrayList<Model> arrayList, RecyclerviewClickListener list_listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.list_listener = list_listener;
    }

    @NonNull
    @Override
    public list_adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewholder(LayoutInflater.from(context).inflate(R.layout.hall_cell,parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull list_adapter.viewholder holder, int position) {
        holder.txtname.setText(arrayList.get(position).name);
        holder.txtcapacity.setText("Upto " + arrayList.get(position).capacity);
        holder.txtaddress.setText(arrayList.get(position).address);
        Log.d("jayy",arrayList.get(position).name);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface RecyclerviewClickListener {
        void onClick(View v,int position);
    }

    public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        LinearLayout hall_cell;
        TextView txtname,txtcapacity,txtaddress;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            hall_cell = itemView.findViewById(R.id.linearhallcell);
            txtaddress = itemView.findViewById(R.id.txthalladdress);
            txtcapacity = itemView.findViewById(R.id.txthallcapacity);
            txtname = itemView.findViewById(R.id.txthallname);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            list_listener.onClick(view,getAdapterPosition());
        }
    }
}
