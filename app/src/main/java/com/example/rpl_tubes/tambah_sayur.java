package com.example.rpl_tubes;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class tambah_sayur extends AppCompatActivity {

    protected Cursor cursor;
    DBhelper db;
    Button btn_simpan;
    ImageView imageView;
    EditText nama_sayur,harga_sayur,stok_sayur,deskripsi_sayur;

    private static final int PICK_IMAGE_REQUEST=99;
    private Uri imagepath;
    private Bitmap imagetostore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_sayur);
        db=new DBhelper(this);
        imageView=findViewById(R.id.gambar_sayur);
        nama_sayur=findViewById(R.id.namasayur);
        harga_sayur=findViewById(R.id.input_harga_sayur);
        stok_sayur=findViewById(R.id.input_stock_sayur);
        deskripsi_sayur=findViewById(R.id.input_deskripsi_sayur);
        btn_simpan=findViewById(R.id.btn_tambah_sayur);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseimage();
            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nm_sayur=nama_sayur.getText().toString();
                String hrg=harga_sayur.getText().toString();
                String stk=stok_sayur.getText().toString();
                Integer hrg_sayur=Integer.parseInt(hrg);
                Integer st_sayur=Integer.parseInt(stk);
                String ds_sayur=deskripsi_sayur.getText().toString();

                boolean insert=db.insert_data_sayur(nm_sayur,hrg_sayur,st_sayur,ds_sayur,imagetostore);
                if(insert==true){
                    Toast.makeText(tambah_sayur.this,"Sayur berhasil di tambah",Toast.LENGTH_SHORT).show();
                    beranda_penjual.bp.refreshlist();
                    finish();
                }

            }
        });
    }

    private void choseimage() {
        try {
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,PICK_IMAGE_REQUEST);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data != null && data.getData()!=null){
                imagepath=data.getData();
                imagetostore= MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);
                imageView.setImageBitmap(imagetostore);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }
}