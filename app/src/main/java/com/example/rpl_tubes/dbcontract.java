package com.example.rpl_tubes;

import android.net.Uri;
import android.provider.BaseColumns;

import java.sql.Blob;

public class dbcontract {
    public dbcontract(){

    }
    public static final String Content_authority="com.example.rpl_tubes";
    public static final Uri base_uri=Uri.parse("content://"+Content_authority);
    public static final String path="keranjang_belanja";

   public static abstract class orderentry implements BaseColumns{
       public static final Uri content_uri =Uri.withAppendedPath(base_uri,path);

       public static final String Table_name="keranjang_belanja";
       public static final String _ID="_id";
       public static final String kolom_nama="nama";
       public static final String kolom_jumlah="jumlah";
       public static final String kolom_total="total";
       public static final String kolom_gambar="gambar";

   }
}
