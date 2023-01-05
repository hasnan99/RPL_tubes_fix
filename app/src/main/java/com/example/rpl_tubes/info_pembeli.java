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

public class info_pembeli extends AppCompatActivity {

    protected Cursor cursor;
    DBhelper db;
    TextView nama_pembeli,email_pembeli,alamat_pembeli,no_telepon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pembeli);
        db=new DBhelper(this);
        nama_pembeli=findViewById(R.id.nama_pembeli);
        email_pembeli=findViewById(R.id.email_pembeli);
        alamat_pembeli=findViewById(R.id.alamat_pembeli);
        no_telepon=findViewById(R.id.no_telepon_pembeli);

        SQLiteDatabase db1=db.getReadableDatabase();
        cursor=db1.rawQuery("SELECT * FROM pembeli WHERE nama='" +
                getIntent().getStringExtra("nama_user")+"'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            cursor.moveToPosition(0);
            nama_pembeli.setText(cursor.getString(1).toString());
            email_pembeli.setText(cursor.getString(3).toString());
            String cek_alamat=cursor.getString(4);
            String cek_hp=cursor.getString(5);
            if(TextUtils.isEmpty(cek_alamat)||TextUtils.isEmpty(cek_hp)){
                alamat_pembeli.setText("masih kosong");
                no_telepon.setText("masih kosong");
            }else{
                alamat_pembeli.setText(cursor.getString(4).toString());
                no_telepon.setText(cursor.getString(5).toString());
            }

        }


    }
    public void update(View view){
        Intent intent=new Intent(this,update_akun_pembeli.class);
        String nama=getIntent().getStringExtra("nama_user");
        intent.putExtra("nama_user",nama);
        startActivity(intent);
    }
}