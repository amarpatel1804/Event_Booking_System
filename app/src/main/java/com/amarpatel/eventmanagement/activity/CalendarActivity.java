package com.amarpatel.eventmanagement.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.amarpatel.eventmanagement.R;

public class CalendarActivity extends AppCompatActivity {

    Button btntime1,btntime2,btntime3,btnpayment;
    CalendarView calendarView;
    String date,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        initiate();

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        int capasity = intent.getIntExtra("capasity",500);


        btntime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btntime1.setBackgroundResource(R.drawable.time_cell);
                btntime2.setBackgroundResource(R.drawable.city_cell);
                btntime3.setBackgroundResource(R.drawable.city_cell);
                time = (String) btntime1.getText();
            }
        });

        btntime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btntime2.setBackgroundResource(R.drawable.time_cell);
                btntime1.setBackgroundResource(R.drawable.city_cell);
                btntime3.setBackgroundResource(R.drawable.city_cell);
                time = (String) btntime2.getText();
            }
        });

        btntime3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btntime3.setBackgroundResource(R.drawable.time_cell);
                btntime1.setBackgroundResource(R.drawable.city_cell);
                btntime2.setBackgroundResource(R.drawable.city_cell);
                time = (String) btntime2.getText();
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                i1++;
                date = i2 + "/" + i1 + "/" + i;
            }
        });

        btnpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity.this, PaymentActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("capasity",capasity);
                intent.putExtra("address",address);
                intent.putExtra("date", date);
                intent.putExtra("time",time);
                startActivity(intent);
            }
        });
    }

    private void initiate() {
        btntime1 = findViewById(R.id.btntime1);
        btntime2 = findViewById(R.id.btntime2);
        btntime3 = findViewById(R.id.btntime3);
        btnpayment = findViewById(R.id.btnpayment);
        calendarView = findViewById(R.id.calendarview);
    }
}