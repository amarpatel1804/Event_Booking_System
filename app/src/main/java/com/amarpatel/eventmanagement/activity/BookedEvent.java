package com.amarpatel.eventmanagement.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.amarpatel.eventmanagement.Helper;
import com.amarpatel.eventmanagement.Model;
import com.amarpatel.eventmanagement.R;
import com.amarpatel.eventmanagement.adapter.book_adapter;

import java.util.ArrayList;

public class BookedEvent extends AppCompatActivity {

//    TextView txtname,txtaddress,txtcapasity,txtdate,txttime;
    RecyclerView recyclerView;
    book_adapter adapter;
    ArrayList<Model> arrayList;
    Helper db;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_event);

        /*txtaddress = findViewById(R.id.txtbookaddress);
        txtcapasity = findViewById(R.id.txtbookcapacity);
        txtname = findViewById(R.id.txtbookname);
        txtdate = findViewById(R.id.txtbookeddate);
        txttime = findViewById(R.id.txtbookedtime);*/

        recyclerView = findViewById(R.id.recyclerbook);

        /*SharedPreferences preferences = getSharedPreferences("save",MODE_PRIVATE);
        boolean flag = preferences.getBoolean("flag",false);
        String name = preferences.getString("name","name");
        String address = preferences.getString("address","address");
        String date = preferences.getString("date","10/10/2022");
        String time = preferences.getString("time","06:00 am to 11:30 am");
        int capasity = preferences.getInt("capasity",500);

        if (flag){
            txtname.setText(name);
            txtaddress.setText(address);
            txtcapasity.setText(String.valueOf(capasity));
            txtdate.setText("Booked on " + date);
            txttime.setText("Time: " + time);
        }*/

        db = new Helper(this);
        showdata();

    }

    public void showdata(){
        Cursor get = db.showdata();
        if (get.getCount() == 0){
            showmessage("Error","Data not found");
        } else {
            arrayList = new ArrayList<>();

            while (get.moveToNext()){
                String name = get.getString(1);
                String capacity = get.getString(2);
                String address = get.getString(3);
                String date = get.getString(4);
                String time = get.getString(5);

                arrayList.add(new Model(name,capacity,address,date,time));
            }
        }

        adapter = new book_adapter(this,arrayList);
        recyclerView.setAdapter(adapter);
    }

    private void showmessage(String title, String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.show();
    }
}