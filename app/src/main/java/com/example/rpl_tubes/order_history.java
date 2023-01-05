package com.example.rpl_tubes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.example.rpl_tubes.adapter.Adapter;
import com.example.rpl_tubes.adapter.order_adapter;

import java.util.ArrayList;

public class order_history extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> nama_sayur,jumlah,total,metode_bayar,status;
    DBhelper db;
    order_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        db=new DBhelper( this);
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

    }

    private void displayData() {
        Cursor cursor=db.getData();
        if (cursor.getCount()==0){
            Toast.makeText(this, "No Data Exists", Toast.LENGTH_SHORT).show();
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