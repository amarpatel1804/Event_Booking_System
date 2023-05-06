package com.amarpatel.eventmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.amarpatel.eventmanagement.MyApplication;
import com.amarpatel.eventmanagement.R;
import com.amarpatel.eventmanagement.city;
import com.amarpatel.eventmanagement.adapter.city_adapter;

import java.util.ArrayList;

public class locationActivity extends AppCompatActivity{

    TextView birthdaytxt;
    RecyclerView recyclerView;
    city_adapter adapter;
    ArrayList<city> arrayList = new ArrayList<>();
    private city_adapter.RecyclerviewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        recyclerView = findViewById(R.id.recycler);
        MyApplication.setRecyclerItemMargin(locationActivity.this,recyclerView);

        arrayList.add(new city("Ahemdabad"));
        arrayList.add(new city("Gandhinagar"));
        arrayList.add(new city("Bharuch"));
        arrayList.add(new city("Vadodara"));
        arrayList.add(new city("Unjha"));
        arrayList.add(new city("Jhalod"));
        arrayList.add(new city("Surat"));
        arrayList.add(new city("Jamnagar"));
        arrayList.add(new city("Junagadh"));
        arrayList.add(new city("Rajkot"));
        arrayList.add(new city("Mehsana"));
        arrayList.add(new city("Anand"));
        arrayList.add(new city("Amreli"));
        arrayList.add(new city("Dahod"));
        arrayList.add(new city("Surendranagar"));
        arrayList.add(new city("Valsad"));

        setOnClicklistener();
        adapter  = new city_adapter(this,arrayList,listener);
        recyclerView.setAdapter(adapter);
    }

    private void setOnClicklistener() {
        listener = new city_adapter.RecyclerviewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(locationActivity.this, RequirementAvtivity.class);
                intent.putExtra("city",arrayList.get(position).getCity());
                startActivity(intent);
            }
        };
    }
}