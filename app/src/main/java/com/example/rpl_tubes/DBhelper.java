package com.example.rpl_tubes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DBhelper extends SQLiteOpenHelper {
    private byte[] imageinbytes;
    private ByteArrayOutputStream byteArrayOutputStream;

    public DBhelper(Context context){
        super(context,"sayuria.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table pembeli(id_pembeli INTEGER primary key autoincrement,nama text,password text,email text,alamat text,no_telepon text,status varchar)");
        db.execSQL("create table penjual(id_penjual INTEGER primary key autoincrement,nama text,email text,alamat text,password text,no_telepon text,status varchar)");
        db.execSQL("create table sayur(id_sayur INTEGER primary key autoincrement,nama_sayur varchar,harga_sayur INTEGER,stock INTEGER, deskripsi_sayur text,gambar_sayur blob)");
        db.execSQL("create table pembayaran (id INTEGER primary key autoincrement, nama text,jumlah text,total text,metode_bayar,status text)");
        String SQL_TABLE = "CREATE TABLE " + dbcontract.orderentry.Table_name + " ("
                + dbcontract.orderentry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +  dbcontract.orderentry.kolom_nama + " TEXT , "
                +  dbcontract.orderentry.kolom_jumlah + " TEXT , "
                +  dbcontract.orderentry.kolom_total + " TEXT , "
                +  dbcontract.orderentry.kolom_gambar + " blob );";

        db.execSQL(SQL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists pembeli");
        db.execSQL("drop table if exists penjual");
        db.execSQL("drop table if exists sayur");
        db.execSQL("drop table if exists keranjang_belanja");
        db.execSQL("drop table if exists pembayaran");

    }

    public void tmbah_keranjang(String nama,String jumlah,String total,byte[] gambar){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("nama",nama);
        values.put("jumlah",jumlah);
        values.put("total",total);
        values.put("nama",nama);
        values.put("gambar",gambar);

        db.insert("keranjang_belanja",null,values);
    }

    public boolean insert_data_sayur(String nama_sayur,Integer harga_sayur,Integer stock,String deskripsi_sayur,Bitmap gambar_sayur){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Bitmap imagetostorebitmap=gambar_sayur;

        byteArrayOutputStream = new ByteArrayOutputStream();
        imagetostorebitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream );

        imageinbytes=byteArrayOutputStream.toByteArray();

        values.put("nama_sayur",nama_sayur);
        values.put("harga_sayur",harga_sayur);
        values.put("stock",stock);
        values.put("deskripsi_sayur",deskripsi_sayur);
        values.put("gambar_sayur",imageinbytes);

        long result=db.insert("sayur",null,values);
        if (result==-1)return false;
        else
            return true;
    }

    public boolean update_data_sayur(String id_sayur,String nama_sayur,Integer harga_sayur,Integer stock,String deskripsi_sayur,Bitmap gambar_sayur){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        Bitmap imagetostorebitmap=gambar_sayur;

        byteArrayOutputStream = new ByteArrayOutputStream();
        imagetostorebitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream );

        imageinbytes=byteArrayOutputStream.toByteArray();

        values.put("nama_sayur",nama_sayur);
        values.put("harga_sayur",harga_sayur);
        values.put("stock",stock);
        values.put("deskripsi_sayur",deskripsi_sayur);
        values.put("gambar_sayur",imageinbytes);

        Cursor cursor=db.rawQuery("select * from sayur where id_sayur=?",new String[]{id_sayur});
        if (cursor.getCount()>0){
            int result=db.update("sayur",values,"id_sayur=?",new String[]{id_sayur});
            if (result== -1){
                return false;
            }else {
                return true;
            }
        }
        else {
            return false;
        }

    }

    public boolean update_data_penjual(String id_penjual,String nama,String email,String alamat,String no_telepon){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("nama",nama);
        values.put("email",email);
        values.put("alamat",alamat);
        values.put("no_telepon",no_telepon);

        Cursor cursor=db.rawQuery("select * from penjual where id_penjual=?",new String[]{id_penjual});
        if (cursor.getCount()>0){
            int result=db.update("penjual",values,"id_penjual=?",new String[]{id_penjual});
            if (result== -1){
                return false;
            }else {
                return true;
            }
        }
        else {
            return false;
        }

    }

    public boolean update_data_pembeli(String id_pembeli,String nama,String email,String alamat,String no_telepon){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("nama",nama);
        values.put("email",email);
        values.put("alamat",alamat);
        values.put("no_telepon",no_telepon);

        Cursor cursor=db.rawQuery("select * from pembeli where id_pembeli=?",new String[]{id_pembeli});
        if (cursor.getCount()>0){
            int result=db.update("pembeli",values,"id_pembeli=?",new String[]{id_pembeli});
            if (result== -1){
                return false;
            }else {
                return true;
            }
        }
        else {
            return false;
        }

    }

    public boolean update_stock(String nama,Integer stock){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("nama_sayur",nama);
        values.put("stock",stock);

        Cursor cursor=db.rawQuery("select * from sayur where nama_sayur=?",new String[]{nama});
        if (cursor.getCount()>0){
            int result=db.update("sayur",values,"nama_sayur=?",new String[]{nama});
            if (result== -1){
                return false;
            }else {
                return true;
            }
        }
        else {
            return false;
        }

    }

    public boolean delete_data_sayur(String nama_sayur){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=db.rawQuery("select * from sayur where nama_sayur=?",new String[]{nama_sayur});
        if (cursor.getCount()>0){
            int result=db.delete("sayur","nama_sayur=?",new String[]{nama_sayur});
            if (result==-1){
                return false;
            }else {
                return true;
            }
        }
        else {
            return false;
        }
    }

    public Cursor lihat_data_sayur(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from sayur",null);
        return cursor;
    }

    public boolean insert_data_pembeli(String nama,String password,String email,String status){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("nama",nama);
        values.put("password",password);
        values.put("email",email);
        values.put("status",status);

            long result=db.insert("pembeli",null,values);
            if(result==-1) return  false;
            else
                return true;
    }

    @SuppressLint("Range")
    public ArrayList<String> getall_nama_sayur(){
        ArrayList<String> arrayList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from keranjang_belanja",null);
        cursor.moveToFirst();
        if (cursor!=null){
            while (cursor.isAfterLast()==false){
                arrayList.add(cursor.getString(cursor.getColumnIndex(dbcontract.orderentry.kolom_nama)));
                Log.d("nama_sayur",arrayList.toString());
                cursor.moveToNext();
            }
        }
        return  arrayList;
    }

    @SuppressLint("Range")
    public ArrayList<String> getall_jumlah_sayur(){
        ArrayList<String> arrayList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from keranjang_belanja",null);
        cursor.moveToFirst();
        if (cursor!=null){
            while (cursor.isAfterLast()==false){
                arrayList.add(cursor.getString(cursor.getColumnIndex(dbcontract.orderentry.kolom_jumlah)));
                Log.d("total_sayur",arrayList.toString());
                cursor.moveToNext();
            }
        }
        return  arrayList;
    }

    public boolean insert_data_pembayaran(String nama,String jumlah,String total,String metode,String status){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("nama",nama);
        values.put("jumlah",jumlah);
        values.put("total",total);
        values.put("metode_bayar",metode);
        values.put("status",status);

        long result=db.insert("pembayaran",null,values);
        if(result==-1) return  false;
        else
            return true;
    }

    public boolean insert_data_penjual(String nama,String password,String email,String status){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("nama",nama);
        values.put("password",password);
        values.put("email",email);
        values.put("status",status);

        long result=db.insert("penjual",null,values);
        if(result==-1) return  false;
        else
            return true;
    }

    public Boolean check_user_password_pembeli(String nama,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from pembeli where nama=? and password=? and status='pembeli' ",new String[]{nama,password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean check_user_password_penjual(String nama,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from penjual where nama=? and password=? and status='penjual' ",new String[]{nama,password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Cursor getData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from pembayaran",null);
        return cursor;
    }
}
