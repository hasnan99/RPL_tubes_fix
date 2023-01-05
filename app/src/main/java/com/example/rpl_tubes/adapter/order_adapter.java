package com.example.rpl_tubes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rpl_tubes.R;

import java.util.ArrayList;

public class order_adapter extends RecyclerView.Adapter<order_adapter.ViewHolder> implements order {
    private Context context;
    private ArrayList nama_sayur,jumlah,total,metode_bayar,status;

    public order_adapter(Context context, ArrayList nama_sayur, ArrayList jumlah, ArrayList total, ArrayList metode_bayar, ArrayList status) {
        this.context = context;
        this.nama_sayur = nama_sayur;
        this.jumlah = jumlah;
        this.total = total;
        this.metode_bayar = metode_bayar;
        this.status = status;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.order_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nama_sayur.setText(String.valueOf(nama_sayur.get(position)));
        holder.jumlah.setText(String.valueOf(jumlah.get(position)));
        holder.total.setText(String.valueOf(total.get(position)));
        holder.metode_bayar.setText(String.valueOf(metode_bayar.get(position)));
        holder.status.setText(String.valueOf(status.get(position)));

    }

    @Override
    public int getItemCount() {
        return nama_sayur.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama_sayur,jumlah,total,metode_bayar,status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_sayur=itemView.findViewById(R.id.name_text);
            jumlah=itemView.findViewById(R.id.jumlah_text);
            total=itemView.findViewById(R.id.total_text);
            metode_bayar=itemView.findViewById(R.id.metode_text);
            status=itemView.findViewById(R.id.status_text);
        }
    }



}
