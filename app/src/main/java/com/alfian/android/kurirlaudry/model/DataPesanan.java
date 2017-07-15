package com.alfian.android.kurirlaudry.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ALFIAN on 02/06/2017.
 */

public class DataPesanan {
    @SerializedName("id_pesanan")
    String id_pesanan;
    @SerializedName("user_plg")
    String user_plg;
    @SerializedName("user_kurir")
    String user_kurir;
    @SerializedName("tanggal")
    String tanggal;
    @SerializedName("longitute")
    String longitute;
    @SerializedName("latitute")
    String latitute;
    @SerializedName("alamat")
    String alamat;
    @SerializedName("ket_lokasi")
    String ket_lokasi;
    @SerializedName("ongkir")
    String ongkir;
    @SerializedName("total_biaya")
    String total_biaya;
    @SerializedName("status_brg")
    String status_brg;
    @SerializedName("status_bayar")
    String status_bayar;

    public String getId_pesanan() {
        return id_pesanan;
    }

    public String getUser_plg() {
        return user_plg;
    }

    public String getUser_kurir() {
        return user_kurir;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getLongitute() {
        return longitute;
    }

    public String getLatitute() {
        return latitute;
    }

    public String getAlamat() { return alamat; }

    public String getKet_lokasi() {
        return ket_lokasi;
    }

    public String getOngkir() {
        return ongkir;
    }

    public String getTotal_biaya() {
        return total_biaya;
    }

    public String getStatus_brg() {
        return status_brg;
    }

    public String getStatus_bayar() {
        return status_bayar;
    }


    public void setId_pesanan(String id_pesanan) {
        this.id_pesanan = id_pesanan;
    }

    public void setUser_plg(String user_plg) {
        this.user_plg = user_plg;
    }

    public void setUser_kurir(String user_kurir) {
        this.user_kurir = user_kurir;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void setLongitute(String longitute) {
        this.longitute = longitute;
    }

    public void setLatitute(String latitute) {
        this.latitute = latitute;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setKet_lokasi(String ket_lokasi) {
        this.ket_lokasi = ket_lokasi;
    }

    public void setOngkir(String ongkir) {
        this.ongkir = ongkir;
    }

    public void setTotal_biaya(String total_biaya) {
        this.total_biaya = total_biaya;
    }

    public void setStatus_brg(String status_brg) {
        this.status_brg = status_brg;
    }

    public void setStatus_bayar(String status_bayar) {
        this.status_bayar = status_bayar;
    }
}
