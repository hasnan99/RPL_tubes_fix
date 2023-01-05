package com.example.rpl_tubes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class beranda_penjual extends AppCompatActivity {

    String[] daftar;
    ListView listView;
    Menu menu;
    protected Cursor cursor;
    DBhelper db;
    public static beranda_penjual bp;

    private BottomAppBar bottomAppBar;
    private BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_penjual);
        bottomAppBar=findViewById(R.id.bottomBar);
        navigationView=findViewById(R.id.navigationview);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent intent = new Intent(beranda_penjual.this,beranda_penjual.class);
                        String nama_home=getIntent().getStringExtra("nama_user");
                        intent.putExtra("nama_user",nama_home);
                        startActivity(intent);
                        return true;

                    case R.id.akun:
                        Intent akun = new Intent(beranda_penjual.this, akun_penjual.class);
                        String nama=getIntent().getStringExtra("nama");
                        akun.putExtra("nama",nama);
                        startActivity(akun);
                        return true;
                }
                return false;
            }
        });

        FloatingActionButton fab=findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(beranda_penjual.this,tambah_sayur.class);
                startActivity(intent);
            }
        });

        bp=this;
        db=new DBhelper(this);
        refreshlist();
    }

    public void refreshlist() {
        SQLiteDatabase db1= db.getReadableDatabase();
        cursor=db1.rawQuery("select * from sayur",null);
        daftar=new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            daftar[i]=cursor.getString(1).toString();
        }
        listView=(ListView)findViewById(R.id.list1);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1,daftar));
        listView.setSelected(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selection=daftar[position];
                final CharSequence[] dialogitem={"Lihat sayur","Update sayur","hapus sayur"};
                AlertDialog.Builder builder=new AlertDialog.Builder(beranda_penjual.this);
                builder.setTitle("pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent detail=new Intent(getApplicationContext(),detail_sayur.class);
                                detail.putExtra("nama_sayur",selection);
                                startActivity(detail);
                                break;
                            case 1:
                                Intent update=new Intent(getApplicationContext(),update_sayur.class);
                                update.putExtra("nama_sayur",selection);
                                startActivity(update);
                                break;
                            case 2:
                                SQLiteDatabase del=db.getReadableDatabase();
                                del.execSQL("delete from sayur where nama_sayur ='"+selection+"'");
                                refreshlist();
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