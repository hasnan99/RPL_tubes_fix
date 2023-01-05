package com.example.rpl_tubes;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class detail_sayur extends AppCompatActivity {

    protected Cursor cursor;
    DBhelper db;
    TextView nama_sayur,harga_sayur,stok_sayur,deskripsi_sayur;
    ImageView gambar_sayur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sayur);
        db=new DBhelper(this);
        gambar_sayur=findViewById(R.id.detail_gambar_sayur);
        nama_sayur=findViewById(R.id.detail_nama_sayur);
        harga_sayur=findViewById(R.id.detail_harga_sayur);
        stok_sayur=findViewById(R.id.detail_stock_sayur);
        deskripsi_sayur=findViewById(R.id.detail_deskripsi_sayur);

        SQLiteDatabase db1=db.getReadableDatabase();
        cursor=db1.rawQuery("SELECT * FROM sayur WHERE nama_sayur='" +
                getIntent().getStringExtra("nama_sayur")+"'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            cursor.moveToPosition(0);
            nama_sayur.setText(cursor.getString(1).toString());
            harga_sayur.setText("Rp."+cursor.getString(2).toString());
            stok_sayur.setText(cursor.getString(3).toString());
            deskripsi_sayur.setText(cursor.getString(4).toString());
            byte[] bytes=cursor.getBlob(5);
            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            gambar_sayur.setImageBitmap(bitmap);
        }


    }
}