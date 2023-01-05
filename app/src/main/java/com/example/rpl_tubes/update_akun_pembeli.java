package com.example.rpl_tubes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class update_akun_pembeli extends AppCompatActivity {

    protected Cursor cursor;
    DBhelper db;
    Button btn_update;
    EditText nama,email,alamat,no_telepon;
    TextView id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_akun_pembeli);

        db=new DBhelper(this);
        nama=findViewById(R.id.editTextTextPersonName);
        email=findViewById(R.id.editTextTextPersonName2);
        alamat=findViewById(R.id.editTextTextPersonName3);
        no_telepon=findViewById(R.id.editTextPhone);
        btn_update=findViewById(R.id.button5);
        id=findViewById(R.id.id_penjual);

        SQLiteDatabase db1=db.getReadableDatabase();
        cursor=db1.rawQuery("SELECT * FROM pembeli WHERE nama='" +
                getIntent().getStringExtra("nama_user")+"'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            cursor.moveToPosition(0);
            id.setText(cursor.getString(0).toString());
            nama.setText(cursor.getString(1).toString());
            email.setText(cursor.getString(3).toString());
            String cek_alamat=cursor.getString(4);
            String cek_hp=cursor.getString(5);
            if(TextUtils.isEmpty(cek_alamat)||TextUtils.isEmpty(cek_hp)){
                alamat.setText("masih kosong");
                no_telepon.setText("masih kosong");
            }else{
                alamat.setText(cursor.getString(4).toString());
                no_telepon.setText(cursor.getString(5).toString());
            }

        }

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama_penjual=nama.getText().toString();
                String email_penjual=email.getText().toString();
                String alamat_penjual=alamat.getText().toString();
                String telepon=no_telepon.getText().toString();
                String id_pembeli=id.getText().toString();

                boolean update=db.update_data_pembeli(id_pembeli,nama_penjual,email_penjual,alamat_penjual,telepon);
                if (update==true){
                    Toast.makeText(update_akun_pembeli.this,"Data berhasil di update",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),beranda.class);
                    String nama=getIntent().getStringExtra("nama_user");
                    intent.putExtra("nama_user",nama);
                    startActivity(intent);
                }
            }
        });
    }
}