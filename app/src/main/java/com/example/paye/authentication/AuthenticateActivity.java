package com.example.paye.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.paye.R;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

public class AuthenticateActivity extends AppCompatActivity {

    private OtpTextView otpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);

        Intent intent = this.getIntent();
        String from = intent.getStringExtra("from");

        otpTextView = findViewById(R.id.authenticate_view_otp);
        otpTextView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                // fired when user types something in the Otpbox
            }
            @Override
            public void onOTPComplete(String otp) {
                // fired when user has entered the OTP fully.
                if (otp.equals("123456"))
                {
                    if (from.equals("register"))
                    {
                        Intent intent = new Intent(AuthenticateActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (from.equals("forgetPassword"))
                    {
                        Intent intent = new Intent(AuthenticateActivity.this, ResetPasswordActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else
                {
                    Toast.makeText(AuthenticateActivity.this, "The OTP is not correct",  Toast.LENGTH_SHORT).show();
                    otpTextView.setOTP("");
                }
            }
        });

    }
}