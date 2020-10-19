package com.example.paye.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class ResetPasswordActivity extends AppCompatActivity {

    //Firebase
    DatabaseReference databaseReference;

    private EditText password;
    private EditText confirmPassword;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Intent intentf = this.getIntent();
        String username = intentf.getStringExtra("username");
        Mapping();
        Click(username);
    }

    protected void Mapping(){
        password = findViewById(R.id.resetPassword_edit_password);
        confirmPassword = findViewById(R.id.resetPassword_edit_confirmPassword);
        confirm = findViewById(R.id.resetPassword_button_confirm);
    }

    protected void Click(String username){
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Firebase
                databaseReference = FirebaseDatabase.getInstance().getReference("DATA").child("user").child(username).child("password");
                if (password.getText().toString().equals("")){
                    password.setError("Enter your new password!");
                    confirmPassword.setText("");
                } else {
                    if (confirmPassword.getText().toString().equals("")){
                        confirmPassword.setError("Retype your new password!");
                        password.setText("");
                    } else {
                        if (!password.getText().toString().equals(confirmPassword.getText().toString())){
                            password.setError("passwords do not match!");
                            confirmPassword.setError("passwords do not match!");
                            password.setText("");
                            confirmPassword.setText("");
                        } else {
                            databaseReference.setValue(password.getText().toString());
                            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                            intent.putExtra("username", username);
                            intent.putExtra("password", password.getText().toString());
                            password.setText("");
                            confirmPassword.setText("");
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }
        });
    }
}