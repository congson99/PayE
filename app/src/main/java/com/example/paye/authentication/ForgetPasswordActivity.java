package com.example.paye.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.paye.R;
import com.example.paye.main.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgetPasswordActivity extends AppCompatActivity {

    //database
    DatabaseReference databaseReference;

    private EditText username;
    private EditText email;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        Mapping();
        Click();

    }

    protected void Mapping(){
        username = findViewById(R.id.forgetPassword_edit_username);
        email = findViewById(R.id.forgetPassword_edit_email);
        confirm = findViewById(R.id.forgetPassword_button_confirm);
    }

    protected void Click(){
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Firebase
                databaseReference = FirebaseDatabase.getInstance().getReference("DATA").child("user").child(username.getText().toString()).child("email");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (username.getText().toString().equals("")){
                            username.setError("Enter your username");
                        } else {
                            if (email.getText().toString().equals("")){
                                email.setError("Enter your email");
                            } else {
                                if(snapshot.getValue() == null) {
                                    username.setError("This account does not exist!");
                                } else {
                                    if (snapshot.getValue().toString().equals(email.getText().toString())){
                                        Intent intent = new Intent(ForgetPasswordActivity.this, AuthenticateActivity.class);
                                        intent.putExtra("from", "forgetPassword");
                                        intent.putExtra("username", username.getText().toString());
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        email.setError("Wrong email!");
                                        email.setText("");
                                    }
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}