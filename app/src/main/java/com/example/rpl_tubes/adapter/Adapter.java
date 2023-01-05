package com.example.rpl_tubes.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rpl_tubes.R;
import com.example.rpl_tubes.detail_produk;
import com.example.rpl_tubes.model.ADDmodel;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context context;
    ArrayList<ADDmodel>adDmodels;
    SQLiteDatabase sqLiteDatabase;
    int singledata;

    public Adapter(Context context, int singledata,ArrayList<ADDmodel> adDmodels, SQLiteDatabase sqLiteDatabase ) {
        this.context = context;
        this.adDmodels = adDmodels;
        this.sqLiteDatabase = sqLiteDatabase;
        this.singledata = singledata;
    }


    public void filter(ArrayList<ADDmodel> filter){
        this.adDmodels=filter;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.list_item,null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final ADDmodel adDmodel=adDmodels.get(position);
        byte[] image=adDmodel.getImage();
        Bitmap bitmap= BitmapFactory.decodeByteArray(image,0,image.length);
        holder.imageView.setImageBitmap(bitmap);
        holder.nama_sayur.setText(adDmodel.getNama_sayur());
        holder.harga_sayur.setText("Rp."+String.valueOf(adDmodel.getHarga()));
        holder.stock_sayur.setText(String.valueOf(adDmodel.getStock()));
        holder.deskripsi_sayur.setText(adDmodel.getDeskripsi());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, detail_produk.class);
                intent.putExtra("nama_sayur",adDmodels.get(position).getNama_sayur());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return adDmodels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama_sayur,harga_sayur,deskripsi_sayur,stock_sayur;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_sayur=itemView.findViewById(R.id.nama_sayur);
            harga_sayur=itemView.findViewById(R.id.harga_sayur);
            imageView=itemView.findViewById(R.id.img1);
            deskripsi_sayur=itemView.findViewById(R.id.deskripsi_teks_sayur);
            stock_sayur=itemView.findViewById(R.id.stock_sayur);
        }
    }
}
