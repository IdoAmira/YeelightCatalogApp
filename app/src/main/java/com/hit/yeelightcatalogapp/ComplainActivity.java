package com.hit.yeelightcatalogapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class ComplainActivity extends AppCompatActivity {
    final int PICK_PICTURE_REQUEST=1;
    final int CAMERA_REQUEST = 2;
    ImageView image_iv;
    Button photoFromGallery;
    Button takePicture;
    Button send;
    Uri uri;
    final int write_permission_request = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);

        TextView name_tv = findViewById(R.id.name_complain_tv);
        String name = getIntent().getStringExtra("name");
        name_tv.setText(name_tv.getText().toString()+" "+name);

        image_iv =findViewById(R.id.image_iv);
        final EditText Sub=findViewById(R.id.subject_et);
        final EditText text=findViewById(R.id.text_et);
        photoFromGallery =findViewById(R.id.Addphoto_btn);
        photoFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent,PICK_PICTURE_REQUEST);
            }
        });
        takePicture = findViewById(R.id.takepic_btn);
        takePicture.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT < 30) {
                    int haswritepermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (haswritepermission == PackageManager.PERMISSION_GRANTED) {
                       takePicture();
                    } else {

                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, write_permission_request);
                    }
                } else {
                    takePicture();
                }

            }
        });
        takePicture.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:"+getPackageName()));
                startActivity(intent);
                return false;
            }
        });
        send=findViewById(R.id.send_btn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subst=Sub.getText().toString();
                String textst=text.getText().toString();
                String address="idoamira33@gmail.com";
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,textst);
                intent.putExtra(Intent.EXTRA_SUBJECT,subst);
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{address});
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setType("text/html");
                startActivity(intent.createChooser(intent,getResources().getString(R.string.send_email)));
            }
        });
    }
    private void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAMERA_REQUEST);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == write_permission_request) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePicture();
            } else {
                Toast.makeText(this,getResources().getString(R.string.complain_toast),Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_PICTURE_REQUEST&&resultCode==RESULT_OK){
            image_iv.setVisibility(View.VISIBLE);
            uri=data.getData();
            image_iv.setImageURI(uri);
        }
        else if(requestCode==CAMERA_REQUEST&&resultCode==RESULT_OK){
            image_iv.setVisibility(View.VISIBLE);
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,"title", null);
            uri = Uri.parse(bitmapPath);
            image_iv.setImageBitmap(bitmap);
        }
    }
}