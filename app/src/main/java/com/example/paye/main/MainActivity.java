package com.example.paye.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.paye.R;
import com.example.paye.authentication.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemReselectedListener {

    SharedPreferences sharedPreferences;

    //Temp
    TextView test;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        BindingView();
        Intent intentf = getIntent();
        test.setText(intentf.getStringExtra("username"));
        Click();

    }

    protected void BindingView(){
        //temp
        test = (TextView) findViewById(R.id.testmain);
        button = (Button) findViewById(R.id.buttonmain);

        //Bottom Navigation
        BottomNavigationView navigationView = findViewById(R.id.main_bottomNavigation);
        navigationView.setOnNavigationItemReselectedListener(this);
    }

    protected void Click(){
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

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.nav_chat:
                test.setText("Chat");
                break;
            case R.id.nav_profile:
                test.setText("Profile");
                break;
            case R.id.nav_wallet:
                test.setText("Wallet");
                break;
        }
    }
}