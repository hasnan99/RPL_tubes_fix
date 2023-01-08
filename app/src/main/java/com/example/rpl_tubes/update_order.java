package com.example.rpl_tubes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class update_order extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView nama_sayur,jumlah,total,metode,status,id_status;
    protected Cursor cursor;
    Button btn_update;
    Spinner spinner;
    DBhelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_update);
        id_status=findViewById(R.id.textView45);
        nama_sayur=findViewById(R.id.textView35);
        jumlah=findViewById(R.id.textView37);
        total=findViewById(R.id.textView39);
        metode=findViewById(R.id.textView41);
        spinner=findViewById(R.id.spinner2);
        btn_update=findViewById(R.id.button4);
        db=new DBhelper(this);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.spinner2, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        SQLiteDatabase db1=db.getReadableDatabase();
        cursor=db1.rawQuery("SELECT * FROM pembayaran WHERE nama='" +
                getIntent().getStringExtra("nama_order")+"'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            cursor.moveToPosition(0);
            id_status.setText(cursor.getString(0).toString());
            nama_sayur.setText(cursor.getString(1).toString());
            jumlah.setText(cursor.getString(2).toString());
            total.setText(cursor.getString(3).toString());
            metode.setText(cursor.getString(4).toString());
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status=parent.getItemAtPosition(position).toString();
                String id_s=id_status.getText().toString();
                boolean update=db.update_status(id_s,status);
                if (update==true){
                    Toast.makeText(update_order.this,"Data berhasil di ubah",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(update_order.this,order_penjual.class);
                    startActivity(intent);
                }

            }
        });

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}