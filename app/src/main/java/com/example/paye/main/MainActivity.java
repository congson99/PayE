package com.example.paye.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.paye.R;
import com.example.paye.authentication.LoginActivity;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    TextView test;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test = (TextView) findViewById(R.id.testmain);
        button = (Button) findViewById(R.id.buttonmain);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        Intent intentf = getIntent();
        test.setText(intentf.getStringExtra("username"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("username");
                editor.remove("password");
                editor.apply();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}