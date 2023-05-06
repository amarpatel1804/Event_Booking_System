package com.amarpatel.eventmanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.amarpatel.eventmanagement.R;

public class MainActivity extends AppCompatActivity {

    ImageButton birthdaybtn, marriagebtn, gatheringbtn, gamefest, bookevent, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        birthdaybtn=findViewById(R.id.birthdaybtn);
        marriagebtn = findViewById(R.id.marriagebtn);
        gatheringbtn=findViewById(R.id.gatheringbtn);
        gamefest=findViewById(R.id.gamefestbtn);
        bookevent = findViewById(R.id.bookedevent);
        profile = findViewById(R.id.profileicon);

        birthdaybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, locationActivity.class);
                startActivity(intent);
            }
        });

        marriagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,locationActivity.class);
                startActivity(intent);
            }
        });

        gatheringbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,locationActivity.class);
                startActivity(intent);
            }
        });

        gamefest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,locationActivity.class);
                startActivity(intent);
            }
        });

        bookevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, BookedEvent.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

    }
}