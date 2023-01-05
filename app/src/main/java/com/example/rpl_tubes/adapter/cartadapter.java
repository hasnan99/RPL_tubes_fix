package com.example.rpl_tubes.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.rpl_tubes.R;
import com.example.rpl_tubes.dbcontract;

public class cartadapter extends CursorAdapter {

    public cartadapter(Context context, Cursor cursor) {
        super(context, cursor,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.cart_list,parent,false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nama_sayur,harga,total;

        nama_sayur=view.findViewById(R.id.drinkNameinOrderSummary);
        harga=view.findViewById(R.id.priceinOrderSummary);
        total=view.findViewById(R.id.quantityinOrderSummary);

        int nama_=cursor.getColumnIndex(dbcontract.orderentry.kolom_nama);
        int harga_sayur=cursor.getColumnIndex(dbcontract.orderentry.kolom_total);
        int jumlah_sayur=cursor.getColumnIndex(dbcontract.orderentry.kolom_jumlah);

        String nama_s=cursor.getString(nama_);
        String harga_s=cursor.getString(harga_sayur);
        String total_s=cursor.getString(jumlah_sayur);

        nama_sayur.setText(nama_s);
        harga.setText(harga_s);
        total.setText(total_s);


    }
}
