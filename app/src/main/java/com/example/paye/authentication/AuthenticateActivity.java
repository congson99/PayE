package com.example.paye.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.paye.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

public class AuthenticateActivity extends AppCompatActivity {

    //Firebase
    DatabaseReference databaseReference;

    private OtpTextView otpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);

        Intent intentf = this.getIntent();
        String from = intentf.getStringExtra("from");
        String email = intentf.getStringExtra("email");
        String username = intentf.getStringExtra("username");
        String name = intentf.getStringExtra("name");
        String password = intentf.getStringExtra("password");
        Mapping();
        Listener(from,email, username, name, password);
    }

    protected void Mapping(){
        otpTextView = findViewById(R.id.authenticate_view_otp);
    }

    protected void Listener(String from,String email, String username, String name, String password){
        otpTextView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                // fired when user types something in the Otpbox
            }
            @Override
            public void onOTPComplete(String otp) {
                // fired when user has entered the OTP fully.
                if (otp.equals("123456")) {
                    if (from.equals("register")) {
                        databaseReference = FirebaseDatabase.getInstance().getReference("DATA").child("user").child(username);
                        databaseReference.child("email").setValue(email);
                        databaseReference.child("name").setValue(name);
                        databaseReference.child("password").setValue(password);
                        Intent intent = new Intent(AuthenticateActivity.this, LoginActivity.class);
                        intent.putExtra("username", username);
                        intent.putExtra("password", password);
                        startActivity(intent);
                        finish();
                    }
                    if (from.equals("forgetPassword")) {
                        Intent intent = new Intent(AuthenticateActivity.this, ResetPasswordActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(AuthenticateActivity.this, "The OTP is not correct",  Toast.LENGTH_SHORT).show();
                    otpTextView.setOTP("");
                }
            }
        });
    }
}