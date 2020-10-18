package com.example.paye.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.paye.R;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText password;
    private EditText confirmPassword;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Mapping();
        Click();
    }

    protected void Mapping(){
        password = findViewById(R.id.resetPassword_edit_password);
        confirmPassword = findViewById(R.id.resetPassword_edit_confirmPassword);
        confirm = findViewById(R.id.resetPassword_button_confirm);
    }

    protected void Click(){
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}