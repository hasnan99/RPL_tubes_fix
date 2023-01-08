package com.example.rpl_tubes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rpl_tubes.adapter.Adapter;
import com.example.rpl_tubes.model.ADDmodel;
import com.example.rpl_tubes.utils.util;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class beranda extends AppCompatActivity {
    DBhelper db;
    ADDmodel model;
    SQLiteDatabase sqLiteDatabase;
    Adapter adapter;
    RecyclerView recyclerView;
    SearchView searchView;
    private BottomAppBar bottomAppBar;
    private BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        navigationView=findViewById(R.id.navigation_pembeli);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_pembeli:
                        Intent intent = new Intent(beranda.this,beranda.class);
                        String nama_home=getIntent().getStringExtra("nama_user");
                        intent.putExtra("nama_user",nama_home);
                        startActivity(intent);
                        return true;

                    case R.id.akun_pembeli:
                        Intent akun = new Intent(beranda.this, akun_pembeli.class);
                        String nama=getIntent().getStringExtra("nama_user");
                        akun.putExtra("nama_user",nama);
                        startActivity(akun);
                        return true;

                    case R.id.order:
                        Intent order=new Intent(beranda.this,order_history.class);
                        String nama_order=getIntent().getStringExtra("nama_user");
                        order.putExtra("nama_user",nama_order);
                        startActivity(order);
                        return true;

                    case R.id.keranjang:
                        Intent cart=new Intent(beranda.this,summary_activity.class);
                        String nama_user=getIntent().getStringExtra("nama_user");
                        cart.putExtra("nama_user",nama_user);
                        startActivity(cart);
                        return true;

                }
                return false;
            }
        });

        db=new DBhelper(this);
        searchView=findViewById(R.id.search);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        findid();
        displaydata();
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));



    }

    private void filterList(String newText) {
        sqLiteDatabase=db.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from sayur",null);
        ArrayList<ADDmodel>adDmodels=new ArrayList<>();
        while (cursor.moveToNext()){
            Integer harga_sayur=cursor.getInt(2);
            byte[]gambar_sayur=cursor.getBlob(5);
            String nama_sayur=cursor.getString(1);
            String deskripsi_sayur=cursor.getString(4);
            Integer stock_sayur=cursor.getInt(3);
            adDmodels.add(new ADDmodel(nama_sayur,harga_sayur,gambar_sayur,deskripsi_sayur,stock_sayur));
        }
        cursor.close();
        ArrayList<ADDmodel>isi=new ArrayList<>();
        for (ADDmodel item:adDmodels){
            if (item.getNama_sayur().toLowerCase().contains(newText.toLowerCase())){
                isi.add(item);
            }
        }
        if (isi.isEmpty()){
            Toast.makeText(this,"sayur tidak ditemukan",Toast.LENGTH_SHORT).show();
        }else{
            adapter.filter(isi);
        }
    }

    private void displaydata() {
        sqLiteDatabase=db.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from sayur",null);
        ArrayList<ADDmodel>adDmodels=new ArrayList<>();
        while (cursor.moveToNext()){
            Integer harga_sayur=cursor.getInt(2);
            byte[]gambar_sayur=cursor.getBlob(5);
            String nama_sayur=cursor.getString(1);
            adDmodels.add(new ADDmodel(nama_sayur,harga_sayur,gambar_sayur));
        }
        cursor.close();
        adapter=new Adapter(this,R.layout.list_item,adDmodels,sqLiteDatabase);
        recyclerView.setAdapter(adapter);
    }

    private void findid() {
        recyclerView=findViewById(R.id.view1);
        util itemdecorator=new util(50);
        recyclerView.addItemDecoration(itemdecorator);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}