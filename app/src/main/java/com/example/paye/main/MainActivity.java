package com.example.paye.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.paye.R;
import com.example.paye.authentication.LoginActivity;
import com.example.paye.fragment.ChatFragment;
import com.example.paye.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    TextView test;
    Button button;
    FragmentManager fragmentManager;
    Fragment ChatFragment, ProfileFragment, ScanFragment, WalletFragment, SettingFragment;
    ImageButton BNChat, BNProfile, BNScan, BNWallet, BNSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        BindingView();
        LoadFragment(ChatFragment);
        Intent intentf = getIntent();
        test.setText(intentf.getStringExtra("username"));
        Click();

    }

    protected void BindingView(){
        //temp
        test = (TextView) findViewById(R.id.testmain);
        button = (Button) findViewById(R.id.buttonmain);

        //Bottom Navigation
        BNChat = (ImageButton) findViewById(R.id.bottomNavigation_Chat);
        BNProfile = (ImageButton) findViewById(R.id.bottomNavigation_Profile);
        BNScan = (ImageButton) findViewById(R.id.bottomNavigation_Scan);
        BNWallet = (ImageButton) findViewById(R.id.bottomNavigation_Wallet);
        BNSetting = (ImageButton) findViewById(R.id.bottomNavigation_Setting);

        //Fragment
        fragmentManager = getSupportFragmentManager();
        ChatFragment = fragmentManager.findFragmentById(R.id.main_ChatFragment);
        ProfileFragment = fragmentManager.findFragmentById(R.id.main_ProfileFragment);
        ScanFragment = fragmentManager.findFragmentById(R.id.main_ScanFragment);
        WalletFragment = fragmentManager.findFragmentById(R.id.main_WalletFragment);
        SettingFragment = fragmentManager.findFragmentById(R.id.main_SettingFragment);
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

        BNChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BNChat.setImageResource(R.drawable.ic_chat_show);
                BNProfile.setImageResource(R.drawable.ic_profile_hint);
                BNScan.setImageResource(R.drawable.ic_scan_hint);
                BNWallet.setImageResource(R.drawable.ic_wallet_hint);
                BNSetting.setImageResource(R.drawable.ic_setting_hint);
                LoadFragment(ChatFragment);
            }
        });

        BNProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BNChat.setImageResource(R.drawable.ic_chat_hint);
                BNProfile.setImageResource(R.drawable.ic_profile_show);
                BNScan.setImageResource(R.drawable.ic_scan_hint);
                BNWallet.setImageResource(R.drawable.ic_wallet_hint);
                BNSetting.setImageResource(R.drawable.ic_setting_hint);
                LoadFragment(ProfileFragment);
            }
        });

        BNScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BNChat.setImageResource(R.drawable.ic_chat_hint);
                BNProfile.setImageResource(R.drawable.ic_profile_hint);
                BNScan.setImageResource(R.drawable.ic_scan_show);
                BNWallet.setImageResource(R.drawable.ic_wallet_hint);
                BNSetting.setImageResource(R.drawable.ic_setting_hint);
                LoadFragment(ScanFragment);
            }
        });

        BNWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BNChat.setImageResource(R.drawable.ic_chat_hint);
                BNProfile.setImageResource(R.drawable.ic_profile_hint);
                BNScan.setImageResource(R.drawable.ic_scan_hint);
                BNWallet.setImageResource(R.drawable.ic_wallet_show);
                BNSetting.setImageResource(R.drawable.ic_setting_hint);
                LoadFragment(WalletFragment);
            }
        });

        BNSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BNChat.setImageResource(R.drawable.ic_chat_hint);
                BNProfile.setImageResource(R.drawable.ic_profile_hint);
                BNScan.setImageResource(R.drawable.ic_scan_hint);
                BNWallet.setImageResource(R.drawable.ic_wallet_hint);
                BNSetting.setImageResource(R.drawable.ic_setting_show);
                LoadFragment(SettingFragment);
            }
        });
    }

    private void LoadFragment(Fragment fragment) {
        if (fragment == ChatFragment){
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(ChatFragment)
                    .hide(ProfileFragment)
                    .hide(ScanFragment)
                    .hide(WalletFragment)
                    .hide(SettingFragment)
                    .commit();
        } else if (fragment == ProfileFragment){
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(ChatFragment)
                    .show(ProfileFragment)
                    .hide(ScanFragment)
                    .hide(WalletFragment)
                    .hide(SettingFragment)
                    .commit();
        } else if (fragment == ScanFragment){
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(ChatFragment)
                    .hide(ProfileFragment)
                    .show(ScanFragment)
                    .hide(WalletFragment)
                    .hide(SettingFragment)
                    .commit();
        } else if (fragment == WalletFragment){
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(ChatFragment)
                    .hide(ProfileFragment)
                    .hide(ScanFragment)
                    .show(WalletFragment)
                    .hide(SettingFragment)
                    .commit();
        } else if (fragment == SettingFragment){
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(ChatFragment)
                    .hide(ProfileFragment)
                    .hide(ScanFragment)
                    .hide(WalletFragment)
                    .show(SettingFragment)
                    .commit();
        }
    }
}