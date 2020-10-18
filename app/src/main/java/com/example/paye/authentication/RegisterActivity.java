package com.example.paye.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.paye.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText username;
    private EditText fullNmae;
    private EditText password;
    private EditText confirmPassword;
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
        fullNmae = findViewById(R.id.register_edit_name);
        password = findViewById(R.id.register_edit_password);
        confirmPassword = findViewById(R.id.register_edit_confirmPassword);
        register = findViewById(R.id.register_button_register);
    }

    protected void Click(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, AuthenticateActivity.class);
                intent.putExtra("from", "register");
                startActivity(intent);
                finish();
            }
        });
    }
}