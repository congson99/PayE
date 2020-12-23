package com.example.paye.main;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.paye.R;
import com.example.paye.authentication.LoginActivity;
import com.example.paye.fragment.SearchFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //temp
    public static TextView scanResult;
    Button go;

    //SharedPreferences
    SharedPreferences sharedPreferences;

    //Firebase
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceSearch;
    DatabaseReference databaseReferenceUser;

    //Camera Intent
    int REQUEST_CODE1 = 1, REQUEST_CODE2 = 2, REQUEST_CODE3 = 3, REQUEST_CODE4 = 4;
    Integer PHOTO1 = 21, PHOTO2 = 22, PHOTO3 = 23, PHOTO4 = 24;

    //Items
    TextView profileName,
            ProfileName2, profileDescription2,
            profileDescriptionPhoto21, profileDescriptionPhoto22,
            profileDescriptionPhoto23, profileDescriptionPhoto24;
    EditText profileDescription,
            profileDescriptionPhoto1, profileDescriptionPhoto2,
            profileDescriptionPhoto3, profileDescriptionPhoto4,
            searchSearch;
    Button profileUser, profileUser2, profileQR, profile2QR, button,
            profileButtonQR, profileButtonConfirm,
            profileButtonCamera1, profileButtonCamera2, profileButtonCamera3, profileButtonCamera4,
            profileButtonPhoto1, profileButtonPhoto2, profileButtonPhoto3, profileButtonPhoto4,
            profileButtonConfirm1, profileButtonConfirm2, profileButtonConfirm3, profileButtonConfirm4;
    ImageButton BNChat, BNProfile, BNScan, BNWallet, BNSetting,
            profileSearch, searchButtonSearch;
    ImageView profileAvatar, profilePhoto1, profilePhoto2, profilePhoto3, profilePhoto4,
            profileAvatar2, profilePhoto21, profilePhoto22, profilePhoto23, profilePhoto24;
    FragmentManager fragmentManager;
    Fragment ChatFragment, ProfileFragment, SearchFragment,SearchProfileFragment , ScanFragment, WalletFragment, SettingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        BindingView();
        LoadFragment(ChatFragment);
        Intent intentf = getIntent();
        LoadData(intentf);
        Click(intentf);
    }

    protected void BindingView(){
        //temp
        scanResult = (TextView) findViewById(R.id.scan_result);
        go = (Button) findViewById(R.id.scan_find);

        //profile
        profileUser = (Button) findViewById(R.id.profile_username);
        profileQR = (Button) findViewById(R.id.profile_showQR);
        profileName = (TextView) findViewById(R.id.profile_name);
        profileAvatar = (ImageView) findViewById(R.id.profile_avatar);
        profileDescription = (EditText) findViewById(R.id.profile_description);
        profileButtonConfirm = (Button) findViewById(R.id.profile_changeDescription);
        profileButtonQR = (Button) findViewById(R.id.profile_showQR);
        profileSearch = (ImageButton) findViewById(R.id.profile_button_search);
        //photo
        profilePhoto1 = (ImageView) findViewById(R.id.profile_photo1);
        profilePhoto2 = (ImageView) findViewById(R.id.profile_photo2);
        profilePhoto3 = (ImageView) findViewById(R.id.profile_photo3);
        profilePhoto4 = (ImageView) findViewById(R.id.profile_photo4);
        profileButtonCamera1 = (Button) findViewById(R.id.profile_button_camera1);
        profileButtonCamera2 = (Button) findViewById(R.id.profile_button_camera2);
        profileButtonCamera3 = (Button) findViewById(R.id.profile_button_camera3);
        profileButtonCamera4 = (Button) findViewById(R.id.profile_button_camera4);
        profileButtonPhoto1 = (Button) findViewById(R.id.profile_button_photo1);
        profileButtonPhoto2 = (Button) findViewById(R.id.profile_button_photo2);
        profileButtonPhoto3 = (Button) findViewById(R.id.profile_button_photo3);
        profileButtonPhoto4 = (Button) findViewById(R.id.profile_button_photo4);
        profileDescriptionPhoto1 = (EditText) findViewById(R.id.profile_photoDescription1);
        profileDescriptionPhoto2 = (EditText) findViewById(R.id.profile_photoDescription2);
        profileDescriptionPhoto3 = (EditText) findViewById(R.id.profile_photoDescription3);
        profileDescriptionPhoto4 = (EditText) findViewById(R.id.profile_photoDescription4);
        profileButtonConfirm1 = (Button) findViewById(R.id.profile_changePhoto1);
        profileButtonConfirm2 = (Button) findViewById(R.id.profile_changePhoto2);
        profileButtonConfirm3 = (Button) findViewById(R.id.profile_changePhoto3);
        profileButtonConfirm4 = (Button) findViewById(R.id.profile_changePhoto4);
        //Search
        searchSearch = (EditText) findViewById(R.id.search_search_searchBar);
        searchButtonSearch = (ImageButton) findViewById(R.id.search_button_search);
        //Profile 2
        profileUser2 = (Button) findViewById(R.id.profile2_username);
        ProfileName2 = (TextView) findViewById(R.id.profile2_name);
        profileAvatar2 = (ImageView) findViewById(R.id.profile2_avatar);
        profileDescription2 = (TextView) findViewById(R.id.profile2_description);
        profilePhoto21 = (ImageView) findViewById(R.id.profile2_photo1);
        profilePhoto22 = (ImageView) findViewById(R.id.profile2_photo2);
        profilePhoto23 = (ImageView) findViewById(R.id.profile2_photo3);
        profilePhoto24 = (ImageView) findViewById(R.id.profile2_photo4);
        profileDescriptionPhoto21 = (TextView) findViewById(R.id.profile2_photoDescription1);
        profileDescriptionPhoto22 = (TextView) findViewById(R.id.profile2_photoDescription2);
        profileDescriptionPhoto23 = (TextView) findViewById(R.id.profile2_photoDescription3);
        profileDescriptionPhoto24 = (TextView) findViewById(R.id.profile2_photoDescription4);
        profile2QR = (Button) findViewById(R.id.profile2_showQR);

        //temp
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
        SearchFragment = fragmentManager.findFragmentById(R.id.main_SearchFragment);
        SearchProfileFragment = fragmentManager.findFragmentById(R.id.main_SearchProfileFragment);
        WalletFragment = fragmentManager.findFragmentById(R.id.main_WalletFragment);
        SettingFragment = fragmentManager.findFragmentById(R.id.main_SettingFragment);
    }

    private void LoadFragment(Fragment fragment) {
        if (fragment == ChatFragment){
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(ChatFragment)
                    .hide(ProfileFragment)
                    .hide(SearchFragment)
                    .hide(SearchProfileFragment)
                    .hide(ScanFragment)
                    .hide(WalletFragment)
                    .hide(SettingFragment)
                    .commit();
        } else if (fragment == ProfileFragment){
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(ChatFragment)
                    .show(ProfileFragment)
                    .hide(SearchFragment)
                    .hide(SearchProfileFragment)
                    .hide(ScanFragment)
                    .hide(WalletFragment)
                    .hide(SettingFragment)
                    .commit();
        } else if (fragment == SearchFragment){
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(ChatFragment)
                    .hide(ProfileFragment)
                    .show(SearchFragment)
                    .hide(SearchProfileFragment)
                    .hide(ScanFragment)
                    .hide(WalletFragment)
                    .hide(SettingFragment)
                    .commit();
        } else if (fragment == SearchProfileFragment){
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(ChatFragment)
                    .hide(ProfileFragment)
                    .hide(SearchFragment)
                    .show(SearchProfileFragment)
                    .hide(ScanFragment)
                    .hide(WalletFragment)
                    .hide(SettingFragment)
                    .commit();
        } else if (fragment == ScanFragment){
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(ChatFragment)
                    .hide(ProfileFragment)
                    .hide(SearchFragment)
                    .hide(SearchProfileFragment)
                    .show(ScanFragment)
                    .hide(WalletFragment)
                    .hide(SettingFragment)
                    .commit();
        } else if (fragment == WalletFragment){
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(ChatFragment)
                    .hide(ProfileFragment)
                    .hide(SearchFragment)
                    .hide(SearchProfileFragment)
                    .hide(ScanFragment)
                    .show(WalletFragment)
                    .hide(SettingFragment)
                    .commit();
        } else if (fragment == SettingFragment){
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(ChatFragment)
                    .hide(SearchFragment)
                    .hide(ProfileFragment)
                    .hide(SearchProfileFragment)
                    .hide(ScanFragment)
                    .hide(WalletFragment)
                    .show(SettingFragment)
                    .commit();
        }
    }

    private void LoadData(Intent intentf){
        //username
        profileUser.setText(intentf.getStringExtra("username"));

        databaseReference = FirebaseDatabase.getInstance().getReference("DATA").child("user").child(intentf.getStringExtra("username"));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Name
                profileName.setText(snapshot.child("name").getValue().toString());
                //Avatar
                if(!snapshot.child("avatar").getValue().toString().equals("none")){
                    byte[] tempGet = Base64.decode(snapshot.child("avatar").getValue().toString(), Base64.DEFAULT);
                    Bitmap tempBitmap = BitmapFactory.decodeByteArray(tempGet, 0, tempGet.length);
                    Bitmap circularBitmap = getRoundedCornerBitmap(tempBitmap, 500);
                    profileAvatar.setImageBitmap(circularBitmap);
                }
                //description
                if(snapshot.child("description").getValue().toString().equals("none")){
                    profileDescription.setText("You have no description");
                } else {
                    profileDescription.setText(snapshot.child("description").getValue().toString());
                }
                //photo
                if(!snapshot.child("photo1").getValue().toString().equals("none")){
                    byte[] tempGet = Base64.decode(snapshot.child("photo1").getValue().toString(), Base64.DEFAULT);
                    Bitmap tempBitmap = BitmapFactory.decodeByteArray(tempGet, 0, tempGet.length);
                    profilePhoto1.setImageBitmap(tempBitmap);
                }
                if(!snapshot.child("photo2").getValue().toString().equals("none")){
                    byte[] tempGet = Base64.decode(snapshot.child("photo2").getValue().toString(), Base64.DEFAULT);
                    Bitmap tempBitmap = BitmapFactory.decodeByteArray(tempGet, 0, tempGet.length);
                    profilePhoto2.setImageBitmap(tempBitmap);
                }
                if(!snapshot.child("photo3").getValue().toString().equals("none")){
                    byte[] tempGet = Base64.decode(snapshot.child("photo3").getValue().toString(), Base64.DEFAULT);
                    Bitmap tempBitmap = BitmapFactory.decodeByteArray(tempGet, 0, tempGet.length);
                    profilePhoto3.setImageBitmap(tempBitmap);
                }
                if(!snapshot.child("photo4").getValue().toString().equals("none")){
                    byte[] tempGet = Base64.decode(snapshot.child("photo4").getValue().toString(), Base64.DEFAULT);
                    Bitmap tempBitmap = BitmapFactory.decodeByteArray(tempGet, 0, tempGet.length);
                    profilePhoto4.setImageBitmap(tempBitmap);
                }
                //Description
                profileDescriptionPhoto1.setText(snapshot.child("description1").getValue().toString());
                profileDescriptionPhoto2.setText(snapshot.child("description2").getValue().toString());
                profileDescriptionPhoto3.setText(snapshot.child("description3").getValue().toString());
                profileDescriptionPhoto4.setText(snapshot.child("description4").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    protected void Click(Intent intentf){
        //temp
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!scanResult.getText().toString().equals("")){
                    databaseReferenceSearch = FirebaseDatabase.getInstance().getReference("DATA").child("user").child(searchSearch.getText().toString());
                    databaseReferenceSearch.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() != null){
                                LoadFragment(SearchProfileFragment);
                                LoadProfile(scanResult.getText().toString());
                            } else {
                                scanResult.setError("This username does not exist!");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        //temp logout
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

        //bottom navigation
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

                startActivity(new Intent(getApplicationContext(), ScanActivity.class));
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

        //Profile
        profileButtonQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QRCodeWriter qrCodeWriter = new QRCodeWriter();

                try {
                    BitMatrix bitMatrix = qrCodeWriter.encode(intentf.getStringExtra("username"), BarcodeFormat.QR_CODE, 200, 200);
                    Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.RGB_565);

                    for (int x = 0; x < 200; x++){
                        for (int y = 0; y < 200; y++){
                            bitmap.setPixel(x,y,bitMatrix.get(x, y)? Color.BLACK : Color.WHITE);
                        }
                    }
                    DialogQRcode(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        });
        profileSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadFragment(SearchFragment);
            }
        });
        profileButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("description").setValue(profileDescription.getText().toString());
                Toast.makeText(MainActivity.this, "Change successful!", Toast.LENGTH_LONG).show();
            }
        });
        //Camera
        profileButtonCamera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE1);
            }
        });
        profileButtonCamera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE2);
            }
        });
        profileButtonCamera3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE3);
            }
        });
        profileButtonCamera4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE4);
            }
        });
        //Photo
        profileButtonPhoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "photo"), PHOTO1);
            }
        });
        profileButtonPhoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "photo"), PHOTO2);
            }
        });
        profileButtonPhoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "photo"), PHOTO3);
            }
        });
        profileButtonPhoto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "photo"), PHOTO4);
            }
        });
        //confirm
        profileButtonConfirm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("description1").setValue(profileDescriptionPhoto1.getText().toString());
                byte[] tempPhoto = ImageViewToByte(profilePhoto1);
                String tempString = Base64.encodeToString(tempPhoto, Base64.DEFAULT);
                databaseReference.child("photo1").setValue(tempString);
                Toast.makeText(MainActivity.this, "Change successful!", Toast.LENGTH_LONG).show();
            }
        });
        profileButtonConfirm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("description2").setValue(profileDescriptionPhoto2.getText().toString());
                byte[] tempPhoto = ImageViewToByte(profilePhoto2);
                String tempString = Base64.encodeToString(tempPhoto, Base64.DEFAULT);
                databaseReference.child("photo2").setValue(tempString);
                Toast.makeText(MainActivity.this, "Change successful!", Toast.LENGTH_LONG).show();
            }
        });
        profileButtonConfirm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("description3").setValue(profileDescriptionPhoto3.getText().toString());
                byte[] tempPhoto = ImageViewToByte(profilePhoto3);
                String tempString = Base64.encodeToString(tempPhoto, Base64.DEFAULT);
                databaseReference.child("photo3").setValue(tempString);
                Toast.makeText(MainActivity.this, "Change successful!", Toast.LENGTH_LONG).show();
            }
        });
        profileButtonConfirm4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("description4").setValue(profileDescriptionPhoto4.getText().toString());
                byte[] tempPhoto = ImageViewToByte(profilePhoto4);
                String tempString = Base64.encodeToString(tempPhoto, Base64.DEFAULT);
                databaseReference.child("photo4").setValue(tempString);
                Toast.makeText(MainActivity.this, "Change successful!", Toast.LENGTH_LONG).show();
            }
        });

        //Search
        searchButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!searchSearch.getText().toString().equals("")){
                    databaseReferenceSearch = FirebaseDatabase.getInstance().getReference("DATA").child("user").child(searchSearch.getText().toString());
                    databaseReferenceSearch.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() != null){
                                LoadFragment(SearchProfileFragment);
                                LoadProfile(searchSearch.getText().toString());
                            } else {
                                searchSearch.setError("This username does not exist!");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        profile2QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QRCodeWriter qrCodeWriter = new QRCodeWriter();

                try {
                    BitMatrix bitMatrix = qrCodeWriter.encode(profileUser2.getText().toString(), BarcodeFormat.QR_CODE, 200, 200);
                    Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.RGB_565);

                    for (int x = 0; x < 200; x++){
                        for (int y = 0; y < 200; y++){
                            bitmap.setPixel(x,y,bitMatrix.get(x, y)? Color.BLACK : Color.WHITE);
                        }
                    }
                    DialogQRcode(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //Circle Bitmap
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    //CameraIntent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE1 && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            profilePhoto1.setImageBitmap(bitmap);
        }else if(requestCode == REQUEST_CODE2 && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            profilePhoto2.setImageBitmap(bitmap);
        }else if(requestCode == REQUEST_CODE3 && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            profilePhoto3.setImageBitmap(bitmap);
        }else if(requestCode == REQUEST_CODE4 && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            profilePhoto4.setImageBitmap(bitmap);
        }else if(requestCode == PHOTO1 && data != null){
            Uri selecImageURI = data.getData();
            profilePhoto1.setImageURI(selecImageURI);
        }else if(requestCode == PHOTO2 && data != null){
            Uri selecImageURI = data.getData();
            profilePhoto2.setImageURI(selecImageURI);
        }else if(requestCode == PHOTO3 && data != null){
            Uri selecImageURI = data.getData();
            profilePhoto3.setImageURI(selecImageURI);
        }else if(requestCode == PHOTO4 && data != null){
            Uri selecImageURI = data.getData();
            profilePhoto4.setImageURI(selecImageURI);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //QR code
    public void DialogQRcode(Bitmap bitmap){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.template_qrcode);
        ImageView QR = (ImageView) dialog.findViewById(R.id.qrcode_qr);
        QR.setImageBitmap(bitmap);
        dialog.show();
    }

    //LoadProfile
    private void LoadProfile(String usernameString){
        //username
        profileUser2.setText(usernameString);

        databaseReferenceUser = FirebaseDatabase.getInstance().getReference("DATA").child("user").child(usernameString);
        databaseReferenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Name
                ProfileName2.setText(snapshot.child("name").getValue().toString());
                //Avatar
                if(!snapshot.child("avatar").getValue().toString().equals("none")){
                    byte[] tempGet = Base64.decode(snapshot.child("avatar").getValue().toString(), Base64.DEFAULT);
                    Bitmap tempBitmap = BitmapFactory.decodeByteArray(tempGet, 0, tempGet.length);
                    Bitmap circularBitmap = getRoundedCornerBitmap(tempBitmap, 500);
                    profileAvatar2.setImageBitmap(circularBitmap);
                }
                //description
                if(snapshot.child("description").getValue().toString().equals("none")){
                } else {
                    profileDescription2.setText(snapshot.child("description").getValue().toString());
                }
                //photo
                if(!snapshot.child("photo1").getValue().toString().equals("none")){
                    byte[] tempGet = Base64.decode(snapshot.child("photo1").getValue().toString(), Base64.DEFAULT);
                    Bitmap tempBitmap = BitmapFactory.decodeByteArray(tempGet, 0, tempGet.length);
                    profilePhoto21.setImageBitmap(tempBitmap);
                }
                if(!snapshot.child("photo2").getValue().toString().equals("none")){
                    byte[] tempGet = Base64.decode(snapshot.child("photo2").getValue().toString(), Base64.DEFAULT);
                    Bitmap tempBitmap = BitmapFactory.decodeByteArray(tempGet, 0, tempGet.length);
                    profilePhoto22.setImageBitmap(tempBitmap);
                }
                if(!snapshot.child("photo3").getValue().toString().equals("none")){
                    byte[] tempGet = Base64.decode(snapshot.child("photo3").getValue().toString(), Base64.DEFAULT);
                    Bitmap tempBitmap = BitmapFactory.decodeByteArray(tempGet, 0, tempGet.length);
                    profilePhoto23.setImageBitmap(tempBitmap);
                }
                if(!snapshot.child("photo4").getValue().toString().equals("none")){
                    byte[] tempGet = Base64.decode(snapshot.child("photo4").getValue().toString(), Base64.DEFAULT);
                    Bitmap tempBitmap = BitmapFactory.decodeByteArray(tempGet, 0, tempGet.length);
                    profilePhoto24.setImageBitmap(tempBitmap);
                }
                //Description
                profileDescriptionPhoto21.setText(snapshot.child("description1").getValue().toString());
                profileDescriptionPhoto22.setText(snapshot.child("description2").getValue().toString());
                profileDescriptionPhoto23.setText(snapshot.child("description3").getValue().toString());
                profileDescriptionPhoto24.setText(snapshot.child("description4").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public byte[] ImageViewToByte(ImageView view){
        BitmapDrawable drawable = (BitmapDrawable) view.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}