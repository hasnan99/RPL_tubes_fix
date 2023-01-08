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

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homeAdmin_sayur extends AppCompatActivity {
    DBhelper db;
    private BottomNavigationView navigationView;
    ListView listView;
    String[] daftar;
    protected Cursor cursor;
    public static homeAdmin_sayur bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin_sayur);

        db=new DBhelper(this);

        navigationView=findViewById(R.id.navigation_AdminSayur);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent intent = new Intent(homeAdmin_sayur.this,firstHome_admin.class);
                        startActivity(intent);
                        return true;
                    case R.id.admin_penjuals:
                        Intent intentUsers = new Intent(homeAdmin_sayur.this,homeAdmin_user.class);
                        startActivity(intentUsers);
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
                final CharSequence[] dialogitem={"Lihat sayur","hapus sayur"};
                AlertDialog.Builder builder=new AlertDialog.Builder(homeAdmin_sayur.this);
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