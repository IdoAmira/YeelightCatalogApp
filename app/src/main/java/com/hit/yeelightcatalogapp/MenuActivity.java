package com.hit.yeelightcatalogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        TextView name_tv = findViewById(R.id.name_menu_tv);
        String name = getIntent().getStringExtra("name");
        name_tv.setText(name_tv.getText().toString()+" "+name);

        Button product = findViewById(R.id.products_btn);
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,ProductActivity.class);
                startActivity(intent);
            }
        });

        Button Contact_btn=findViewById(R.id.contact_btn);
        Contact_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuActivity.this,ContactUsActivity.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });

        Button booking=findViewById(R.id.meeting_btn);
        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuActivity.this,ScheduleActivity.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });

        Button complain=findViewById(R.id.complain_btn);
        complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuActivity.this,ComplainActivity.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
    }
}