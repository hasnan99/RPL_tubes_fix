package com.example.rpl_tubes;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;



public class dbprovider extends ContentProvider {

    public static final  int order=100;

    public static UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(dbcontract.Content_authority,dbcontract.path,order);
    }

    public DBhelper dBhelper;

    @Override
    public boolean onCreate() {
        dBhelper=new DBhelper(getContext());
        return true;

    }


    @Override
    public Cursor query( Uri uri,  String[] projection, String selection,  String[] selectionArgs,  String sortOrder) {
        SQLiteDatabase db=dBhelper.getReadableDatabase();
        Cursor cursor;
        int match=uriMatcher.match(uri);
        switch (match){
            case order:
                cursor=db.query(dbcontract.orderentry.Table_name,projection,selection,selectionArgs, null,null,sortOrder);
                break;

            default:
                throw new IllegalArgumentException("CANT QUERY");
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return  cursor;
    }


    @Override
    public String getType( Uri uri) {
        return null;
    }


    @Override
    public Uri insert( Uri uri, ContentValues values) {
        int match=uriMatcher.match(uri);
        switch (match){
            case order:
                return insertcart(uri,values);

            default:
                throw new IllegalArgumentException("Tidak bisa masukkan data");

        }

    }

    private Uri insertcart(Uri uri, ContentValues values) {
        String nama=values.getAsString(dbcontract.orderentry.kolom_nama);
        if (nama==null){
            throw  new IllegalArgumentException("nama harus ada");
        }

        String quantity=values.getAsString(dbcontract.orderentry.kolom_jumlah);
        if (quantity==null){
            throw  new IllegalArgumentException("quantity harus ada");
        }

        String total=values.getAsString(dbcontract.orderentry.kolom_total);
        if (total==null){
            throw  new IllegalArgumentException("total harus ada");
        }

        SQLiteDatabase db=dBhelper.getWritableDatabase();
        long id=db.insert(dbcontract.orderentry.Table_name,null,values);

        if (id==-1){
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri,id);
    }

    @Override
    public int delete( Uri uri,  String selection, String[] selectionArgs) {
        int rowsdeleted;
        SQLiteDatabase db=dBhelper.getWritableDatabase();
        int match=uriMatcher.match(uri);
        switch (match){
            case order:
                rowsdeleted=db.delete(dbcontract.orderentry.Table_name,null,selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Tidak bisa hapus");
        }

        if (rowsdeleted!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsdeleted;
    }

    @Override
    public int update( Uri uri,  ContentValues values,String selection, String[] selectionArgs) {
        return 0;
    }
}
