package com.hit.yeelightcatalogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ContactUsActivity extends AppCompatActivity {
    String number;
    String namecus;
    final int call_permission_request = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        number = "08-6852114";

        TextView name_tv = findViewById(R.id.name_contact_tv);
        String name = getIntent().getStringExtra("name");
        name_tv.setText(name_tv.getText().toString()+" "+name);

        Button keep = findViewById(R.id.btn_keep);
        keep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, number);
                intent.putExtra(ContactsContract.Intents.Insert.NAME, "Yeelight");
                startActivity(intent);
            }
        });

        Button waze = findViewById(R.id.btn_waze);
        waze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lat = "31.887850";
                String lng = "34.964590";
                try {
                    // Launch Waze to look for Hawaii:
                    String url = "waze://?ll=" + lat + "," + lng + "&navigate=yes";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // If Waze is not installed, open it in Google Play:
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
                    startActivity(intent);
                }
            }
        });

        final Button call = findViewById(R.id.btn_call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    int hascallpermission = checkSelfPermission(Manifest.permission.CALL_PHONE);
                    if (hascallpermission == PackageManager.PERMISSION_GRANTED) {
                        callphone();
                    } else {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, call_permission_request);
                    }
                } else {
                    callphone();
                }
            }
        });
        call.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:"+getPackageName()));
                startActivity(intent);
                return false;
            }
        });
    }

    private void callphone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData( Uri.parse("tel:" + number));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == call_permission_request) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callphone();
            } else {
                Toast.makeText(this,getResources().getString(R.string.contact_toast),Toast.LENGTH_LONG).show();
            }
        }
    }
}