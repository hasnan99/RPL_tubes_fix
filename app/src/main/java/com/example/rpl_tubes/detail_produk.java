package com.example.rpl_tubes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class detail_produk extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    ImageView imageView,kurang,tambah;
    TextView nama_sayur;
    TextView harga_sayur;
    TextView stok_sayur;
    TextView deskripsi;
    TextView jumlah;
    Cursor cursor;
    Button tambah_keranjang;
    DBhelper db;
    int total,st;
    public Uri mycarturi;
    boolean allvalues=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);
        imageView=findViewById(R.id.imageView3);
        nama_sayur=findViewById(R.id.textView18);
        harga_sayur=findViewById(R.id.textView22);
        stok_sayur=findViewById(R.id.textView23);
        jumlah=findViewById(R.id.quantity);
        kurang=findViewById(R.id.kurang);
        tambah=findViewById(R.id.tambah);
        tambah_keranjang=findViewById(R.id.button3);
        deskripsi=findViewById(R.id.textView24);
        db=new DBhelper(this);

        tambah_keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savecart();
                String nm_sayur=nama_sayur.getText().toString();
                Integer st_sayur=Integer.parseInt(stok_sayur.getText().toString());
                Integer update_sayur=st_sayur-total;
                boolean update=db.update_stock(nm_sayur,update_sayur);
                if (update==true){
                    Toast.makeText(detail_produk.this,"Berhasil Masuk keranjang",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),beranda.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(detail_produk.this,"gagal Masuk keranjang",Toast.LENGTH_SHORT).show();
                }

            }
        });

        SQLiteDatabase db1=db.getReadableDatabase();
        cursor=db1.rawQuery("SELECT * FROM sayur WHERE nama_sayur='" +
                getIntent().getStringExtra("nama_sayur")+"'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            cursor.moveToPosition(0);
            nama_sayur.setText(cursor.getString(1).toString());
            harga_sayur.setText(cursor.getString(2).toString());
            stok_sayur.setText(cursor.getString(3).toString());
            deskripsi.setText(cursor.getString(4).toString());
            byte[] bytes=cursor.getBlob(5);
            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            imageView.setImageBitmap(bitmap);
        }

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                st=Integer.parseInt(stok_sayur.getText().toString());
                if(total>=st){
                    Toast.makeText(detail_produk.this,"Melebihi Stock",Toast.LENGTH_SHORT).show();
                }else{
                    total++;
                }
                displayquantity();
            }
        });

        kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (total==0){
                    Toast.makeText(detail_produk.this,"Sudah 0",Toast.LENGTH_SHORT).show();
                }else {
                    total--;
                    displayquantity();
                }
            }
        });

    }

    private boolean savecart() {
        String nama=nama_sayur.getText().toString();
        int total=Integer.valueOf(harga_sayur.getText().toString());
        int jumlah_barang=Integer.valueOf(jumlah.getText().toString());
        int total_harga=total*jumlah_barang;
        String harga_baru=String.valueOf(total_harga);
        String harga=(harga_baru);
        String quantity=jumlah.getText().toString();


        ContentValues values=new ContentValues();

        values.put(dbcontract.orderentry.kolom_nama,nama);
        values.put(dbcontract.orderentry.kolom_total,harga);
        values.put(dbcontract.orderentry.kolom_jumlah,quantity);

        if (mycarturi==null){
            Uri newuri=getContentResolver().insert(dbcontract.orderentry.content_uri,values );
            if (newuri==null){
                Toast.makeText(this,"gagal masuk keranjang",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"Berhasil masuk keranjang",Toast.LENGTH_SHORT).show();
            }
        }
        allvalues=true;
        return allvalues;

    }

    private void displayquantity() {
        jumlah.setText(String.valueOf(total));
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection={dbcontract.orderentry._ID,dbcontract.orderentry.kolom_nama,dbcontract.orderentry.kolom_total,dbcontract.orderentry.kolom_jumlah};
        return new CursorLoader(this,mycarturi,projection,null,null,null);
    }

    @Override
    public void onLoadFinished( Loader<Cursor> loader, Cursor data) {
        if (cursor==null||cursor.getCount()<1){
            return;
        }
        if (cursor.moveToFirst()){
            int nama=cursor.getColumnIndex(dbcontract.orderentry.kolom_nama);
            int harga=cursor.getColumnIndex(dbcontract.orderentry.kolom_total);
            int jumlah_s=cursor.getColumnIndex(dbcontract.orderentry.kolom_jumlah);

            String nama_s=cursor.getString(nama);
            String harga_s=cursor.getString(harga);
            String total_s=cursor.getString(jumlah_s);

            nama_sayur.setText(nama_s);
            harga_sayur.setText(harga_s);
            jumlah.setText(total_s);
        }
    }

    @Override
    public void onLoaderReset( Loader<Cursor> loader) {
        nama_sayur.setText("");
        harga_sayur.setText("");
        jumlah.setText("");
    }
}