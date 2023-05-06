package com.amarpatel.eventmanagement.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amarpatel.eventmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText editemail, editpassword;
    Button btnlogin;
    TextView forgotpass, createacc, incorrect;
    ProgressBar progressBar;
    FirebaseAuth auth;
    final String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    private boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initiate();

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        int capasity = intent.getIntExtra("capasity",500);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editemail.getText().toString();
                String password = editpassword.getText().toString();
                btnlogin.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                if (email.isEmpty() || password.isEmpty()) {
                    btnlogin.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else if (!email.matches(emailpattern)) {
                    btnlogin.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    editemail.setError("Invalid email");
                    editemail.requestFocus();
                } else if (password.length() < 6) {
                    btnlogin.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    editpassword.setError("Minimum 6 character");
                    editpassword.requestFocus();
                } else {
                    auth = FirebaseAuth.getInstance();
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                incorrect.setText("");
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();

                                sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                                editor = sharedPreferences.edit();
                                editor.putString("email",email);
                                editor.putString("pass",password);
                                editor.putBoolean("flag",true);
                                editor.apply();

                                Intent intent = new Intent(LoginActivity.this, CalendarActivity.class);
                                intent.putExtra("name",name);
                                intent.putExtra("capasity",capasity);
                                intent.putExtra("address",address);
                                startActivity(intent);
                                finish();
                            } else {
                                btnlogin.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                                incorrect.setText("Incorrect Email or password");
                            }
                        }
                    });
                }
            }

        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editemail.getText().toString();

                if (email.isEmpty()) {
                    editemail.setError("Email is required!");
                    editemail.requestFocus();
                } else if (!email.matches(emailpattern)) {
                    editemail.setError("Invalid email");
                    editemail.requestFocus();
                } else {
                    auth = FirebaseAuth.getInstance();
                    btnlogin.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);

                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            btnlogin.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            if (task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Check your email to reset your password!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Try again! something went wrong!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        createacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("capasity",capasity);
                intent.putExtra("address",address);
                startActivity(intent);
            }
        });
    }

    private void initiate() {
        editemail = findViewById(R.id.etxtloginemail);
        editpassword = findViewById(R.id.etxtloginpassword);
        btnlogin = findViewById(R.id.btnlogin);
        forgotpass = findViewById(R.id.txtforgotpassword);
        createacc = findViewById(R.id.txtcreate);
        incorrect = findViewById(R.id.txtincorrect);
        progressBar = findViewById(R.id.progressbar);
    }
}