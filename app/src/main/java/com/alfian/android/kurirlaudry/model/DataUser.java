package com.alfian.android.kurirlaudry.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alfian on 15/02/2017.
 */

public class DataUser {
    @SerializedName("user_plg")
    String user_plg;
    @SerializedName("pass_plg")
    String pass_plg;
    @SerializedName("nama_plg")
    String nama_plg;
    @SerializedName("alamat")
    String alamat;
    @SerializedName("hp")
    String hp;
    @SerializedName("saldo")
    String saldo;

    public String getUser_plg() {
        return user_plg;
    }

    public String getPass_plg() {
        return pass_plg;
    }

    public String getNama_plg() {
        return nama_plg;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getHp() {
        return hp;
    }

    public String getSaldo() {
        return saldo;
    }
}
