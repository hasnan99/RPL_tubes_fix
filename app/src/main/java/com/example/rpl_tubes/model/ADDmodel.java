package com.example.rpl_tubes.model;

import android.graphics.Bitmap;

public class ADDmodel {
    String nama_sayur;
    byte[] image;
    int harga;
    String deskripsi;
    int stock;

    public ADDmodel(String nama_sayur, int harga,byte[] image, String deskripsi, int stock) {
        this.nama_sayur = nama_sayur;
        this.image = image;
        this.harga = harga;
        this.deskripsi = deskripsi;
        this.stock = stock;
    }

    public ADDmodel(String nama_sayur, int harga, byte[] image) {
        this.nama_sayur = nama_sayur;
        this.image = image;
        this.harga = harga;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getNama_sayur() {
        return nama_sayur;
    }

    public void setNama_sayur(String nama_sayur) {
        this.nama_sayur = nama_sayur;
    }
}
