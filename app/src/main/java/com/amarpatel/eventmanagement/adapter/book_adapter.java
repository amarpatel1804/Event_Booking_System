package com.amarpatel.eventmanagement.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.amarpatel.eventmanagement.Helper;
import com.amarpatel.eventmanagement.Model;
import com.amarpatel.eventmanagement.R;

import java.util.ArrayList;

public class book_adapter extends RecyclerView.Adapter<book_adapter.viewholder> {

    Context context;
    ArrayList<Model> arrayList = new ArrayList<>();
    Helper db;

    public book_adapter(Context context, ArrayList<Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewholder(LayoutInflater.from(context).inflate(R.layout.book_cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(arrayList.get(position).name);
        holder.capacity.setText(arrayList.get(position).capacity);
        holder.address.setText(arrayList.get(position).address);
        holder.date.setText("Booked Date: " + arrayList.get(position).date);
        holder.time.setText("Time: " + arrayList.get(position).time);

        db = new Helper(context);
        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Are you want to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String r = db.deletedata(arrayList.get(position).name);
                        Toast.makeText(context, "Data deleted", Toast.LENGTH_SHORT).show();

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        builder.setCancelable(true);
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        TextView name, capacity, address, date, time;
        LinearLayout layout;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtbookname);
            capacity = itemView.findViewById(R.id.txtbookcapacity);
            address = itemView.findViewById(R.id.txtbookaddress);
            date = itemView.findViewById(R.id.txtbookeddate);
            time = itemView.findViewById(R.id.txtbookedtime);
            layout = itemView.findViewById(R.id.linearbookcell);
        }
    }
}
