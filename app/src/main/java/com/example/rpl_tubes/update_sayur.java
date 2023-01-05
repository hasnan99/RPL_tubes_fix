package com.example.rpl_tubes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class update_sayur extends AppCompatActivity {

    protected Cursor cursor;
    DBhelper db;
    Button btn_update;
    EditText nama_sayur,harga_sayur,stok_sayur,deskripsi_sayur;
    TextView id_sayur;
    ImageView imageView;

    private static final int PICK_IMAGE_REQUEST=99;
    private Uri imagepath;
    private Bitmap imagetostore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sayur);

        db=new DBhelper(this);
        nama_sayur=findViewById(R.id.namasayur);
        harga_sayur=findViewById(R.id.input_harga_sayur);
        stok_sayur=findViewById(R.id.input_stock_sayur);
        deskripsi_sayur=findViewById(R.id.input_deskripsi_sayur);
        id_sayur=findViewById(R.id.id);
        btn_update=findViewById(R.id.btn_update_sayur);

        imageView=findViewById(R.id.gambar_sayur);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseimage();
            }
        });

        SQLiteDatabase db1=db.getReadableDatabase();
        cursor=db1.rawQuery("SELECT * FROM sayur WHERE nama_sayur = '"+
                getIntent().getStringExtra("nama_sayur")+"'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            cursor.moveToPosition(0);
            id_sayur.setText(cursor.getString(0).toString());
            nama_sayur.setText(cursor.getString(1).toString());
            harga_sayur.setText(cursor.getString(2).toString());
            stok_sayur.setText(cursor.getString(3).toString());
            deskripsi_sayur.setText(cursor.getString(4).toString());
            byte[] bytes=cursor.getBlob(5);
            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            imageView.setImageBitmap(bitmap);
        }

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nm_sayur=nama_sayur.getText().toString();
                String hrg=harga_sayur.getText().toString();
                String stk=stok_sayur.getText().toString();
                Integer hrg_sayur=Integer.parseInt(hrg);
                Integer st_sayur=Integer.parseInt(stk);
                String ds_sayur=deskripsi_sayur.getText().toString();
                String ids=id_sayur.getText().toString();

                boolean update=db.update_data_sayur(ids,nm_sayur,hrg_sayur,st_sayur,ds_sayur,imagetostore);
                if (update==true){
                    Toast.makeText(update_sayur.this,"Data berhasil di ubah",Toast.LENGTH_SHORT).show();
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