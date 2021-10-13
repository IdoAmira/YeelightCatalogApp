package com.hit.yeelightcatalogapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        RecyclerView recyclerView=findViewById(R.id.product_r);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Product>products=new ArrayList<>();
        products.add(new Product(getResources().getString(R.string.product1),R.drawable.ceillinglight));
        products.add(new Product(getResources().getString(R.string.product2),R.drawable.smartbulb));
        products.add(new Product(getResources().getString(R.string.product3),R.drawable.sidelamp));
        products.add(new Product(getResources().getString(R.string.product4),R.drawable.ledstrip));
        products.add(new Product(getResources().getString(R.string.product5),R.drawable.dimmer));
        ProductAdapter productAdapter = new ProductAdapter(products);
        recyclerView.setAdapter(productAdapter);
    }
}