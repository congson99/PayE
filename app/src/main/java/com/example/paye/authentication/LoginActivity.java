package com.example.paye.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.paye.R;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private TextView forgetPassword;
    private Button login;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BindingView();
        Click();

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
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}