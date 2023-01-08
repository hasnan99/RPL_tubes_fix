package com.example.rpl_tubes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class order_penjual extends AppCompatActivity {
    String[] daftar;
    ListView listView;
    protected Cursor cursor;
    DBhelper db;
    public static order_penjual bp;

    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_penjual);
        navigationView=findViewById(R.id.navigationview_order);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent intent = new Intent(order_penjual.this,beranda_penjual.class);
                        String nama_home=getIntent().getStringExtra("nama");
                        intent.putExtra("nama",nama_home);
                        startActivity(intent);
                        return true;

                    case R.id.order_penjual:
                        Intent order = new Intent(order_penjual.this,order_penjual.class);
                        String nama_order=getIntent().getStringExtra("nama");
                        order.putExtra("nama",nama_order);
                        startActivity(order);
                        return true;

                    case R.id.akun:
                        Intent akun = new Intent(order_penjual.this, akun_penjual.class);
                        String nama=getIntent().getStringExtra("nama");
                        akun.putExtra("nama",nama);
                        startActivity(akun);
                        return true;
                }
                return false;
            }
        });

        bp=this;
        db=new DBhelper(this);
        refreshlist();
    }
    public void refreshlist() {
        SQLiteDatabase db1= db.getReadableDatabase();
        cursor=db1.rawQuery("select * from pembayaran",null);
        daftar=new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            daftar[i]=cursor.getString(1).toString();
        }
        listView=(ListView)findViewById(R.id.list_order);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1,daftar));
        listView.setSelected(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selection=daftar[position];
                final CharSequence[] dialogitem={"Lihat order","Update order"};
                AlertDialog.Builder builder=new AlertDialog.Builder(order_penjual.this);
                builder.setTitle("pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent detail=new Intent(getApplicationContext(),lihat_order.class);
                                detail.putExtra("nama_order",selection);
                                startActivity(detail);
                                break;
                            case 1:
                                Intent update=new Intent(getApplicationContext(),update_order.class);
                                update.putExtra("nama_order",selection);
                                startActivity(update);
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter)listView.getAdapter()).notifyDataSetInvalidated();
    }
}