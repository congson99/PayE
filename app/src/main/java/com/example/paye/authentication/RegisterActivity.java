package com.example.paye.authentication;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.paye.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    //Firebase
    DatabaseReference databaseReference;

    private EditText email;
    private EditText username;
    private EditText fullName;
    private EditText password;
    private EditText confirmPassword;
    private Button check;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Mapping();
        Click();

    }

    protected void Mapping(){
        email = findViewById(R.id.register_edit_email);
        username = findViewById(R.id.register_edit_username);
        fullName = findViewById(R.id.register_edit_name);
        password = findViewById(R.id.register_edit_password);
        confirmPassword = findViewById(R.id.register_edit_confirmPassword);
        check = findViewById(R.id.register_button_check);
        register = findViewById(R.id.register_button_register);
    }

    protected void Click(){
        databaseReference = FirebaseDatabase.getInstance().getReference("DATA").child("user");
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("")){
                    username.setError("Enter your username!");
                } else {
                    databaseReference.child(username.getText().toString()).child("password").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() == null) {
                                username.setTextColor(Color.parseColor("#1B5E20"));
                                Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_tick, null);;
                                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                                username.setError("OK!", drawable);
                            } else {
                                username.setTextColor(Color.parseColor("#B00020"));
                                username.setError("This username already exists");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().equals("")){
                    email.setError("Enter your email!");
                } else {
                    if (username.getText().toString().equals("")){
                        username.setError("Enter your username!");
                    } else {
                        if (fullName.getText().toString().equals("")) {
                            fullName.setError("Enter your name!");
                        } else {
                            if (password.getText().toString().equals("")) {
                                password.setError("Enter your password!");
                                confirmPassword.setText("");
                            } else {
                                if (confirmPassword.getText().toString().equals("")) {
                                    confirmPassword.setError("Retype your password!");
                                    password.setText("");
                                } else {
                                    if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                                        databaseReference.child(username.getText().toString()).child("password").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.getValue() == null) {
                                                    username.setTextColor(Color.parseColor("#1B5E20"));
                                                    Intent intent = new Intent(RegisterActivity.this, AuthenticateActivity.class);
                                                    intent.putExtra("from", "register");
                                                    intent.putExtra("email", email.getText().toString());
                                                    intent.putExtra("username", username.getText().toString());
                                                    intent.putExtra("name", fullName.getText().toString());
                                                    intent.putExtra("password", password.getText().toString());
                                                    password.setText("");
                                                    confirmPassword.setText("");
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    username.setTextColor(Color.parseColor("#B00020"));
                                                    username.setError("This username already exists");
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    } else {
                                        password.setError("passwords do not match!");
                                        confirmPassword.setError("passwords do not match!");
                                        password.setText("");
                                        confirmPassword.setText("");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}