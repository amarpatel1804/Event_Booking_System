package com.amarpatel.eventmanagement.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amarpatel.eventmanagement.R;
import com.amarpatel.eventmanagement.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {

    EditText editname, editemail, editpass, editconfirmpass, editphone;
    Button button;
    final String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initilize();
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        Intent intent = getIntent();
        String h_name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        int capasity = intent.getIntExtra("capasity",500);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editname.getText().toString();
                String phone = editphone.getText().toString();
                String email = editemail.getText().toString();
                String password = editpass.getText().toString();
                String conpassword = editconfirmpass.getText().toString();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || conpassword.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else if (phone.length() != 10) {
                    editphone.setError("Invalid Mobile no");
                    editphone.requestFocus();
                } else if (!email.matches(emailpattern)) {
                    editemail.setError("Invalid email");
                    editemail.requestFocus();
                } else if (password.length() < 6) {
                    editpass.setError("Minimum 6 character");
                    editpass.requestFocus();
                } else if (!conpassword.equals(password)) {
                    editconfirmpass.setError("Password not match");
                    editconfirmpass.requestFocus();
                } else if (!isemailcheck()){
                    Toast.makeText(RegisterActivity.this, "This email has been registered.", Toast.LENGTH_LONG).show();
                } else {
                    progressDialog.setTitle("Registration");
                    progressDialog.setMessage("Please wait while Registration...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    Log.e("TAG", email);

                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        User user = new User(name, email, password);

                                        FirebaseDatabase.getInstance().getReference("users")
                                                .child("+91" + phone)
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            progressDialog.dismiss();
                                                            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                                                    "+91" + editphone.getText().toString(),
                                                                    60,
                                                                    TimeUnit.SECONDS,
                                                                    RegisterActivity.this,
                                                                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                                                        @Override
                                                                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                                                        }

                                                                        @Override
                                                                        public void onVerificationFailed(@NonNull FirebaseException e) {
                                                                            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                        }

                                                                        @Override
                                                                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                                            super.onCodeSent(s, forceResendingToken);
                                                                            Intent intent = new Intent(RegisterActivity.this, VerifyOTP.class);
                                                                            intent.putExtra("mobile", editphone.getText().toString());
                                                                            intent.putExtra("backendotp", s);
                                                                            intent.putExtra("name",h_name);
                                                                            intent.putExtra("capasity",capasity);
                                                                            intent.putExtra("address",address);
                                                                            startActivity(intent);
                                                                            finish();
                                                                        }
                                                                    }
                                                            );
                                                            SharedPreferences preferences = getSharedPreferences("user",MODE_PRIVATE);
                                                            SharedPreferences.Editor editor = preferences.edit();
                                                            editor.putString("phone","+91" + phone);
                                                            editor.apply();

                                                            Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            progressDialog.dismiss();
                                                            Toast.makeText(RegisterActivity.this, "Failed to Registered in mobile", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Failed to Registered Try again!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private boolean isemailcheck() {
        auth.fetchSignInMethodsForEmail(editemail.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        boolean check = !task.getResult().getSignInMethods().isEmpty();
                        if (check){
                            Toast.makeText(RegisterActivity.this, "This email has been registered.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return true;
    }

    private void initilize() {
        editname = findViewById(R.id.etxtname);
        editphone = findViewById(R.id.etxtphone);
        editemail = findViewById(R.id.etxtemail);
        editpass = findViewById(R.id.etxtpassword);
        editconfirmpass = findViewById(R.id.etxtconfirmpassword);
        button = findViewById(R.id.btnregisternext);
    }
}