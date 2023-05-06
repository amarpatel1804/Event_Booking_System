package com.amarpatel.eventmanagement.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amarpatel.eventmanagement.R;
import com.amarpatel.eventmanagement.adapter.Slider_Adapter;
import com.amarpatel.eventmanagement.Slider_Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Hall extends AppCompatActivity {

    TextView txtname,txtcapacity,txtdescription,txtcontact,txtaddress;
    Button btnbook;
    ViewPager2 viewPager2;
    ArrayList<Slider_Item> list;
    ArrayList<String> arr = new ArrayList<>();
    Slider_Adapter adapter;
    LinearLayout linearLayout;
    private int custom_position = 0;
    private Handler slideHandler = new Handler();

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    SharedPreferences sharedPreferences;
    public boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall);

        initiate();
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String city = intent.getStringExtra("city");
        String text = intent.getStringExtra("btn_name");
        String address = intent.getStringExtra("address");
        int capasity = intent.getIntExtra("capasity",500);
        txtname.setText(name);
        txtcapacity.setText(city);
        txtaddress.setText(text);

        getdata(city,text,name);
        getimage(city,text,name);

        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        String getemail = sharedPreferences.getString("email","xyz");
        String getpass = sharedPreferences.getString("pass","jay");
        flag = sharedPreferences.getBoolean("flag",false);

        list = new ArrayList<>();

        list.add(new Slider_Item(R.drawable.image_1));
        list.add(new Slider_Item(R.drawable.image_2));
        list.add(new Slider_Item(R.drawable.image_3));

        adapter = new Slider_Adapter(list,viewPager2);
        viewPager2.setAdapter(adapter);
        Dots(custom_position++);

        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(80));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float  r = 1 - Math.abs(position);
                page.setScaleY(0.85f + (r * 0.14f));
            }
        });
        viewPager2.setPageTransformer(transformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(sliderRunnable);
                slideHandler.postDelayed(sliderRunnable,3000);
                if (custom_position > 2){
                    custom_position = 0;
                }
                Dots(custom_position++);
            }
        });

        btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flag){
                    Intent intent1 = new Intent(Hall.this, CalendarActivity.class);
                    intent1.putExtra("name",name);
                    intent1.putExtra("capasity",capasity);
                    intent1.putExtra("address",address);
                    startActivity(intent1);
                } else {
                    Intent intent1 = new Intent(Hall.this, LoginActivity.class);
                    intent1.putExtra("name",name);
                    intent1.putExtra("capasity",capasity);
                    intent1.putExtra("address",address);
                    startActivity(intent1);
                }
            }
        });
    }

    private void initiate() {
        viewPager2  = findViewById(R.id.viewpager);
        linearLayout = (LinearLayout) findViewById(R.id.SliderDots);
        txtname = findViewById(R.id.txtname);
        txtdescription = findViewById(R.id.txtdescription);
        txtcontact = findViewById(R.id.txtcontact);
        txtcapacity = findViewById(R.id.txtcapacity);
        txtaddress = findViewById(R.id.txtaddress);
        btnbook = findViewById(R.id.btnbook);
    }

    private void getimage(String city, String text, String name) {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("city").child(String.format("%s",city)).child(String.format("%s",text)).child(name).child("image_path");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.d("jay", "in listner");
//                Log.d("jay", String.valueOf(snapshot.getChildrenCount()));
                for (DataSnapshot ds : snapshot.getChildren()) {
                    arr.add(ds.getKey());
                }
                for (int i=0; i< snapshot.getChildrenCount(); i++){
                    setimage(city,text,name,i);
                    Log.i("jay",arr.get(i));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Hall.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setimage(String city, String text, String name, int i) {
        storageReference = FirebaseStorage.getInstance().getReference()
                .child("images/").child(city + "/").child(text + "/").child(name + "/").child(arr.get(i) + ".png");
        try {
            File localFile = File.createTempFile("tempfile",".png");
            storageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            Drawable drawable = new BitmapDrawable(getResources(),bitmap);
//                            list.add(new Slider_Item(drawable));
//                            img.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getdata(String city, String text, String name) {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("city").child(city).child(text).child(name);

        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        int capacity = Integer.parseInt(String.valueOf(dataSnapshot.child("capacity").getValue()));
                        String address = "Address: " + String.valueOf(dataSnapshot.child("address").getValue());
                        String description = "About: " + String.valueOf(dataSnapshot.child("description").getValue());
                        String contact = "Contact: " + String.valueOf(dataSnapshot.child("contact").getValue());

                        txtcapacity.setText("Capacity: Upto " + String.valueOf(capacity));
                        txtaddress.setText(address);
                        txtdescription.setText(description);
                        txtcontact.setText(contact);
                    }  else {
                        Toast.makeText(Hall.this,"User Dosen't Exixts",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Hall.this,"Failed to Read",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

//    private void setvalue() {
//        database = FirebaseDatabase.getInstance();
//        databaseReference = database.getReference("city").child("Ahemdabad").child("Hall");
//
//        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (task.isSuccessful()){
//                    if (task.getResult().exists()){
//                        DataSnapshot dataSnapshot = task.getResult();
//                        String name = String.valueOf(dataSnapshot.child("capacity").getValue());
//                        Log.d("jay", name);
//                    }  else {
//                        Toast.makeText(Hall.this,"User Dosen't Exixts",Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(Hall.this,"Failed to Read",Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        slideHandler.postDelayed(sliderRunnable,3000);
    }

    private void Dots(int CurrentSlidePosition) {
        if (linearLayout.getChildCount()>0){
            linearLayout.removeAllViews();
        }

        ImageView[] dots = new ImageView[3];

        for (int i=0;i<dots.length;i++){
            dots[i] = new ImageView(this);

            if (i == CurrentSlidePosition){
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dot));
            } else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.non_active_dot));
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(4,0,4,0);
            linearLayout.addView(dots[i],layoutParams);
        }
    }
}