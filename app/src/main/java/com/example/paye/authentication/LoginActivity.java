package com.example.paye.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.paye.R;
import com.example.paye.main.MainActivity;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    DatabaseReference databaseReference;

    private EditText username;
    private EditText password;
    private TextView forgetPassword;
    private Button login;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        Intent intentf = this.getIntent();
        BindingView();
        Click();

        if (intentf.getStringExtra("username") != null) username.setText(intentf.getStringExtra("username"));
        if (intentf.getStringExtra("password") != null) password.setText(intentf.getStringExtra("password"));
    }

    protected void BindingView(){
        username = findViewById(R.id.login_edit_username);
        password = findViewById(R.id.login_edit_password);
        forgetPassword = findViewById(R.id.login_text_forgetPassword);
        login = findViewById(R.id.login_button_login);
        register = findViewById(R.id.login_text_register);
    }

    protected void Click(){

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setText("");
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Firebase
                databaseReference = FirebaseDatabase.getInstance().getReference("DATA").child("user").child(username.getText().toString()).child("password");

                if (username.getText().toString().equals("")) {
                    username.setError("Enter your username!");
                    password.setText("");
                } else {
                    if (password.getText().toString().equals("")){
                        password.setError("Enter your password!");
                    } else {
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.getValue() == null) {
                                    username.setError("This account does not exist!");
                                    password.setText("");
                                } else {
                                    if (snapshot.getValue().toString().equals(password.getText().toString())){
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.putExtra("username", username.getText().toString());
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("username", username.getText().toString());
                                        editor.putString("password", password.getText().toString());
                                        editor.apply();
                                        password.setText("");
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        password.setError("Wrong password!");
                                        password.setText("");
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setText("");
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}