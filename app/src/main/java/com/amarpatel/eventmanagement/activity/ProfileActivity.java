package com.amarpatel.eventmanagement.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amarpatel.eventmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    TextView txtname,txtemail,txtphone,txtlogout;
    String phone;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initiate();

        SharedPreferences preferences = getSharedPreferences("user",MODE_PRIVATE);
        phone = preferences.getString("phone","+918128628315");
        getdata(phone);

        txtlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
                sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putBoolean("flag",false);
                editor.apply();
                startActivity(intent);
            }
        });
    }

    private void getdata(String phone) {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("users").child(phone);

        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();

                        String name = String.valueOf(dataSnapshot.child("name").getValue());
                        String email = String.valueOf(dataSnapshot.child("email").getValue());

                        txtname.setText(name);
                        txtemail.setText(email);
                        txtphone.setText(phone);
                    } else {
                        Toast.makeText(ProfileActivity.this, "Data not exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProfileActivity.this, "Failed to Read data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initiate() {
        txtname = findViewById(R.id.txtproname);
        txtemail = findViewById(R.id.txtproemail);
        txtphone = findViewById(R.id.txtprophone);
        txtlogout = findViewById(R.id.txtlogout);
    }
}