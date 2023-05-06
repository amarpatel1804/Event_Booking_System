package com.amarpatel.eventmanagement.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amarpatel.eventmanagement.Model;
import com.amarpatel.eventmanagement.R;
import com.amarpatel.eventmanagement.list_adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListOfHall extends AppCompatActivity {

    TextView txtlist;
    RecyclerView recyclerView;
    list_adapter adapter,adapter1;
    ArrayList<String> arr = new ArrayList<>();
    ArrayList<Model> arrayList = new ArrayList<>();
    String text, city, address,capacity;
    ProgressBar progressBar;
    private list_adapter.RecyclerviewClickListener list_listener;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_hall);

        txtlist = findViewById(R.id.txtlistof);
        progressBar = findViewById(R.id.progressbarlist);
        recyclerView = findViewById(R.id.recycleroflist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        city = intent.getStringExtra("city");
        text = intent.getStringExtra("btn_name");
        txtlist.setText(String.format("List of %s", text));
        progressBar.setVisibility(View.VISIBLE);
//        Log.d("jay", city);
//        Log.d("jay", text);
        getdata(city, text);
    }

    private void getdata(String city, String text) {
//        Log.d("jay", "in getdata");
        database = FirebaseDatabase.getInstance();

//        Log.d("jay","ondata changed " + city);
//        Log.d("jay","ondata changed " + text);
        databaseReference = database.getReference().child("city").child(String.format("%s",city)).child(String.format("%s",text));

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.d("jay", "in listner");
//                Log.d("jay", String.valueOf(snapshot.getChildrenCount()));
                for (DataSnapshot ds : snapshot.getChildren()) {
                    arr.add(ds.getKey());
                }
                for (int i=0; i< snapshot.getChildrenCount(); i++){
                    setvalue(city,text,i);
                }

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListOfHall.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setOnClickListener(String city, String text) {

        list_listener = new list_adapter.RecyclerviewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(ListOfHall.this, Hall.class);
                    intent.putExtra("city",city);
                    intent.putExtra("btn_name",text);
                    intent.putExtra("name",arr.get(position));
                    intent.putExtra("address",address);
                    intent.putExtra("capasity",capacity);
                    startActivity(intent);
            }
        };
    }

    private void setvalue(String city, String text, int i) {
//        Log.d("jay", "in set value");
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("city").child(city).child(text);
//        Log.d("jay", String.valueOf(i));
        databaseReference.child(this.arr.get(i)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
//                        Log.d("jay", "in complete method");
                        DataSnapshot dataSnapshot = task.getResult();
                        String name = ListOfHall.this.arr.get(i);
                        capacity = String.valueOf(dataSnapshot.child("capacity").getValue());
                        address = String.valueOf(dataSnapshot.child("address").getValue());

                        arrayList.add(new Model(name,address,capacity));
//                        Log.d("jay", address);

                        setOnClickListener(city,text);
                        adapter = new list_adapter(getApplicationContext(), arrayList, list_listener);
                        recyclerView.setAdapter(adapter);

                    } else {
                        Toast.makeText(ListOfHall.this, "Data not exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ListOfHall.this, "Failed to Read data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}