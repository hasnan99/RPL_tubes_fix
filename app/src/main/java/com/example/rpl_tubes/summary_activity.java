package com.example.rpl_tubes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rpl_tubes.adapter.Adapter;
import com.example.rpl_tubes.adapter.cartadapter;

public class summary_activity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public com.example.rpl_tubes.adapter.cartadapter Cartadapter;
    public static final int LOADER=0;
    TextView alamat,total_harga;
    protected Cursor cursor;
    DBhelper db;
    RadioGroup pilih;
    String pembayaran;
    RadioButton pilih_pembayaran;
    Button beli;
    String status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Button button=findViewById(R.id.clearthedatabase);
        db=new DBhelper(this);
        alamat=findViewById(R.id.view_alamat);
        total_harga=findViewById(R.id.view_total_harga);
        pilih =findViewById(R.id.grup_pilih);
        beli=findViewById(R.id.button6);

        SQLiteDatabase db1=db.getReadableDatabase();
        cursor=db1.rawQuery("SELECT * FROM pembeli WHERE nama='" +
                getIntent().getStringExtra("nama_user")+"'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            cursor.moveToPosition(0);
            alamat.setText(cursor.getString(4));
        }

        String ttl=Integer.toString(getTotalOfAmount());
        total_harga.setText("Rp."+ttl);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int delete=getContentResolver().delete(dbcontract.orderentry.content_uri,null,null);


            }
        });



        getSupportLoaderManager().initLoader(LOADER,null,this);

        ListView listView=findViewById(R.id.list);
        Cartadapter=new cartadapter(this,null);
        listView.setAdapter(Cartadapter);

        beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bayar=pilih.getCheckedRadioButtonId();
                pilih_pembayaran=findViewById(bayar);
                pembayaran=pilih_pembayaran.getText().toString();
                beli();
            }
        });
    }

    private void beli() {
        SQLiteDatabase db2 = db.getReadableDatabase();
        cursor = db2.rawQuery("SELECT  * from keranjang_belanja", null);
        cursor.moveToFirst();
        String nama = null;
        String jumlah = null;
        if (cursor.getCount() > 0) {
            nama = String.valueOf(cursor.getString(1)).toString();
            jumlah = String.valueOf(cursor.getString(2)).toString();
        }
        String nama_barang = String.valueOf(db.getall_nama_sayur());
        String jumlah_barang = String.valueOf(db.getall_jumlah_sayur());
        String harga_total=String.valueOf(total_harga.getText().toString());
        String bayar=String.valueOf(pembayaran);
        status="proses";

        boolean insert=db.insert_data_pembayaran(nama_barang,jumlah_barang,harga_total,bayar,status);
        if(insert==true){
            Toast.makeText(summary_activity.this,"Berhasil melakukan pembayaran",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),beranda.class);
            startActivity(intent);
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id,  Bundle args) {
        String[] projection={dbcontract.orderentry._ID,dbcontract.orderentry.kolom_nama,dbcontract.orderentry.kolom_total,dbcontract.orderentry.kolom_jumlah};
        return new CursorLoader(this,dbcontract.orderentry.content_uri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished( Loader<Cursor> loader, Cursor data) {
        Cartadapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset( Loader<Cursor> loader) {
        Cartadapter.swapCursor(null);
    }

    public int getTotalOfAmount() {
        int sum = 0;
        SQLiteDatabase db2 = db.getReadableDatabase();
        Cursor c = db2.rawQuery("SELECT  "+dbcontract.orderentry.kolom_total+ " FROM "+ dbcontract.orderentry.Table_name, null);
        while (c.moveToNext()) {
            sum += Integer.parseInt(c.getString(0));
        }
        c.close();
        return sum;
    }


}