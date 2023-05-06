package com.amarpatel.eventmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amarpatel.eventmanagement.R;

public class RequirementAvtivity extends AppCompatActivity {

    Button hall,party_plot,banquit;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirement_avtivity);

        hall = findViewById(R.id.btnhall);
        party_plot = findViewById(R.id.btnpartyplot);
        banquit = findViewById(R.id.btnbanquit);
        Intent intent = getIntent();
        city = intent.getStringExtra("city");
        Log.d("jay", city);

        hall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequirementAvtivity.this, ListOfHall.class);
                intent.putExtra("city",city);
                intent.putExtra("btn_name",hall.getText().toString());
                startActivity(intent);
            }
        });

        party_plot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequirementAvtivity.this,ListOfHall.class);
                intent.putExtra("city",city);
                intent.putExtra("btn_name",party_plot.getText().toString());
                startActivity(intent);
            }
        });

        banquit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequirementAvtivity.this,ListOfHall.class);
                intent.putExtra("city",city);
                intent.putExtra("btn_name",banquit.getText().toString());
                startActivity(intent);
            }
        });
    }
}