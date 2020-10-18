package com.example.paye.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.paye.R;

public class ForgetPasswordActivity extends AppCompatActivity {

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
        email = findViewById(R.id.forgetPassword_edit_email);
        confirm = findViewById(R.id.forgetPassword_button_confirm);
    }

    protected void Click(){
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgetPasswordActivity.this, AuthenticateActivity.class);
                intent.putExtra("from", "forgetPassword");
                startActivity(intent);
                finish();
            }
        });
    }
}