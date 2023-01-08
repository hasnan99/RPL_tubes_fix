package com.example.rpl_tubes;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.TextView;

public class lihat_order extends AppCompatActivity {

    TextView nama_sayur,jumlah,total,metode,status;
    protected Cursor cursor;
    DBhelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_order);
        nama_sayur=findViewById(R.id.textView35);
        jumlah=findViewById(R.id.textView37);
        total=findViewById(R.id.textView39);
        metode=findViewById(R.id.textView41);
        status=findViewById(R.id.textView43);
        db=new DBhelper(this);

        SQLiteDatabase db1=db.getReadableDatabase();
        cursor=db1.rawQuery("SELECT * FROM pembayaran WHERE nama='" +
                getIntent().getStringExtra("nama_order")+"'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            cursor.moveToPosition(0);
            nama_sayur.setText(cursor.getString(1).toString());
            jumlah.setText(cursor.getString(2).toString());
            total.setText(cursor.getString(3).toString());
            metode.setText(cursor.getString(4).toString());
            status.setText(cursor.getString(5).toString());
        }

    }
}