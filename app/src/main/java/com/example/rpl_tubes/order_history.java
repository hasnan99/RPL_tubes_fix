package com.example.rpl_tubes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rpl_tubes.adapter.Adapter;
import com.example.rpl_tubes.adapter.order_adapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class order_history extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> nama_sayur,jumlah,total,metode_bayar,status;
    DBhelper db;
    order_adapter adapter;
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        db=new DBhelper( this);
        navigationView=findViewById(R.id.navigation_pembeli);
        nama_sayur=new ArrayList<>();
        jumlah=new ArrayList<>();
        total=new ArrayList<>();
        metode_bayar=new ArrayList<>();
        status=new ArrayList<>();
        recyclerView=findViewById(R.id.recycler_view);
        adapter=new order_adapter(this,nama_sayur,jumlah,total,metode_bayar,status);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData();

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_pembeli:
                        Intent intent = new Intent(order_history.this,beranda.class);
                        String nama_home=getIntent().getStringExtra("nama_user");
                        intent.putExtra("nama_user",nama_home);
                        startActivity(intent);
                        return true;

                    case R.id.akun_pembeli:
                        Intent akun = new Intent(order_history.this, akun_pembeli.class);
                        String nama=getIntent().getStringExtra("nama_user");
                        akun.putExtra("nama_user",nama);
                        startActivity(akun);
                        return true;

                    case R.id.order:
                        Intent order=new Intent(order_history.this,order_history.class);
                        String nama_order=getIntent().getStringExtra("nama_user");
                        order.putExtra("nama_user",nama_order);
                        startActivity(order);
                        return true;

                    case R.id.keranjang:
                        Intent cart=new Intent(order_history.this,summary_activity.class);
                        String nama_user=getIntent().getStringExtra("nama_user");
                        cart.putExtra("nama_user",nama_user);
                        startActivity(cart);
                        return true;

                }
                return false;
            }
        });

    }

    private void displayData() {
        Cursor cursor=db.getData();
        if (cursor.getCount()==0){
            Toast.makeText(this, "Tidak Ada Order", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            while (cursor.moveToNext())
            {
                nama_sayur.add(cursor.getString(1));
                jumlah.add(cursor.getString(2));
                total.add(cursor.getString(3));
                metode_bayar.add(cursor.getString(4));
                status.add(cursor.getString(5));


            }
        }
    }
}