package com.alfian.android.kurirlaudry.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ALFIAN on 06/06/2017.
 */

public class DataBarang {
    @SerializedName("id_barang")
    String id_barang;
    @SerializedName("id_pesanan")
    String id_pesanan;
    @SerializedName("jenis_jasa")
    String jenis_jasa;
    @SerializedName("jenis_brg")
    String jenis_brg;
    @SerializedName("jumlah_brg")
    String jumlah_brg;
    @SerializedName("keterangan")
    String keterangan;
    @SerializedName("berat")
    String berat;
    @SerializedName("biaya")
    String biaya;

    public String getId_barang() {
        return id_barang;
    }

    public String getId_pesanan() {
        return id_pesanan;
    }

    public String getJenis_jasa() {
        return jenis_jasa;
    }

    public String getJenis_brg() {
        return jenis_brg;
    }

    public String getJumlah_brg() {
        return jumlah_brg;
    }

    public String getKeterangan() { return keterangan; }

    public String getBerat() {
        return berat;
    }

    public String getBiaya() {
        return biaya;
    }


    public void setId_barang(String id_barang) {
        this.id_barang = id_barang;
    }

    public void setId_pesanan(String id_pesanan) {
        this.id_pesanan = id_pesanan;
    }

    public void setJenis_jasa(String jenis_jasa) {
        this.jenis_jasa = jenis_jasa;
    }

    public void setJenis_brg(String jenis_brg) {
        this.jenis_brg = jenis_brg;
    }

    public void setJumlah_brg(String jumlah_brg) {
        this.jumlah_brg = jumlah_brg;
    }

    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }

    public void setBerat(String berat) {
        this.berat = berat;
    }

    public void setBiaya(String biaya) {
        this.biaya = biaya;
    }
}
