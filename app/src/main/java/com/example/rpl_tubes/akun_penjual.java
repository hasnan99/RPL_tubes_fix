package com.example.rpl_tubes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class akun_penjual extends AppCompatActivity {
    String[] daftar;
    ListView listView;
    Menu menu;
    protected Cursor cursor;
    DBhelper db;
    Button edit_profile,logout;
    private BottomAppBar bottomAppBar;
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun_penjual);

        bottomAppBar=findViewById(R.id.bottomBar);
        navigationView=findViewById(R.id.navigationview);
        edit_profile=findViewById(R.id.edit_info_penjual);
        logout=findViewById(R.id.logoutBtnProfile);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent intent = new Intent(akun_penjual.this,beranda_penjual.class);
                        String nama_home=getIntent().getStringExtra("nama");
                        intent.putExtra("nama",nama_home);
                        startActivity(intent);
                        return true;

                    case R.id.order_penjual:
                        Intent order = new Intent(akun_penjual.this,order_penjual.class);
                        String nama_order=getIntent().getStringExtra("nama");
                        order.putExtra("nama",nama_order);
                        startActivity(order);
                        return true;

                    case R.id.akun:
                        Intent akun = new Intent(akun_penjual.this, akun_penjual.class);
                        String nama=getIntent().getStringExtra("nama");
                        akun.putExtra("nama",nama);
                        startActivity(akun);
                        return true;
                }
                return false;
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(akun_penjual.this,Login.class);
                startActivity(intent);
            }
        });
    }
    public void edit(View view){
        Intent intent=new Intent(this,info_penjual.class);
        String nama=getIntent().getStringExtra("nama");
        intent.putExtra("nama",nama);
        startActivity(intent);
    }

}