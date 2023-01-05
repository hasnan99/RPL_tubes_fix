package com.example.rpl_tubes.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

public interface order {
    @NonNull
    order_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    void onBindViewHolder(@NonNull order_adapter.ViewHolder holder, int position);

    int getItemCount();
}
