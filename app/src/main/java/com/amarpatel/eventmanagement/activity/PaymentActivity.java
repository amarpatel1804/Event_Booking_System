package com.amarpatel.eventmanagement.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.airbnb.lottie.LottieAnimationView;
import com.amarpatel.eventmanagement.Helper;
import com.amarpatel.eventmanagement.R;

public class PaymentActivity extends AppCompatActivity {

    EditText eupi;
    Button btnpay;
    final String upipattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";

    LottieAnimationView animationView;
    Helper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        eupi = findViewById(R.id.etxtupi);
        btnpay = findViewById(R.id.btnpay);
        animationView = findViewById(R.id.lottie);
        db = new Helper(this);

        String upi = eupi.getText().toString();

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String capasity = String.valueOf(intent.getIntExtra("capasity",500));
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");

        /*SharedPreferences preferences = getSharedPreferences("save",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name",name);
        editor.putString("address",address);
        editor.putString("date",date);
        editor.putString("time",time);
        editor.putInt("capasity",capasity);
        editor.putBoolean("flag",true);
        editor.apply();*/

        db.insertdata(name,capasity,address,date,time);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Note");
        alert.setMessage("You have to pay only 30% of total charge and another 70% pay to owner");
        alert.show();

        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (false){
                    eupi.setError("Invalid UPI ID");
                    eupi.requestFocus();
                } else {
                    animationView.setAnimation(R.raw.successful);
                    animationView.playAnimation();
                }
            }
        });
    }
}