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

public class akun_pembeli extends AppCompatActivity {
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
        setContentView(R.layout.activity_akun_pembeli);

        bottomAppBar=findViewById(R.id.bottomBar);
        navigationView=findViewById(R.id.navigation_pembeli);
        edit_profile=findViewById(R.id.edit_info_penjual);
        logout=findViewById(R.id.logoutBtnProfile);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_pembeli:
                        Intent intent = new Intent(akun_pembeli.this,beranda.class);
                        String nama_home=getIntent().getStringExtra("nama_user");
                        intent.putExtra("nama_user",nama_home);
                        startActivity(intent);
                        return true;

                    case R.id.akun_pembeli:
                        Intent akun = new Intent(akun_pembeli.this, akun_pembeli.class);
                        String nama=getIntent().getStringExtra("nama_user");
                        akun.putExtra("nama_user",nama);
                        startActivity(akun);
                        return true;

                    case R.id.order:
                        Intent order=new Intent(akun_pembeli.this,order_history.class);
                        String nama_order=getIntent().getStringExtra("nama_user");
                        order.putExtra("nama_user",nama_order);
                        startActivity(order);
                        return true;

                    case R.id.keranjang:
                        Intent cart=new Intent(akun_pembeli.this,summary_activity.class);
                        String nama_user=getIntent().getStringExtra("nama_user");
                        cart.putExtra("nama_user",nama_user);
                        startActivity(cart);
                        return true;

                }
                return false;
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(akun_pembeli.this,Login.class);
                startActivity(intent);
            }
        });
    }
    public void edit_pembeli(View view){
        Intent intent=new Intent(this,info_pembeli.class);
        String nama=getIntent().getStringExtra("nama_user");
        intent.putExtra("nama_user",nama);
        startActivity(intent);
    }

}