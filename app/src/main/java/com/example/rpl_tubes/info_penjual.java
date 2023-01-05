package com.example.rpl_tubes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class info_penjual extends AppCompatActivity {

    protected Cursor cursor;
    DBhelper db;
    TextView nama_penjual,email_penjual,alamat_penjual,no_telepon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_penjual);
        db=new DBhelper(this);
        nama_penjual=findViewById(R.id.nama_penjual);
        email_penjual=findViewById(R.id.email_penjual);
        alamat_penjual=findViewById(R.id.alamat_penjual);
        no_telepon=findViewById(R.id.no_telepon);

        SQLiteDatabase db1=db.getReadableDatabase();
        cursor=db1.rawQuery("SELECT * FROM penjual WHERE nama='" +
                getIntent().getStringExtra("nama")+"'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            cursor.moveToPosition(0);
            nama_penjual.setText(cursor.getString(1).toString());
            email_penjual.setText(cursor.getString(2).toString());
            String cek_alamat=cursor.getString(3);
            String cek_hp=cursor.getString(5);
            if(TextUtils.isEmpty(cek_alamat)||TextUtils.isEmpty(cek_hp)){
                alamat_penjual.setText("masih kosong");
                no_telepon.setText("masih kosong");
            }else{
                alamat_penjual.setText(cursor.getString(3).toString());
                no_telepon.setText(cursor.getString(5).toString());
            }

        }


    }
    public void update(View view){
        Intent intent=new Intent(this,update_akun_penjual.class);
        String nama=getIntent().getStringExtra("nama");
        intent.putExtra("nama",nama);
        startActivity(intent);
    }
}